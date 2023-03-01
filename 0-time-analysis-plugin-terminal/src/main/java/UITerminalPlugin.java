import Interfaces.DataPluginInterface;
import Interfaces.UIPluginInterface;
import de.models.EntryType;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import useCases.*;

import java.time.LocalDateTime;
import java.util.*;

public class UITerminalPlugin implements UIPluginInterface {

    private DataPluginInterface dataPlugin;
    private UIAdapter uiAdapter;
    private Scanner scanner;
    private AdditionalLecture additionalLecture;
    private AdditionalEntry additionalEntry;

    public UITerminalPlugin(DataPluginInterface dataPlugin, AdditionalEntry additionalEntry, AdditionalLecture additionalLecture) {
        this.dataPlugin = dataPlugin;
        this.uiAdapter = new UIAdapter(this, this.dataPlugin);//todo im konstruktor reingeben??
        this.scanner = new Scanner(System.in);
        this.additionalEntry = additionalEntry;
        this.additionalLecture = additionalLecture;

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
        //show list of lectures
        System.out.println("Lecture");
        List<LectureResource> lectures = uiAdapter.getAllLecturesOfCurrentSemester();
        counter = 0;
        for (LectureResource l : lectures) {
            System.out.println(counter + ">" + l.getName());
            counter++;
        }

        String lectureIndex = scanner.nextLine();
        while (!isInputValidNumber(lectureIndex, lectures.size())) {
            System.out.println("invalid input, try again!");
            lectureIndex = scanner.nextLine();
        }

        EntryRessource entryRessource = new EntryRessource(start, end, EntryType.values()[Integer.parseInt(typeIndex)], details, lectures.get(Integer.parseInt(lectureIndex)));
        this.additionalEntry.addEntry(this.uiAdapter.mapEntryRessourceToEntry(entryRessource), lec);
        Map<String, String> data = new HashMap<>();
        data.put("Start", start);
        data.put("End", end);
        data.put("Details", details);
        data.put("Lecture", lectures.get(Integer.parseInt(lectureIndex)));
        data.put("Type", EntryType.values()[Integer.parseInt(typeIndex)].toString());
        uiAdapter.addEntryByTimeStamp(data);
    }

    @Override
    public void addLecture() {
        //strings einlesen:   name, semester, lectureTime, selfStudyTime
        String name, semester, lectureTime, selfStudyTime;
        System.out.println("Name of the lecture:");
        name = scanner.nextLine();
        System.out.println("choose the semester"); //todo liste von semestern ausgeben
        semester = scanner.nextLine();
        System.out.println("enter the official lecture time");
        lectureTime = scanner.nextLine();
        System.out.println("enter the ammount of time you are supposed to study on your own");
        selfStudyTime = scanner.nextLine();

        LectureResource lectureResource = new LectureResource(name, semester, Integer.parseInt(lectureTime), Integer.parseInt(selfStudyTime)); //todo hier eher mit String für zeitangabe oder als int?
        this.additionalLecture.addLecture(this.uiAdapter.mapLectureRessourceToLecture(lectureResource)); //todo so richtig?
    }

    public void start() {
        System.out.println("\n" + "  _______ _                                       _           _     \n" + " |__   __(_)                    /\\               | |         (_)    \n" + "    | |   _ _ __ ___   ___     /  \\   _ __   __ _| |_   _ ___ _ ___ \n" + "    | |  | | '_ ` _ \\ / _ \\   / /\\ \\ | '_ \\ / _` | | | | / __| / __|\n" + "    | |  | | | | | | |  __/  / ____ \\| | | | (_| | | |_| \\__ \\ \\__ \\\n" + "    |_|  |_|_| |_| |_|\\___| /_/    \\_\\_| |_|\\__,_|_|\\__, |___/_|___/\n" + "                                                     __/ |          \n" + "                                                    |___/           \n");
        showMainMenu();
    }

    public void showMainMenu() {
        //TODO Semester auswaehlen?
        System.out.println("Options:\n1>Create new Lecture\n2>Create new Entry");
        String option = scanner.nextLine();
        switch (option) {
            case "1":
                addLecture();
                break;
            case "2":
                addEntryByTimeStamp();
                break;
            default:
                System.out.println(option + " ist keine valide Auswahl.");
        }
        showMainMenu();
    }


    private boolean isInputValidNumber(String input, int expectedRange) {
        //todo ist das hier richtig oder gehört das in den adapter oder woanders hin??
        try {
            Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        int inputAsInt = Integer.parseInt(input);
        return inputAsInt < expectedRange && inputAsInt >= 0;
    }


}
