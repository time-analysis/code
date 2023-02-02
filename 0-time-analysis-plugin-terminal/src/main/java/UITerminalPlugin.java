import de.models.EntryType;
import useCases.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UITerminalPlugin implements UIPluginInterface {

    DataPluginInterface dataPlugin;

    public UITerminalPlugin(DataPluginInterface dataPlugin) {
        this.dataPlugin = dataPlugin;
    }

    @Override
    public void addEntryByTimeStamp() {
        //get lecture
        //create entry: start,end, type,details
        //create entry: start timer, stop timer, type, details
        //TODO besseres,direktes errorhandling (Fehlermeldung statt crash, direkt nach eingabe (nicht erst am ende)
        UIAdapter uiAdapter = new UIAdapter(this, dataPlugin);
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Start time (press \"n\" to use current time)");
        String start = scanner.next();

        if (start.equals("n")) {
            start = uiAdapter.formatLocalDateTime(LocalDateTime.now());
        }
        System.out.println("End time (press \"n\" to use current time)");
        String end = scanner.next();
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
        int typeIndex = scanner.nextInt(); //vorsicht: array out of bounds

        System.out.println("Details of study");
        String details = scanner.next();
        //show list of lectures
        System.out.println("Lecture");
        List<String> lectures = uiAdapter.getAllLecturesOfCurrentSemester();
        counter = 0;
        for (String l : lectures) {
            System.out.println(counter + ">" + l);
            counter++;
        }
        int lectureIndex = scanner.nextInt();//vorsicht: array out of bounds

        Map<String, String> data = new HashMap<>();
        data.put("Start", start);
        data.put("End", end);
        data.put("Details", details);
        data.put("Lecture", lectures.get(lectureIndex));
        data.put("Type", EntryType.values()[typeIndex].toString());

        uiAdapter.addEntryByTimeStamp(data);
    }

    public void start() {
        System.out.println("\n" + "  _______ _                                       _           _     \n" + " |__   __(_)                    /\\               | |         (_)    \n" + "    | |   _ _ __ ___   ___     /  \\   _ __   __ _| |_   _ ___ _ ___ \n" + "    | |  | | '_ ` _ \\ / _ \\   / /\\ \\ | '_ \\ / _` | | | | / __| / __|\n" + "    | |  | | | | | | |  __/  / ____ \\| | | | (_| | | |_| \\__ \\ \\__ \\\n" + "    |_|  |_|_| |_| |_|\\___| /_/    \\_\\_| |_|\\__,_|_|\\__, |___/_|___/\n" + "                                                     __/ |          \n" + "                                                    |___/           \n");
        showMainMenu();
    }

    public void showMainMenu() {
        //TODO Semester auswaehlen?
        System.out.println("Options:\n1>Create new Lecture\n2>Create new Entry");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        switch (option) {
            case "1":
                break;
            case "2":
                addEntryByTimeStamp();
                break;
            default:
                System.out.println(option + " ist keine valide Auswahl.");
        }
        showMainMenu();
    }


}
