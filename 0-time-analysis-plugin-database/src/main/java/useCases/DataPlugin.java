package useCases;

import Interfaces.DataPluginInterface;
import Interfaces.UIPluginInterface;
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
        File semesterFile = new File(semesterFileName);
        File lectureFile = new File(lectureFileName);
        if (!semesterFile.exists() || !semesterFile.isFile()) {
            System.out.println("No semesters found, start by creating a semester."); //todo use uiplugin to display
        } else if (!lectureFile.exists() || !lectureFile.isFile()) {
            System.out.println("No lectures found. Lectures are required to created an Entry."); //todo use uiplugin to display
        }

    }

    //todo check for dupliate lecture and duplicate Semester (name has to be unique)
    @Override
    public boolean persistEntry(EntryRessource entryRessource) {

        String csvEntry = String.format(
                "%s,%s,%s,%s,%s"
                , sanitizeInputForCSVFormat(entryRessource.getLecture())
                , sanitizeInputForCSVFormat(entryRessource.getStart())
                , sanitizeInputForCSVFormat(entryRessource.getEnd())
                , sanitizeInputForCSVFormat(entryRessource.getType())
                , sanitizeInputForCSVFormat(entryRessource.getDetails())
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
                , sanitizeInputForCSVFormat(lectureResource.getName())
                , sanitizeInputForCSVFormat(lectureResource.getSemester())
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
                , sanitizeInputForCSVFormat(semester.getName())
                , sanitizeInputForCSVFormat(semester.getStart())
                , sanitizeInputForCSVFormat(semester.getEnd()));
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
        SemesterRessource semesterResource = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(semesterFileName))) {
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            for (String line : lines) {
                String[] split = line.split(",");
                String name = split[0];
                if (name.equals(semesterName)) {
                    String start = split[1];
                    String end = split[2];
                    semesterResource = new SemesterRessource(name, start, end);
                }
            }
            if (Objects.isNull(semesterResource)) {
                return Optional.empty();
            } else {
                return Optional.of(semesterResource);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public List<EntryRessource> getEntrysByLectureName(String lectureResource) {

        List<EntryRessource> EntryList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(entryFileName))) {
            bufferedReader.lines().forEach(line -> {
                String[] split = line.split(",");
                String lecture = split[0];
                if (lecture.equals(lectureResource)) {
                    String start = split[1];
                    String end = split[2];
                    String type = split[3];
                    String details = split[4];
                    EntryList.add(new EntryRessource(lecture, start, end, type, details));
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return EntryList;
    }

    @Override
    public List<EntryRessource> getEntrys() {
        List<EntryRessource> EntryList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(entryFileName))) {
            bufferedReader.lines().forEach(line -> {
                String[] split = line.split(",");
                String lecture = split[0];
                String start = split[1];
                String end = split[2];
                String type = split[3];
                String details = split[4];
                EntryList.add(new EntryRessource(start, end, type, details, lecture));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return EntryList;
    }

    private String sanitizeInputForCSVFormat(String input) {
        String toReturn = input;
        if (input.contains(",")) {
            System.out.println("The letter \",\" is not allowed as input. It will be removed"); //todo use uiplugin
            toReturn = toReturn.replace(",", "");
        }
        return toReturn;
    }
}
