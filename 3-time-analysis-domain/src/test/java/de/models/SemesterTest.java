package de.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SemesterTest {

    @Test
    void testSemesterConstructor() {
        LocalDate now = LocalDate.now();
        String name = "1. Semester";
        Semester semester = new Semester(name, now, now);

        assertEquals(semester.getName(), name);
        assertEquals(semester.getStart(), now);
        assertEquals(semester.getEnd(), now);
    }

    @Test
    void throwsErrorIfStartIsAfterEnd() {
        String semesterWithStartAfterEnd = "SemesterWithStartAfterEnd";
        LocalDate start = LocalDate.of(2023, 01, 31);
        LocalDate end = LocalDate.of(2022, 01, 29);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Semester(semesterWithStartAfterEnd, start, end));
        assertEquals("start cannot be after end", exception.getMessage());
    }

    @Test
    void throwsErrorIfNoNameIsGiven() {
        LocalDate end = LocalDate.of(2023, 12, 07);
        LocalDate start = LocalDate.of(2023, 01, 13);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Semester("", start, end));
        assertEquals("name must contain at least one letter", exception.getMessage());
    }
}