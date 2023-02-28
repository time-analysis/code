package useCases;

import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;

import java.io.*;
import java.util.List;

public class DataPlugin implements DataPluginInterface {
    private String entryFileName;
    private String lectureFileName;

    public DataPlugin(String entryFileName, String lectureFileName) {
        this.entryFileName = entryFileName;
        this.lectureFileName = lectureFileName;
    }

    @Override
    public boolean persistEntry(EntryRessource entryRessource) {

        String csvEntry = String.format(
                "%s,%s,%s,%s,%s"
                , entryRessource.getStart()
                , entryRessource.getEnd()
                , entryRessource.getType()
                , entryRessource.getDetails()
                , entryRessource.getLecture());
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
    public void persistNewLecture(LectureResource lectureResource) {
        String csvLecture = String.format(
                "%s,%s,%s,%s"
                , lectureResource.getName()
                , lectureResource.getLectureTime()
                , lectureResource.getSelfStudyTime()
                , lectureResource.getSemester());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lectureFileName, true))) {
            writer.write(csvLecture);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LectureResource> getLectures() {

    }
}
