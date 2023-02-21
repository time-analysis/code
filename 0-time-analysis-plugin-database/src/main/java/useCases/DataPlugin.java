package useCases;

import java.io.*;
import java.util.Map;

public class DataPlugin implements DataPluginInterface {
    private String entryFileName;
    private String lectureFileName;

    public DataPlugin(String entryFileName, String lectureFileName) {
        this.entryFileName = entryFileName;
        this.lectureFileName = lectureFileName;
    }

    public boolean persistEntry(Map<String, String> data) {

        String csvEntry = String.format(
                "%s,%s,%s,%s,%s"
                , data.get("Start")
                , data.get("End")
                , data.get("Type")
                , data.get("Details")
                , data.get("Lecture"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(entryFileName, true))) {
            writer.write(csvEntry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void persistNewLecture(Map<String, String> lectureMap) {
        String csvLecture = String.format(
                "%s,%s,%s,%s"
                , lectureMap.get("name")
                , lectureMap.get("lectureTime")
                , lectureMap.get("selfStudyTime")
                , lectureMap.get("semesterName"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lectureFileName, true))) {
            writer.write(csvLecture);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
