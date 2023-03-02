package useCases;

import Interfaces.DataPluginInterface;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataPlugin implements DataPluginInterface {
    private String entryFileName;
    private String lectureFileName;
    private String semesterFileName;

    public DataPlugin(String entryFileName, String lectureFileName, String semesterFileName) {
        this.entryFileName = entryFileName;
        this.lectureFileName = lectureFileName;
        this.semesterFileName = semesterFileName;
    }

    //todo check if files exists
    @Override
    public boolean persistEntry(EntryRessource entryRessource) {

        String csvEntry = String.format(
                "%s,%s,%s,%s,%s"
                , entryRessource.getLecture()
                , entryRessource.getStart()
                , entryRessource.getEnd()
                , entryRessource.getType()
                , entryRessource.getDetails()
        );
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
    public void persistLecture(LectureResource lectureResource) {
        String csvLecture = String.format(
                "%s,%s,%s,%s"
                , lectureResource.getName()
                , lectureResource.getSemester()
                , lectureResource.getLectureTime()
                , lectureResource.getSelfStudyTime());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lectureFileName, true))) {
            writer.write(csvLecture);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void persistSemester(SemesterRessource semester) {
        String csvSemester = String.format(
                "%s,%s,%s"
                , semester.getName()
                , semester.getStart()
                , semester.getEnd());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(semesterFileName, true))) {
            writer.write(csvSemester);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<SemesterRessource> getSemesterByName(String semesterName) {
        //todo implement
        return Optional.empty();
    }

    @Override
    public Optional<LectureResource> getLectureByName(String lectureName) {
        LectureResource lectureResource = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(lectureFileName))) {
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            for (String line : lines) {
                String[] split = line.split(",");
                String name = split[0];
                if (name.equals(lectureName)) {
                    String semester = split[1];
                    int lectureTime = Integer.parseInt(split[2]);
                    int selfStudyTime = Integer.parseInt(split[3]);
                    lectureResource = new LectureResource(name, semester, lectureTime, selfStudyTime);
                }
            }
            if (Objects.isNull(lectureResource)) {
                return Optional.empty();
            } else {
                return Optional.of(lectureResource);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LectureResource> getLectures() {
        List<LectureResource> lectureList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(lectureFileName))) {
            bufferedReader.lines().forEach(line -> {
                String[] split = line.split(",");
                String name = split[0];
                String semester = split[1];
                int lectureTime = Integer.parseInt(split[2]);
                int selfStudyTime = Integer.parseInt(split[3]);
                lectureList.add(new LectureResource(name, semester, lectureTime, selfStudyTime));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lectureList;
    }

    @Override
    public List<SemesterRessource> getSemesters() {
        List<SemesterRessource> semesterList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(semesterFileName))) {
            bufferedReader.lines().forEach(line -> {
                String[] split = line.split(",");
                String name = split[0];
                String start = split[1];
                String end = split[2];
                semesterList.add(new SemesterRessource(name, start, end));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return semesterList;
    }
}
