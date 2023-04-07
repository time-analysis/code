import Interfaces.UIAdapterInterface;
import Interfaces.UIPluginInterface;
import renderModels.AnalysisResultForLectureRenderModel;
import renderModels.SelfStudyTimeAndLectureTimeRenderModel;
import repositories.EntryRepositoryInterface;
import repositories.LectureRepositoryInterface;
import repositories.SemesterRepositoryInterface;
import ressourceModels.*;
import usecases.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class UITerminalPlugin implements UIPluginInterface {


    private UIAdapterInterface uiAdapter;
    private SemesterRepositoryInterface semesterRepository;
    private LectureRepositoryInterface lectureRepository;
    private EntryRepositoryInterface entryRepository;
    private Scanner scanner;

    public UITerminalPlugin(SemesterRepositoryInterface semesterRepository, LectureRepositoryInterface lectureRepository, EntryRepositoryInterface entryRepository, UIAdapterInterface uiAdapter) {

        this.uiAdapter = uiAdapter;
        this.scanner = new Scanner(System.in);
        this.semesterRepository = semesterRepository;
        this.lectureRepository = lectureRepository;
        this.entryRepository = entryRepository;
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
        commands.put("8", new ActionAndDesciption(this::comparePlannedTimeToActualSpendTime, "compare planned time to the the time that was actually spend"));
        commands.put("9", new ActionAndDesciption(this::getUnfinishedEntries, "view unfinished Entries"));
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
            EntryRessourceType entryType = getEntryTypeForEntry();
            LectureResource lecture = getLectureForEntry();

            EntryRessource entryRessource = new EntryRessource(start, end, entryType, details, lecture.getName(), EntryRessourceStatus.FINISHED);
            AdditionalEntry additionalEntry = new AdditionalEntry(entryRepository);
            additionalEntry.addEntry(this.uiAdapter.mapEntryRessourceToEntry(entryRessource), this.uiAdapter.mapLectureRessourceToLecture(lecture));
        } else if (Integer.parseInt(finishOption) == 2) {
            EntryRessourceType entryType = getEntryTypeForEntry();
            LectureResource lecture = getLectureForEntry();

            EntryRessource entryRessource = new EntryRessource(start, entryType, lecture.getName(), EntryRessourceStatus.RUNNING);
            AdditionalEntry additionalEntry = new AdditionalEntry(entryRepository);
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
        AdditionalLecture additionalLecture = new AdditionalLecture(lectureRepository);
        additionalLecture.addLecture(this.uiAdapter.mapLectureRessourceToLecture(lectureResource));
    }

    private void addSemester() {

        String name = getStringFromInputWithPrompt("name of semester:");
        String start = getStringFromInputWithPrompt("start date for semester:");
        String end = getStringFromInputWithPrompt("end date for semester:");
        AdditionalSemester additionalSemester = new AdditionalSemester(semesterRepository);
        SemesterRessource semesterRessource = new SemesterRessource(name, start, end);
        additionalSemester.addSemester(uiAdapter.mapSemesterRessourceToSemester(semesterRessource));
    }

    private void getTimePerSemester() {

        GetSemesters getSemestersUseCase = new GetSemesters(semesterRepository);
        List<SemesterRessource> semesterList = uiAdapter.mapSemesterListToSemesterRessourceList(getSemestersUseCase.getSemesters());
        SemesterRessource semester = getObjectFromNumberedList(semesterList, "no semesters found. Start by creating a semester");
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        Duration duration = analysis.getTimePerSemester(uiAdapter.mapSemesterRessourceToSemester(semester));
        System.out.println(uiAdapter.formatDuration(duration));
    }

    private void getSelfStudyTime() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        Duration duration = analysis.getStudyTime();
        System.out.println(uiAdapter.formatDuration(duration));
    }

    private void getPresenceTime() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        Duration duration = analysis.getPresenceTime();
        System.out.println(uiAdapter.formatDuration(duration));
    }

    private void getTimePerLecture() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        GetLectures getLectureUseCase = new GetLectures(lectureRepository);
        List<LectureResource> lectures = uiAdapter.mapLectureListToLectureListRessource(getLectureUseCase.getLectures());
        LectureResource lecture = getObjectFromNumberedList(lectures, "no lectures found. Start by creating a lecture");
        SelfStudyTimeAndLectureTimeRenderModel time = this.uiAdapter.selfStudyTimeAndLectureTimeToRenderModel(analysis.getTimeSpentForLecture(lecture.getName()));
        System.out.println("Planned SelfStudyTime: " + lecture.getSelfStudyTime() + " Hours | Actual selfStudyTime: " + time.getSelfStudyTime());
        System.out.println("Planned lectureTime: " + lecture.getLectureTime() + " Hours | Actual lectureTime: " + time.getLectureTime());
    }


    private void comparePlannedTimeToActualSpendTime() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        List<AnalysisResultForLectureRenderModel> results = this.uiAdapter.analysisResultForLectureListToModelList(analysis.compareTimeTargetToActual());
        for (AnalysisResultForLectureRenderModel l : results) {
            System.out.println(l.getLecture().getName());
            System.out.println("Planned SelfStudyTime: " + l.getLecture().getSelfStudyTime() + " Hours | Actual selfStudyTime: " + l.getSelfStudyTimeAndLectureTime().getSelfStudyTime());
            System.out.println("Planned lectureTime: " + l.getLecture().getLectureTime() + " Hours | Actual lectureTime: " + l.getSelfStudyTimeAndLectureTime().getLectureTime());
        }
    }

    private void getUnfinishedEntries() {
        GetEntries getEntries = new GetEntries(entryRepository);
        List<EntryRessource> unfinishedEntries = uiAdapter.mapEntryListToEntryRessourceList(getEntries.getUnfinishedEntries());
        EntryRessource entryRessource = getObjectFromNumberedList(unfinishedEntries, "no unfinished entries found");
        String end = getEndTimeForEntry();
        String details = getStringFromInputWithPrompt("Details of study");
        AdditionalEntry additionalEntry = new AdditionalEntry(entryRepository);
        additionalEntry.finishEntry(uiAdapter.mapEntryRessourceToEntry(entryRessource), uiAdapter.stringToLocalDateTime(end), details);
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

    private EntryRessourceType getEntryTypeForEntry() {
        GetEntryTypes getEntryTypesUseCase = new GetEntryTypes();
        EntryRessourceType[] entryTypes = getEntryTypesUseCase.getEntryType();
        return getObjectFromNumberedList(List.of(entryTypes), "No types found");
    }

    private LectureResource getLectureForEntry() {
        GetLectures getLectureUseCase = new GetLectures(lectureRepository);
        List<LectureResource> lectures = uiAdapter.mapLectureListToLectureListRessource(getLectureUseCase.getLectures());
        return getObjectFromNumberedList(lectures, "no lectures found. Start by creating a lecture");
    }


    private SemesterRessource getSemesterForLecture() {
        GetSemesters getSemestersUseCase = new GetSemesters(semesterRepository);
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
            e.printStackTrace();
            return false;
        }
        int inputAsInt = Integer.parseInt(input);
        return inputAsInt < expectedRange && inputAsInt >= 0;
    }


    private <T extends listeable> T getObjectFromNumberedList(List<T> list, String listIsEmptyMessage) {
        if (list.isEmpty()) {
            System.out.println(listIsEmptyMessage);
        } else {
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

}
