package useCases;

import java.io.*;
import java.util.Map;

public class DataPlugin implements DataPluginInterface {
    private File file = new File("Entries.csv");
    private BufferedWriter writer;

    public DataPlugin() {
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWriter(BufferedWriter writer){
        this.writer = writer;
    }

    public boolean writeData(Map<String, String> data) {
        String csvEntry = String.format(
                "%s,%s,%s,%s,%s"
                , data.get("Start")
                , data.get("End")
                , data.get("Type")
                , data.get("Details")
                , data.get("Lecture"));
        try {
            writer.write(csvEntry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
