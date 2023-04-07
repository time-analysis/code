package repositories;

import Interfaces.DataAdapterInterface;
import de.models.Semester;
import ressourceModels.SemesterRessource;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SemesterRepository implements SemesterRepositoryInterface {
    private final DataAdapterInterface dataAdapter;
    private final String semesterFileName;

    public SemesterRepository(DataAdapterInterface dataAdapter, String semesterFileName) {
        this.dataAdapter = dataAdapter;
        this.semesterFileName = semesterFileName;
    }

    @Override
    public void createSemester(Semester semester) {
        SemesterRessource semesterRessource = dataAdapter.mapSemesterToSemesterRessource(semester);
        Optional<Semester> semesterRessourceOptional = getSemesterByName(sanitizeInputForCSVFormat(semester.getName()));
        if (semesterRessourceOptional.isPresent()) {
            System.out.println("A Semester with this name already exists");
            return;
        }
        String csvSemester = String.format(
                "%s,%s,%s"
                , sanitizeInputForCSVFormat(semesterRessource.getName())
                , sanitizeInputForCSVFormat(semesterRessource.getStart())
                , sanitizeInputForCSVFormat(semesterRessource.getEnd()));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(semesterFileName, true))) {
            writer.write(csvSemester);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Semester> getSemesterByName(String semesterName) {
        return getSemesters().stream().filter(semester -> semester.getName().equals(semesterName)).findAny();
    }

    @Override
    public List<Semester> getSemesters() {
        List<SemesterRessource> semesterList;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(semesterFileName))) {
            semesterList = bufferedReader.lines().map(this::parseStringToSemesterRessource).collect(Collectors.toList());
        } catch (IOException e) {
            return List.of();
        }
        return this.dataAdapter.mapSemesterRessourceListToSemesterList(semesterList);
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

    private SemesterRessource parseStringToSemesterRessource(String semesterCSVInput) {
        String[] split = semesterCSVInput.split(",");
        String name = split[0];
        String start = split[1];
        String end = split[2];
        return new SemesterRessource(name, start, end);
    }
}
