package de.models;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    @Test
    void startHasToBeBeforeEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse("2022-10-30 10:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2022-10-30 09:00", formatter);
        Semester semester = new Semester("5. Semester", LocalDate.now(), LocalDate.now());
        Lecture lecture = new Lecture("ASE", semester, 10, 20);
        assertThrows(IllegalStateException.class,
                () -> new Entry(start, end, EntryType.PRESENCE, "test", lecture));

    }

    @Test
    void testConstructor() {
        LocalDateTime startEnd = LocalDateTime.now();
        LocalDate now = LocalDate.now();
        Semester semster = new Semester("5. Semester", now, now);
        Lecture lecture = new Lecture("ASE", semster, 50, 10);
        Entry entry = new Entry(startEnd, startEnd, EntryType.STUDY, "more information", lecture);

        assertEquals(entry.getStart(), startEnd);
        assertEquals(entry.getEnd(), startEnd);
        assertEquals(entry.getType(), EntryType.STUDY);
        assertEquals(entry.getDetails(), "more information");
        assertEquals(entry.getLecture(), lecture);
    }

    @Test
    void testConstructorWithoutStartAndEnd() {
        LocalDate now = LocalDate.now();
        Semester semster = new Semester("5. Semester", now, now);
        Lecture lecture = new Lecture("ASE", semster, 50, 10);
        Entry entry = new Entry(EntryType.STUDY, "more information", lecture);

        assertTrue(Duration.between(entry.getStart(), LocalDateTime.now()).toMillis() < 10);
        assertTrue(Duration.between(entry.getEnd(), LocalDateTime.now()).toMillis() < 10);
        assertEquals(entry.getType(), EntryType.STUDY);
        assertEquals(entry.getDetails(), "more information");
        assertEquals(entry.getLecture(), lecture);
    }
}