package plugins;

import adapters.DataAdapter;
import de.models.*;
import filterCriteria.EntryFilterCriteria;
import filterCriteria.EntryFilterCriteriaBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.EntryRepository;
import repositories.EntryRepositoryInterface;
import repositories.LectureRepositoryInterface;
import repositories.SemesterRepositoryInterface;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntryRepositoryTest {

    private final String entryFileName = "EntryTest.csv";
    private final String lectureFileName = "LectureTest.csv";
    private final String semesterFileName = "SemesterTest.csv";
    private EntryRepositoryInterface entryRepository;
    private BufferedReader entryReader;
    private SemesterRepositoryInterface semesterRepository;
    private LectureRepositoryInterface lectureRepository;

    LocalDate date = LocalDate.of(2022, 12, 12);
    LocalDateTime start = LocalDateTime.of(2022, 10, 10, 10, 10);
    LocalDateTime end = LocalDateTime.of(2022, 10, 10, 12, 10);
    Semester semester = new Semester("meinSemester", date, date);
    Lecture db = new Lecture("Datenbanken1", semester, 10, 10);
    Lecture pm = new Lecture("Projektmanagement", semester, 10, 10);
    Entry finishedPMLectureEntry = new Entry(start, end, EntryType.LECTURE, pm, "details");
    Entry finishedPMSelfStudyEntry = new Entry(start, end, EntryType.SELFSTUDY, pm, "details");
    Entry runningDBLectureEntry = new Entry(start, EntryType.LECTURE, db);
    Entry runningDBSelfStudyEntry = new Entry(start, EntryType.SELFSTUDY, db);


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

        semesterRepository = new SemesterRepositoryMock();
        lectureRepository = new LectureRepositoryMock();
        dataAdapter.setRepositories(semesterRepository, lectureRepository);
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

    @Test
    void testFilterForName() {
        loadSampleData();
        EntryFilterCriteriaBuilder builder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = builder.withLectureName("Datenbanken1").build();
        List<Entry> entries = this.entryRepository.getEntrys(criteria);
        assertTrue(entries.contains(runningDBLectureEntry));
        assertTrue(entries.contains(runningDBSelfStudyEntry));
        assertEquals(2, entries.size());
    }

    @Test
    void testFilterForStatus() {
        loadSampleData();
        EntryFilterCriteriaBuilder builder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = builder.withStatus(EntryStatus.FINISHED).build();
        List<Entry> entries = this.entryRepository.getEntrys(criteria);
        assertEquals(2, entries.size());
        assertTrue(entries.contains(finishedPMLectureEntry));
        assertTrue(entries.contains(finishedPMSelfStudyEntry));
    }

    @Test
    void testFilterForType() {
        loadSampleData();
        EntryFilterCriteriaBuilder builder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = builder.withType(EntryType.LECTURE).build();
        List<Entry> entries = this.entryRepository.getEntrys(criteria);
        assertEquals(2, entries.size());
        assertTrue(entries.contains(finishedPMLectureEntry));
        assertTrue(entries.contains(runningDBLectureEntry));
    }

    @Test
    void testFilterWithNoCriteriaReturnsAllEntries() {
        loadSampleData();
        EntryFilterCriteriaBuilder builder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = builder.build();
        List<Entry> entries = this.entryRepository.getEntrys(criteria);
        assertEquals(4, entries.size());
        assertTrue(entries.contains(finishedPMLectureEntry));
        assertTrue(entries.contains(finishedPMSelfStudyEntry));
        assertTrue(entries.contains(runningDBLectureEntry));
        assertTrue(entries.contains(runningDBSelfStudyEntry));
    }

    @Test
    void testFilterWithNotExistingCriteriaReturnsNoEntries() {
        loadSampleData();
        EntryFilterCriteriaBuilder builder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = builder.withLectureName("DieseVorlesungGibtEsNicht").build();
        List<Entry> entries = this.entryRepository.getEntrys(criteria);
        assertTrue(entries.isEmpty());
    }

    @Test
    void testFilterWithCombinedCriteria() {
        loadSampleData();
        EntryFilterCriteriaBuilder builder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = builder.withLectureName("Projektmanagement").withType(EntryType.LECTURE).build();
        List<Entry> entries = this.entryRepository.getEntrys(criteria);
        assertEquals(1, entries.size());
        assertTrue(entries.contains(finishedPMLectureEntry));
    }

    private void loadSampleData() {
        this.semesterRepository.createSemester(semester);
        this.lectureRepository.createLecture(db);
        this.lectureRepository.createLecture(pm);
        this.entryRepository.createEntry(finishedPMLectureEntry);
        this.entryRepository.createEntry(finishedPMSelfStudyEntry);
        this.entryRepository.createEntry(runningDBLectureEntry);
        this.entryRepository.createEntry(runningDBSelfStudyEntry);
    }

}