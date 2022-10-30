import useCases.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UITerminalPlugin implements UIPluginInterface {

    DataPluginInterface dataPlugin;

    public UITerminalPlugin() {
        this.dataPlugin = new DataPlugin("Entries.csv");
    }

    @Override
    public void addEntryByTimeStamp() {
        //get lecture
        //create entry: start,end, type,details
        //create entry: start timer, stop timer, type, details
        UIAdapter uiAdapter = new UIAdapter(this, dataPlugin);
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Start time");
        String start = scanner.next();
        System.out.println("End time");
        String end = scanner.next();
        //show list of type
        System.out.println("Type of study");
        String type = scanner.next();
        System.out.println("Details of study");
        String details = scanner.next();
        //show list of lectures
        System.out.println("Lecture");
        String lecture = scanner.next();

        Map<String, String> data = new HashMap<>();
        data.put("Start", start);
        data.put("End", end);
        data.put("Details", details);
        data.put("Lecture", lecture);
        data.put("Type", type);

        uiAdapter.addEntryByTimeStamp(data);

    }

    @Override
    public void showLastEntry() {

    }

    public void start() {
        System.out.println("\n" +
                "  _______ _                                       _           _     \n" +
                " |__   __(_)                    /\\               | |         (_)    \n" +
                "    | |   _ _ __ ___   ___     /  \\   _ __   __ _| |_   _ ___ _ ___ \n" +
                "    | |  | | '_ ` _ \\ / _ \\   / /\\ \\ | '_ \\ / _` | | | | / __| / __|\n" +
                "    | |  | | | | | | |  __/  / ____ \\| | | | (_| | | |_| \\__ \\ \\__ \\\n" +
                "    |_|  |_|_| |_| |_|\\___| /_/    \\_\\_| |_|\\__,_|_|\\__, |___/_|___/\n" +
                "                                                     __/ |          \n" +
                "                                                    |___/           \n");
        System.out.println("Options:\n1>Create new Lecture\n2>Create new Entry");
        Scanner scanner = new Scanner(System.in);
        String option = scanner.next();
        switch (option) {
            case "1":
                break;
            case "2":
                addEntryByTimeStamp();
        }
    }


}
