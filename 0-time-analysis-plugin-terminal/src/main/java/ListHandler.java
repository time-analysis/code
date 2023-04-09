import ressourceModels.listeable;

import java.util.List;
import java.util.Scanner;

public class ListHandler {
    private UITerminalPlugin terminalPlugin;

    public ListHandler(UITerminalPlugin terminalPlugin) {
        this.terminalPlugin = terminalPlugin;
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


    public <T extends listeable> T getObjectFromNumberedList(List<T> list, String listIsEmptyMessage) {
        if (list.isEmpty()) {
            terminalPlugin.printMessage(listIsEmptyMessage);
        } else {
            terminalPlugin.printMessage("choose an item from the list");
        }
        terminalPlugin.printMessage(getListOfObjectsAsNumberedList(list));
        Scanner scanner = terminalPlugin.getScanner();
        String index = scanner.nextLine();
        while (!isInputValidNumber(index, list.size())) {
            terminalPlugin.printMessage("invalid input, try again!");
            index = scanner.nextLine();
        }
        return list.get(Integer.parseInt(index));
    }

    private <T extends listeable> String getListOfObjectsAsNumberedList(List<T> list) {
        String toReturn = "";
        for (int i = 0; i < list.size(); i++) {
            toReturn = toReturn.concat(i + "> " + list.get(i).getDisplayName() + "\n");
        }
        return toReturn;
    }
}
