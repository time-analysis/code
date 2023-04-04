package useCases;

import FilterCriteria.EntryFilterCriteria;
import Interfaces.DataPluginInterface;
import ressourceModels.*;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataPlugin implements DataPluginInterface {
    private String entryFileName;
    private String lectureFileName;
    private String semesterFileName;
    private CsvAccessor csvAccessor = new CsvAccessor();


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

    @Override
    public void persistEntry(EntryRessource entryRessource) {

        String csvEntry = String.format(
                "%s,%s,%s,%s,%s,%s"
                , sanitizeInputForCSVFormat(entryRessource.getLecture())
                , sanitizeInputForCSVFormat(entryRessource.getStart())
                , sanitizeInputForCSVFormat(entryRessource.getEnd())
                , sanitizeInputForCSVFormat(entryRessource.getType().name())
                , sanitizeInputForCSVFormat(entryRessource.getDetails())
                , entryRessource.getStatus()
        );
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(entryFileName, true))) {
            writer.write(csvEntry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void persistLecture(LectureResource lectureResource) {
        Optional<LectureResource> lectureResourceOptional = getLectureByName(sanitizeInputForCSVFormat(lectureResource.getName()));
        if (lectureResourceOptional.isPresent()) {
            System.out.println("A lecture with this name already exists"); //todo remove
            return;
        }
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
        Optional<SemesterRessource> semesterRessourceOptional = getSemesterByName(sanitizeInputForCSVFormat(semester.getName()));
        if (semesterRessourceOptional.isPresent()) {
            System.out.println("A Semester with this name already exists");
            return;
        }
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
        return getSemesters().stream().filter(semester -> semester.getName().equals(semesterName)).findAny();
    }

    @Override
    public Optional<LectureResource> getLectureByName(String lectureName) {
        return getLectures().stream().filter(lecture -> lecture.getName().equals(lectureName)).findAny();
    }

    @Override
    public List<LectureResource> getLectures() {
        List<LectureResource> lectureList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(lectureFileName))) {
            lectureList = bufferedReader.lines().map(this::parseStringToLectureRessource).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lectureList;
    }

    @Override
    public List<SemesterRessource> getSemesters() {
        List<SemesterRessource> semesterList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(semesterFileName))) {
            semesterList = bufferedReader.lines().map(this::parseStringToSemesterRessource).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return semesterList;
    }

    @Override
    public List<EntryRessource> getEntrys() {
        List<EntryRessource> entryList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(entryFileName))) {
            entryList = bufferedReader.lines().map(this::parseStringToEntryRessource).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entryList;
    }

    private String sanitizeInputForCSVFormat(String input) {
        String toReturn = input;
        if (!Objects.isNull(input)) {
            if (input.contains(",")) {
                System.out.println("The letter \",\" is not allowed as input. It will be removed"); //todo use uiplugin
                //SendUIMessageUseCase sendMessage = new SendUIMessageUesCase(uiPlugin);
                //sendMessage.send("no comma allowed");
                toReturn = toReturn.replace(",", "");
            }
        }
        return toReturn;
    }

    private LectureResource parseStringToLectureRessource(String lectureCSVInput) {
        String[] split = lectureCSVInput.split(",");
        String name = split[0];
        String semester = split[1];
        int lectureTime = Integer.parseInt(split[2]);
        int selfStudyTime = Integer.parseInt(split[3]);
        return new LectureResource(name, semester, lectureTime, selfStudyTime);
    }

    private EntryRessource parseStringToEntryRessource(String entryCSVInput) {
        String[] split = entryCSVInput.split(",");
        String lecture = split[0];
        String start = split[1];
        String end = split[2];
        String type = split[3];
        String details = split[4];
        String status = split[5];
        return new EntryRessource(start, end, EntryRessourceType.valueOf(type), details, lecture, EntryRessourceStatus.valueOf(status));
    }

    private SemesterRessource parseStringToSemesterRessource(String semesterCSVInput) {
        String[] split = semesterCSVInput.split(",");
        String name = split[0];
        String start = split[1];
        String end = split[2];
        return new SemesterRessource(name, start, end);
    }

    public List<EntryRessource> getEntrys(EntryFilterCriteria filterCriteria) {
        List<EntryRessource> entries = getEntrys();
        Stream<EntryRessource> entryRessourceStream = entries.stream();
        if (!Objects.isNull(filterCriteria.getEntryType())) {
            entryRessourceStream = entryRessourceStream.filter(entry -> entry.getType().name().equals(filterCriteria.getEntryType().name()));
        }
        if (!Objects.isNull(filterCriteria.getLectureName())) {
            entryRessourceStream = entryRessourceStream.filter(entry -> entry.getLecture().equals(filterCriteria.getLectureName()));
        }
        if (!Objects.isNull(filterCriteria.getEntryStatus())) {
            entryRessourceStream = entryRessourceStream.filter(entry -> entry.getType().name().equals(filterCriteria.getEntryStatus().name()));
        }
        return entryRessourceStream.collect(Collectors.toList());
    }

    @Override
    public void updateEntry(EntryRessource entryRessource) {
        List<EntryRessource> entryList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(entryFileName))) {
            entryList = bufferedReader.lines().map(this::parseStringToEntryRessource).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EntryRessource toReplace = entryList.stream().filter(entry -> entry.getStart().equals(entryRessource.getStart())).findFirst().get();//todo better filter
        entryList.remove(toReplace);
        entryList.add(entryRessource);
        try (PrintWriter writer = new PrintWriter(entryFileName)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (EntryRessource e : entryList) {
            persistEntry(e);
        }
    }
}
