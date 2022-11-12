package de.models;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LectureTest {

    @Test
    void testLectureConstructor() {
        LocalDate now = LocalDate.now();
        Semester semster = new Semester("3. Semster", now, now);
        Lecture lecture = new Lecture("Projektmanagement", semster, 10, 30);

        assertEquals(lecture.getName(), "Projektmanagement");
        assertEquals(lecture.getSemster(), semster);
        assertEquals(lecture.getPresence(), 10);
        assertEquals(lecture.getStudy(), 30);
    }
}