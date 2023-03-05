import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIAdapterInterface;
import Interfaces.UIPluginInterface;
import de.models.EntryType;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;
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

    @Override
    public void addEntryByTimeStamp() {
        //get lecture
        //create entry: start,end, type,details
        //create entry: start timer, stop timer, type, details


        System.out.println("Start time (press \"n\" to use current time)");
        String start = scanner.nextLine();

        if (start.equals("n")) {
            start = uiAdapter.formatLocalDateTime(LocalDateTime.now());
        }
        System.out.println("End time (press \"n\" to use current time)");
        String end = scanner.nextLine();
        if (end.equals("n")) {
            end = uiAdapter.formatLocalDateTime(LocalDateTime.now());
        }
        System.out.println("Type of study");
        int counter = 0;
        for (EntryType type : EntryType.values()) {
            System.out.println(counter + ">" + type.toString());
            counter++;
        }
        //todo get data from adapter

        String typeIndex = scanner.nextLine();
        while (!isInputValidNumber(typeIndex, EntryType.values().length)) {
            System.out.println("invalid input, try again!");
            typeIndex = scanner.nextLine();
        }

        System.out.println("Details of study");
        String details = scanner.nextLine();

        List<LectureResource> lectures = uiAdapter.getAllLecturesOfCurrentSemester();

        LectureResource lecture = getLectureRessourceFromNumberedList(lectures);

        EntryRessource entryRessource = new EntryRessource(start, end, EntryType.values()[Integer.parseInt(typeIndex)].name(), details, lecture.getName());
        AdditionalEntry additionalEntry = new AdditionalEntry(dataAdapter, dataPlugin);
        additionalEntry.addEntry(this.uiAdapter.mapEntryRessourceToEntry(entryRessource), this.uiAdapter.mapLectureRessourceToLecture(lecture));
    }

    @Override
    public void addLecture() {
        //strings einlesen:   name, semester, lectureTime, selfStudyTime
        String name, lectureTime, selfStudyTime;
        System.out.println("Name of the lecture:");
        name = scanner.nextLine();
        System.out.println("choose the semester");

        List<SemesterRessource> semesterList = uiAdapter.getAllSemesters();
        SemesterRessource semester = getSemesterRessourceFromNumberedList(semesterList);

        System.out.println("enter the official lecture time");
        lectureTime = scanner.nextLine();
        System.out.println("enter the ammount of time you are supposed to study on your own");
        selfStudyTime = scanner.nextLine();

        LectureResource lectureResource = new LectureResource(name, semester.getName(), Integer.parseInt(lectureTime), Integer.parseInt(selfStudyTime));
        AdditionalLecture additionalLecture = new AdditionalLecture(dataAdapter, dataPlugin, this);
        additionalLecture.addLecture(this.uiAdapter.mapLectureRessourceToLecture(lectureResource));
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

    public void start() {
        System.out.println("\n" + "  _______ _                                       _           _     \n" + " |__   __(_)                    /\\               | |         (_)    \n" + "    | |   _ _ __ ___   ___     /  \\   _ __   __ _| |_   _ ___ _ ___ \n" + "    | |  | | '_ ` _ \\ / _ \\   / /\\ \\ | '_ \\ / _` | | | | / __| / __|\n" + "    | |  | | | | | | |  __/  / ____ \\| | | | (_| | | |_| \\__ \\ \\__ \\\n" + "    |_|  |_|_| |_| |_|\\___| /_/    \\_\\_| |_|\\__,_|_|\\__, |___/_|___/\n" + "                                                     __/ |          \n" + "                                                    |___/           \n");
        showMainMenu();
    }

    public void showMainMenu() {
        System.out.println("Options:\n1>Create new Lecture\n2>Create new Entry\n3>Create new Semester\n4>see time spent for a Lecture\n5>see how much time was spent listening to lectures\n6>see how much time was spent studying on your own\n7>see how much time was spent in a semester");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                addLecture();
                break;
            case "2":
                addEntryByTimeStamp();
                break;
            case "3":
                addSemester();
                break;
            case "4":
                getTimePerLecture();
                break;
            case "5":
                getPresenceTime();
                break;
            case "6":
                getSelfStudyTime();
                break;
            case "7":
                getTimePerSemester();
                break;
            default:
                System.out.println(option + " is not a valid option.");
        }
        showMainMenu();
    }

    private void getTimePerSemester() {

        List<SemesterRessource> semesterList = uiAdapter.getAllSemesters();
        SemesterRessource semester = getSemesterRessourceFromNumberedList(semesterList);
        Analysis analysis = new Analysis(dataAdapter, dataPlugin);
        Duration duration = analysis.getTimePerSemester(dataAdapter.mapSemesterRessourceToSemester(semester));
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
        List<LectureResource> lectures = uiAdapter.getAllLecturesOfCurrentSemester();
        LectureResource lecture = getLectureRessourceFromNumberedList(lectures);

        analysis.getTimeSpentForLecture(dataAdapter.mapLectureRessourceToLecture(lecture));
    }

    private void addSemester() {
        System.out.println("name of semester:");
        String name = scanner.nextLine();
        System.out.println("start date");
        String start = scanner.nextLine();
        System.out.println("end date");
        String end = scanner.nextLine();

        AdditionalSemester additionalSemester = new AdditionalSemester(dataAdapter, dataPlugin, this);
        SemesterRessource semesterRessource = new SemesterRessource(name, start, end);
        additionalSemester.addSemester(dataAdapter.mapSemesterRessourceToSemester(semesterRessource));
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

    private String getListOfSemestersAsNumberedList(List<SemesterRessource> semesters) {
        String listAsString = "";
        if (semesters.isEmpty()) {
            return "No semesters found. Please create a semester";
        }
        for (int i = 0; i < semesters.size(); i++) {
            listAsString = listAsString.concat("\n" + i + "> " + semesters.get(i).getName());
        }
        return listAsString;
    }

    private SemesterRessource getSemesterRessourceFromNumberedList(List<SemesterRessource> semesters) {
        System.out.println("choose a semester from the list");
        System.out.println(getListOfSemestersAsNumberedList(semesters));

        String lectureIndex = scanner.nextLine();
        while (!isInputValidNumber(lectureIndex, semesters.size())) {
            System.out.println("invalid input, try again!");
            lectureIndex = scanner.nextLine();
        }
        return semesters.get(Integer.parseInt(lectureIndex));
    }

    private String getListOfLecturesAsNumberedList(List<LectureResource> lectures) {
        String listAsString = "";
        if (lectures.isEmpty()) {
            return "No lectures found. Please create a lecture";
        }
        for (int i = 0; i < lectures.size(); i++) {
            listAsString = listAsString.concat("\n" + i + "> " + lectures.get(i).getName());
        }
        return listAsString;
    }

    private LectureResource getLectureRessourceFromNumberedList(List<LectureResource> lectures) {
        System.out.println("choose a lecture from the list");
        System.out.println(getListOfLecturesAsNumberedList(lectures));

        String lectureIndex = scanner.nextLine();
        while (!isInputValidNumber(lectureIndex, lectures.size())) {
            System.out.println("invalid input, try again!");
            lectureIndex = scanner.nextLine();
        }
        return lectures.get(Integer.parseInt(lectureIndex));
    }

}
