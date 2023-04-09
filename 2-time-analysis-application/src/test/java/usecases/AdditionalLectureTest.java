package usecases;

import de.models.Lecture;
import de.models.Semester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AdditionalLectureTest {
    private LectureRepositoryMock lectureRepositoryMock;

    @BeforeEach
    void setUp() {
        lectureRepositoryMock = new LectureRepositoryMock();
    }

    @Test
    void addLectureCallsRepositoryAndPassesCorrectData() {
        AdditionalLecture additionalLecture = new AdditionalLecture(lectureRepositoryMock);
        Semester semester = new Semester("1. Semester", LocalDate.of(2023, 01, 01), LocalDate.of(2023, 01, 02));
        Lecture lecture = new Lecture("Datenbanken", semester, 10, 10);
        additionalLecture.addLecture(lecture);
        assertTrue(lectureRepositoryMock.wasCreateLectureMethodCalled());
        assertEquals(lecture, lectureRepositoryMock.getCreatedLectures().get(0));
    }
}