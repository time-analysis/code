import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import Interfaces.UIAdapterInterface;
import useCases.DataAdapter;
import useCases.DataPlugin;
import useCases.UIAdapter;

public class starter {
    public static void main(String[] args) {

        DataPluginInterface dataPlugin = new DataPlugin("Entries.csv", "Lectures.csv", "Semesters.csv");
        UIAdapterInterface uiAdapter = new UIAdapter(dataPlugin);
        DataAdapterInterface dataAdapter = new DataAdapter(dataPlugin);
        UITerminalPlugin terminal = new UITerminalPlugin(dataPlugin, dataAdapter, uiAdapter);
        terminal.start();
    }
}