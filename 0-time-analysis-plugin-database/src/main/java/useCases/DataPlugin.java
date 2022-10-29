package useCases;

import java.io.*;
import java.util.Map;

public class DataPlugin implements DataPluginInterface {
    private String fileName;

    public DataPlugin(String fileName) {
        this.fileName = fileName;
    }

    public boolean writeData(Map<String, String> data) {

        String csvEntry = String.format(
                "%s,%s,%s,%s,%s"
                , data.get("Start")
                , data.get("End")
                , data.get("Type")
                , data.get("Details")
                , data.get("Lecture"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(csvEntry);
            writer.newLine();
            writer.flush();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
