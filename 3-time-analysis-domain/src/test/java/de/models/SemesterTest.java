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

}