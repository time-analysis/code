package de.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
}