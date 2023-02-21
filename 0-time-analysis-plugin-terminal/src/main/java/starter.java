import useCases.DataPlugin;

public class starter {
    public static void main(String[] args) {
        UITerminalPlugin terminal = new UITerminalPlugin(new DataPlugin("Entries.csv", "Lectures.csv"));
        terminal.start();
    }
}