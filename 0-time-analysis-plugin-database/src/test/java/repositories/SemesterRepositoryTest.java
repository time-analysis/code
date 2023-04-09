package repositories;

import adapters.DataAdapter;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import plugins.LectureRepositoryMock;
import plugins.SemesterRepositoryMock;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SemesterRepositoryTest {

    private final String entryFileName = "EntryTest.csv";
    private final String lectureFileName = "LectureTest.csv";
    private final String semesterFileName = "SemesterTest.csv";
    private SemesterRepositoryInterface semesterRepository;
    private BufferedReader semesterReader;

    @BeforeEach
    void setup() {
        List<File> files = List.of(new File(entryFileName), new File(lectureFileName), new File(semesterFileName));
        PrintWriter writer = null;
        for (File file : files) {
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            writer.print("");
            writer.close();
        }
        DataAdapter dataAdapter = new DataAdapter();
        semesterRepository = new SemesterRepository(dataAdapter, semesterFileName);

        dataAdapter.setRepositories(semesterRepository, new LectureRepositoryMock());
        try {
            semesterReader = new BufferedReader(new FileReader(semesterFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    void TestCreateSemesterWritesToFile() {
        semesterRepository.createSemester(new Semester("meinSemester", LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 02)));
        String assumed = "meinSemester,2022-01-01,2022-01-02";
        String fromFile;
        try {
            fromFile = semesterReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(assumed, fromFile);
    }

    @Test
    void TestGetSemestersReadsFromFileAndParsesCorrectly() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(semesterFileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.print("");
        writer.print("DHBWSemester,2022-03-03,2022-05-07");
        writer.close();
        List<Semester> semesters = semesterRepository.getSemesters();
        assertEquals(new Semester("DHBWSemester", LocalDate.of(2022, 03, 03), LocalDate.of(2022, 05, 07)), semesters.get(0));
    }
}