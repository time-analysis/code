package de.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LectureTest {

    @Test
    void testLectureConstructor() {
        LocalDate now = LocalDate.now();
        Semester semster = new Semester("3. Semster", now, now);
        Lecture lecture = new Lecture("Projektmanagement", semster, 10, 30);

        assertEquals(lecture.getName(), "Projektmanagement");
        assertEquals(lecture.getSemester(), semster);
        assertEquals(lecture.getLectureTime(), 10);
        assertEquals(lecture.getSelfStudyTime(), 30);
    }

    @Test
    void testErrorIsThrownIfNameIsEmpty() {
        LocalDate now = LocalDate.now();
        Semester semster = new Semester("3. Semster", now, now);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Lecture("", semster, 10, 30));
        assertEquals("Name of a lecture can not be empty", exception.getMessage());
    }

    @Test
    void testErrorIsThrownIfPlannedTimeIsLessThanZero() {
        LocalDate now = LocalDate.now();
        Semester semster = new Semester("3. Semster", now, now);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Lecture("BWL", semster, -10, 30));
        assertEquals("Time can not be less then zero", exception.getMessage());
    }
}