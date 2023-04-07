package useCases;

import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.EntryRepositoryInterface;
import repositories.LectureRepositoryInterface;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalysisTest {
    LectureRepositoryInterface lectureRepository;
    EntryRepositoryMock entryRepository;

    @BeforeEach
    public void createMocks(){
        this.lectureRepository = new LectureRepositoryMock();
        this.entryRepository = new EntryRepositoryMock();
    }

    @Test
    public void selfStudyTimeIsCalculatedCorrectly() {
        Analysis analysis = new Analysis(entryRepository,lectureRepository);
        Semester semester = new Semester("meinSemester", LocalDate.now(), LocalDate.now());
        Lecture lecture = new Lecture("MeineVL", semester, 10, 10);
        Entry entry = new Entry(LocalDateTime.of(2023, 1, 1, 10, 0), EntryType.SELFSTUDY, lecture);
        entry.finishEntry(LocalDateTime.of(2023, 1, 1, 12, 0), "details");
        List<Entry> entryList = List.of(entry);
        entryRepository.addListOfEntryToReturnList(entryList);


        Duration selfStudyTime = analysis.getStudyTime();

        assertEquals(selfStudyTime, Duration.ofHours(2));
    }

    @Test
    public void selfStudyTimeIsZeroWhenNoStudyTimeIsGiven() {
        Analysis analysis = new Analysis(entryRepository,lectureRepository);
        entryRepository.addListOfEntryToReturnList(List.of());

        Duration selfStudyTime = analysis.getStudyTime();

        assertEquals(selfStudyTime, Duration.ofHours(0));
    }
}
