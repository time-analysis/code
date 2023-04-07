package plugins;

import adapters.DataAdapter;
import repositories.EntryRepository;
import de.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.EntryRepositoryInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataPluginTest {

    private final String entryFileName = "EntryTest.csv";
    private final String lectureFileName = "LectureTest.csv";
    private final String semesterFileName = "SemesterTest.csv";
    private EntryRepositoryInterface entryRepository;
    private BufferedReader entryReader;

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
        entryRepository = new EntryRepository(dataAdapter, entryFileName);


        dataAdapter.setRepositories(new SemesterRepositoryMock(), new LectureRepositoryMock());
        try {
            entryReader = new BufferedReader(new FileReader(entryFileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void createEntryWritesEntryToFile() {
        LocalDateTime time = LocalDateTime.now();

        entryRepository.createEntry(new Entry(LocalDateTime.of(2023, 1, 1, 10, 0), LocalDateTime.of(2023, 1, 1, 12, 0), EntryType.LECTURE, new Lecture("Testvorlesung", new Semester("meinSemester", time.toLocalDate(), time.toLocalDate()), 10, 10), "weitere Informationen", UUID.fromString("06c7b333-2965-4399-a8fc-d10e0a8806fe")));
        String assumed = "Testvorlesung,2023-01-01 10:00,2023-01-01 12:00,LECTURE,weitere Informationen,FINISHED,06c7b333-2965-4399-a8fc-d10e0a8806fe";
        String fromFile;
        try {
            fromFile = entryReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(assumed, fromFile);
    }
}