package de.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {

    @Test
    void startHasToBeBeforeEnd() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime start = LocalDateTime.parse("2022-10-30 10:00", formatter);
        LocalDateTime end = LocalDateTime.parse("2022-10-30 09:00", formatter);
        assertThrows(IllegalStateException.class,
                () -> new Entry(start, end, EntryType.PRESENCE, "test"));

    }

}