package de.models;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
                () -> new Entry(start, EntryType.LECTURE, lecture).finishEntry(end, "details"));

    }

    @Test
    void testConstructor() {
        LocalDateTime startEnd = LocalDateTime.now();
        LocalDate now = LocalDate.now();
        Semester semester = new Semester("5. Semester", now, now);
        Lecture lecture = new Lecture("ASE", semester, 50, 10);
        Entry entry = new Entry(startEnd, EntryType.SELFSTUDY, lecture);
        entry.finishEntry(startEnd, "more information");

        assertEquals(entry.getStart(), startEnd);
        assertEquals(entry.getEnd(), startEnd);
        assertEquals(entry.getType(), EntryType.SELFSTUDY);
        assertEquals(entry.getDetails(), "more information");
        assertEquals(entry.getLecture(), lecture);
    }

    @Test
    void testCalculateDuration() {
        LocalDate now = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(LocalDate.of(2022, 10, 10), LocalTime.of(10, 0, 0, 0));
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2022, 10, 10), LocalTime.of(11, 0, 0, 0));
        Semester semester = new Semester("5. Semester", now, now);
        Lecture lecture = new Lecture("ASE", semester, 50, 10);
        Entry entry = new Entry(start, EntryType.SELFSTUDY, lecture);
        entry.finishEntry(end, "more information");
        assertEquals(entry.calculateDuration(), Duration.ofHours(1));

    }
}