import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIAdapterInterface;
import Interfaces.UIPluginInterface;
import TransferModels.AnalysisResultForLecture;
import TransferModels.SelfStudyTimeAndLectureTime;
import de.models.EntryType;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;
import ressourceModels.listeable;
import useCases.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class UITerminalPlugin implements UIPluginInterface {

    private DataPluginInterface dataPlugin;
    private UIAdapterInterface uiAdapter;
    private DataAdapterInterface dataAdapter;
    private Scanner scanner;

    public UITerminalPlugin(DataPluginInterface dataPlugin, DataAdapterInterface dataAdapter, UIAdapterInterface uiAdapter) {
        this.dataPlugin = dataPlugin;
        this.uiAdapter = uiAdapter;
        this.scanner = new Scanner(System.in);
        this.dataAdapter = dataAdapter;
    }

    public void start() {
        System.out.println(getAsciiArt());
        showMainMenu();
    }

    private String getAsciiArt() {
        return "\n" + "  _______ _                                       _           _     \n" + " |__   __(_)                    /\\               | |         (_)    \n" + "    | |   _ _ __ ___   ___     /  \\   _ __   __ _| |_   _ ___ _ ___ \n" + "    | |  | | '_ ` _ \\ / _ \\   / /\\ \\ | '_ \\ / _` | | | | / __| / __|\n" + "    | |  | | | | | | |  __/  / ____ \\| | | | (_| | | |_| \\__ \\ \\__ \\\n" + "    |_|  |_|_| |_| |_|\\___| /_/    \\_\\_| |_|\\__,_|_|\\__, |___/_|___/\n" + "                                                     __/ |          \n" + "                                                    |___/           \n";
    }

    public void showMainMenu() {
        Map<String, ActionAndDesciption> commands = new HashMap<>();
        commands.put("1", new ActionAndDesciption(this::addLecture, "Create new Lecture"));
        commands.put("2", new ActionAndDesciption(this::addEntryByTimeStamp, "Create new Entry"));
        commands.put("3", new ActionAndDesciption(this::addSemester, "Create new Semester"));
        commands.put("4", new ActionAndDesciption(this::getTimePerLecture, "see how much time was spent for one specific Lecture"));
        commands.put("5", new ActionAndDesciption(this::getPresenceTime, "see how much time was spent listening to lectures"));
        commands.put("6", new ActionAndDesciption(this::getSelfStudyTime, "see how much time was spent studying on your own"));
        commands.put("7", new ActionAndDesciption(this::getTimePerSemester, "see how much time was spent for one specific semester"));
        commands.put("8", new ActionAndDesciption(this::compareTime, "compare planned time to the the time that was actually spend"));
        commands.put("9", new ActionAndDesciption(this::getUnfinishedEntries, "view unifinished Entries"));
        commands.forEach((key, value) -> {
            System.out.println(key + "> " + value.getDesciption());
        });
        String option = scanner.nextLine();
        ActionAndDesciption actionAndDesciption = commands.get(option);
        if (Objects.isNull(actionAndDesciption)) {
            System.out.println(option + " is not a valid option.");
        } else {
            actionAndDesciption.getAction().run();
        }
        showMainMenu();
    }

    @Override
    public void addEntryByTimeStamp() {

        String start = getStartTimeForEntry();

        System.out.println("You can either enter a finishing time now or later");
        System.out.println("1> Finish now\n2> Finish later");
        String finishOption = scanner.nextLine();

        if (Integer.parseInt(finishOption) == 1) {
            String end = getEndTimeForEntry();
            String details = getStringFromInputWithPrompt("Details of study");
            String entryType = getEntryTypeForEntry();
            LectureResource lecture = getLectureForEntry();

            EntryRessource entryRessource = new EntryRessource(start, end, entryType, details, lecture.getName(), "FINISHED");
            AdditionalEntry additionalEntry = new AdditionalEntry(dataAdapter, dataPlugin);
            additionalEntry.addEntry(this.uiAdapter.mapEntryRessourceToEntry(entryRessource), this.uiAdapter.mapLectureRessourceToLecture(lecture));
        } else if (Integer.parseInt(finishOption) == 2) {
            String entryType = getEntryTypeForEntry();
            LectureResource lecture = getLectureForEntry();

            EntryRessource entryRessource = new EntryRessource(start, entryType, lecture.getName());
            AdditionalEntry additionalEntry = new AdditionalEntry(dataAdapter, dataPlugin);
            additionalEntry.startEntry(this.uiAdapter.mapEntryRessourceToEntry(entryRessource));
        } else {
            System.out.println("invalid Input");
        }

    }

    @Override
    public void addLecture() {
        //strings einlesen:   name, semester, lectureTime, selfStudyTime
        String name, lectureTime, selfStudyTime;
        name = getStringFromInputWithPrompt("Name of the lecture:");
        lectureTime = getStringFromInputWithPrompt("enter the official lecture time");
        selfStudyTime = getStringFromInputWithPrompt("enter the ammount of time you are supposed to study on your own");
        SemesterRessource semester = getSemesterForLecture();

        LectureResource lectureResource = new LectureResource(name, semester.getName(), Integer.parseInt(lectureTime), Integer.parseInt(selfStudyTime));
        AdditionalLecture additionalLecture = new AdditionalLecture(dataAdapter, dataPlugin);
        additionalLecture.addLecture(this.uiAdapter.mapLectureRessourceToLecture(lectureResource));
    }

    private void addSemester() {

        String name = getStringFromInputWithPrompt("name of semester:");
        String start = getStringFromInputWithPrompt("start date for semester:");
        String end = getStringFromInputWithPrompt("end date for semester:");
        AdditionalSemester additionalSemester = new AdditionalSemester(dataAdapter, dataPlugin);
        SemesterRessource semesterRessource = new SemesterRessource(name, start, end);
        additionalSemester.addSemester(uiAdapter.mapSemesterRessourceToSemester(semesterRessource));
    }

    private void getTimePerSemester() {

        GetSemesters getSemestersUseCase = new GetSemesters(dataAdapter, dataPlugin);
        List<SemesterRessource> semesterList = uiAdapter.mapSemesterListToSemesterRessourceList(getSemestersUseCase.getSemesters());
        SemesterRessource semester = getObjectFromNumberedList(semesterList,"no semesters found. Start by creating a semester");
        Analysis analysis = new Analysis(dataAdapter, dataPlugin);
        Duration duration = analysis.getTimePerSemester(uiAdapter.mapSemesterRessourceToSemester(semester));
        System.out.println(uiAdapter.formatDuration(duration));
    }

    private void getSelfStudyTime() {
        Analysis analysis = new Analysis(dataAdapter, dataPlugin);
        Duration duration = analysis.getStudyTime();
        System.out.println(uiAdapter.formatDuration(duration));
    }

    private void getPresenceTime() {
        Analysis analysis = new Analysis(dataAdapter, dataPlugin);
        Duration duration = analysis.getPresenceTime();
        System.out.println(uiAdapter.formatDuration(duration));
    }

    private void getTimePerLecture() {
        Analysis analysis = new Analysis(dataAdapter, dataPlugin);
        GetLectures getLectureUseCase = new GetLectures(dataAdapter, dataPlugin);
        List<LectureResource> lectures = uiAdapter.mapLectureListToLectureListRessource(getLectureUseCase.getLectures());
        LectureResource lecture = getObjectFromNumberedList(lectures,"no lectures found. Start by creating a lecture");

        SelfStudyTimeAndLectureTime time = analysis.getTimeSpentForLecture(lecture.getName());
        System.out.println("Planned SelfStudyTime: " + lecture.getSelfStudyTime() + " Hours | Actual selfStudyTime: " + uiAdapter.formatDuration(time.getSelfStudyTime()));
        System.out.println("Planned lectureTime: " + lecture.getLectureTime() + " Hours | Actual lectureTime: " + uiAdapter.formatDuration(time.getLectureTime()));
    }


    private void compareTime() {
        Analysis analysis = new Analysis(dataAdapter, dataPlugin);
        List<AnalysisResultForLecture> results = analysis.compareTimeTargetToActual();
        for (AnalysisResultForLecture l : results) {
            System.out.println(l.getLecture().getName());
            System.out.println("Planned SelfStudyTime: " + l.getLecture().getSelfStudyTime() + " Hours | Actual selfStudyTime: " + uiAdapter.formatDuration(l.getSelfStudyTimeAndLectureTime().getSelfStudyTime()));
            System.out.println("Planned lectureTime: " + l.getLecture().getLectureTime() + " Hours | Actual lectureTime: " + uiAdapter.formatDuration(l.getSelfStudyTimeAndLectureTime().getLectureTime()));
        }
    }

    private void getUnfinishedEntries() {
        GetEntries getEntries = new GetEntries(dataAdapter, dataPlugin);
        List<EntryRessource> unfinishedEntries = dataAdapter.mapEntryListToEntryRessourceList(getEntries.getUnfinishedEntries());
        EntryRessource entryRessource = getObjectFromNumberedList(unfinishedEntries,"no unfinished entries found");
    }


    private String getStartTimeForEntry() {
        System.out.println("Start time (press \"n\" to use current time)");
        String start = scanner.nextLine();
        if (start.equals("n")) {
            start = uiAdapter.formatLocalDateTime(LocalDateTime.now());
        }
        return start;
    }

    private String getEndTimeForEntry() {
        System.out.println("End time (press \"n\" to use current time)");
        String end = scanner.nextLine();
        if (end.equals("n")) {
            end = uiAdapter.formatLocalDateTime(LocalDateTime.now());
        }
        return end;
    }

    private String getEntryTypeForEntry() {
        GetEntryTypes getEntryTypesUseCase = new GetEntryTypes(dataAdapter, dataPlugin);
        EntryType[] entryTypes = getEntryTypesUseCase.getEntryType();
        return getEntryTypeFromNumberedList(entryTypes);
    }

    private LectureResource getLectureForEntry() {
        GetLectures getLectureUseCase = new GetLectures(dataAdapter, dataPlugin);
        List<LectureResource> lectures = uiAdapter.mapLectureListToLectureListRessource(getLectureUseCase.getLectures());
        return getObjectFromNumberedList(lectures,"no lectures found. Start by creating a lecture");
    }


    private SemesterRessource getSemesterForLecture() {
        GetSemesters getSemestersUseCase = new GetSemesters(dataAdapter, dataPlugin);
        List<SemesterRessource> semesterList = uiAdapter.mapSemesterListToSemesterRessourceList(getSemestersUseCase.getSemesters());
        return getObjectFromNumberedList(semesterList, "no semesters found. Start by creating a semester");
    }


    private String getStringFromInputWithPrompt(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public void displayMessage(String message) {
        System.out.println("[INFO] " + message);
    }

    @Override
    public void displayError(String errorMessage) {
        System.out.println("[ERROR] " + errorMessage);
    }

    @Override
    public void displayWarning(String warningMessage) {
        System.out.println("[WARNING] " + warningMessage);

    }

    private boolean isInputValidNumber(String input, int expectedRange) {
        try {
            Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        int inputAsInt = Integer.parseInt(input);
        return inputAsInt < expectedRange && inputAsInt >= 0;
    }


    private <T extends listeable> T getObjectFromNumberedList(List<T> list,String listIsEmptyMessage) {
        if(list.isEmpty()){
            System.out.println(listIsEmptyMessage);
        }else{
            System.out.println("choose an item from the list");
        }
        System.out.println(getListOfObjectsAsNumberedList(list));

        String index = scanner.nextLine();
        while (!isInputValidNumber(index, list.size())) {
            System.out.println("invalid input, try again!");
            index = scanner.nextLine();
        }
        return list.get(Integer.parseInt(index));
    }

    private <T extends listeable> String getListOfObjectsAsNumberedList(List<T> list) {
        String toReturn = "";
        for (int i = 0; i < list.size(); i++) {
            toReturn = toReturn.concat(i + ">" + list.get(i).getDisplayName() + "\n");
        }
        return toReturn;
    }

    private String getEntryTypeFromNumberedList(EntryType[] entryTypes) {
        System.out.println("choose a study type");
        System.out.println(getListOfEntrysAsNumberedList(entryTypes));

        String entryIndex = scanner.nextLine();
        while (!isInputValidNumber(entryIndex, entryTypes.length)) {
            System.out.println("invalid input, try again!");
            entryIndex = scanner.nextLine();
        }
        return entryTypes[Integer.parseInt(entryIndex)].name();
    }

    private String getListOfEntrysAsNumberedList(EntryType[] entryTypes) {
        String toReturn = "";
        for (int i = 0; i < entryTypes.length; i++) {
            toReturn = toReturn.concat(i + ">" + entryTypes[i].toString() + "\n");

        }
        return toReturn;
    }
}
