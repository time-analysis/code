package repositories;

import Interfaces.DataAdapterInterface;
import de.models.Lecture;
import ressourceModels.LectureResource;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class LectureRepository implements LectureRepositoryInterface {

    private final DataAdapterInterface dataAdapter;
    private final String lectureFileName;

    public LectureRepository(DataAdapterInterface dataAdapter, String lectureFileName) {
        this.dataAdapter = dataAdapter;
        this.lectureFileName = lectureFileName;
    }

    @Override
    public void createLecture(Lecture lecture) {
        LectureResource lectureResource = this.dataAdapter.mapLectureToLectureRessource(lecture);
        Optional<Lecture> lectureResourceOptional = getLectureByName(sanitizeInputForCSVFormat(lectureResource.getName()));
        if (lectureResourceOptional.isPresent()) {
            throw new RuntimeException("A lecture with this name already exists");
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
    public Optional<Lecture> getLectureByName(String name) {
        return getLectures().stream().filter(lecture -> lecture.getName().equals(name)).findAny();
    }

    @Override
    public List<Lecture> getLectures() {
        List<LectureResource> lectureList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(lectureFileName))) {
            lectureList = bufferedReader.lines().map(this::parseStringToLectureRessource).collect(Collectors.toList());
        } catch (IOException e) {
            return List.of();
        }
        return this.dataAdapter.mapLectureRessourceListToLectureList(lectureList);
    }

    private String sanitizeInputForCSVFormat(String input) {
        String toReturn = input;
        if (!Objects.isNull(input)) {
            if (input.contains(",")) {
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
}
