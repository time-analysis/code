package de.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void throwsErrorIfNameIsNull() {
        LocalDate end = LocalDate.of(2023, 12, 07);
        LocalDate start = LocalDate.of(2023, 01, 13);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Semester(null, start, end));
        assertEquals("name can not be null", exception.getMessage());
    }

    @Test
    void throwsErrorIfStartIsNull() {
        LocalDate end = LocalDate.of(2023, 12, 07);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Semester("dasBesteSemester", null, end));
        assertEquals("start can not be null", exception.getMessage());
    }

    @Test
    void throwsErrorIfEndIsNull() {
        LocalDate start = LocalDate.of(2023, 01, 13);
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Semester("superTollesSemester", start, null));
        assertEquals("end can not be null", exception.getMessage());
    }
}