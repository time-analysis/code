package usecases;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import transferModels.AnalysisResultForLecture;
import transferModels.SelfStudyTimeAndLectureTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalysisTest {
    LectureRepositoryMock lectureRepository;
    EntryRepositoryMock entryRepository;

    @BeforeEach
    public void createMocks() {
        this.lectureRepository = new LectureRepositoryMock();
        this.entryRepository = new EntryRepositoryMock();
    }

    @Test
    public void selfStudyTimeIsCalculatedCorrectly() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        Semester semester = new Semester("meinSemester", LocalDate.now(), LocalDate.now());
        Lecture lecture = new Lecture("MeineVL", semester, 10, 10);
        Entry entry = new Entry(LocalDateTime.of(2023, 1, 1, 10, 0), EntryType.SELFSTUDY, lecture);
        entry.finishEntry(LocalDateTime.of(2023, 1, 1, 12, 0), "details");
        List<Entry> entryList = List.of(entry);
        entryRepository.addListOfEntryToReturnList(entryList);


        Duration selfStudyTime = analysis.getStudyTime();

        assertEquals(Duration.ofHours(2), selfStudyTime);
    }

    @Test
    public void selfStudyTimeIsZeroWhenNoStudyTimeIsGiven() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        entryRepository.addListOfEntryToReturnList(List.of());

        Duration selfStudyTime = analysis.getStudyTime();

        assertEquals(Duration.ofHours(0), selfStudyTime);
    }

    @Test
    public void analysisResultsAreAssembledCorrectly() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        Semester semester = new Semester("meinSemester", LocalDate.now(), LocalDate.now());
        Lecture lecture1 = new Lecture("DB2", semester, 10, 10);
        Lecture lecture2 = new Lecture("WebServices", semester, 10, 10);
        lectureRepository.addListOfLectureToReturnList(List.of(lecture1, lecture2));
        Entry entryForLecture1 = new Entry(LocalDateTime.of(2023, 1, 1, 10, 0), LocalDateTime.of(2023, 1, 1, 12, 0), EntryType.SELFSTUDY, lecture1, "details");
        entryRepository.addListOfEntryToReturnList(List.of(entryForLecture1));
        entryRepository.addListOfEntryToReturnList(List.of());
        entryRepository.addListOfEntryToReturnList(List.of());
        entryRepository.addListOfEntryToReturnList(List.of());

        List<AnalysisResultForLecture> resultList = analysis.compareTimeTargetToActual();

        assertEquals(Duration.ofHours(2), resultList.get(0).getSelfStudyTimeAndLectureTime().getSelfStudyTime());
        assertEquals(Duration.ofHours(0), resultList.get(0).getSelfStudyTimeAndLectureTime().getLectureTime());
        assertEquals(lecture1, resultList.get(0).getLecture());

        assertEquals(Duration.ofHours(0), resultList.get(1).getSelfStudyTimeAndLectureTime().getSelfStudyTime());
        assertEquals(Duration.ofHours(0), resultList.get(1).getSelfStudyTimeAndLectureTime().getLectureTime());
        assertEquals(lecture2, resultList.get(1).getLecture());
    }

    @Test
    public void TimeSpentForLectureIsCalculatedCorrectly() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        Semester semester = new Semester("meinSemester", LocalDate.now(), LocalDate.now());
        Lecture lecture1 = new Lecture("DB2", semester, 10, 10);
        Entry entryForLecture1 = new Entry(LocalDateTime.of(2023, 1, 1, 10, 0), LocalDateTime.of(2023, 1, 1, 12, 0), EntryType.SELFSTUDY, lecture1, "details");
        Entry secondEntryForLecture1 = new Entry(LocalDateTime.of(2023, 1, 1, 8, 0), LocalDateTime.of(2023, 1, 1, 12, 0), EntryType.LECTURE, lecture1, "details");
        entryRepository.addListOfEntryToReturnList(List.of(entryForLecture1));
        entryRepository.addListOfEntryToReturnList(List.of(secondEntryForLecture1));

        SelfStudyTimeAndLectureTime result = analysis.getTimeSpentForLecture("DB2");

        assertEquals(Duration.ofHours(2), result.getSelfStudyTime());
        assertEquals(Duration.ofHours(4), result.getLectureTime());
    }

    @Test
    public void TimeSpentForLectureIsZeroWhenNoEntriesAreFound() {
        Analysis analysis = new Analysis(entryRepository, lectureRepository);
        entryRepository.addListOfEntryToReturnList(List.of());
        entryRepository.addListOfEntryToReturnList(List.of());

        SelfStudyTimeAndLectureTime result = analysis.getTimeSpentForLecture("DB2");

        assertEquals(Duration.ofHours(0), result.getSelfStudyTime());
        assertEquals(Duration.ofHours(0), result.getLectureTime());
    }
}
