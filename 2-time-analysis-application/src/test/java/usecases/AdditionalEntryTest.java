package usecases;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdditionalEntryTest {

    @Test
    void EntryIsAddedToLecture() {
        Semester semester = new Semester("5. Semester", LocalDate.now(), LocalDate.now());
        Lecture lecture = new Lecture("ASE", semester, 0, 0);
        Entry entry = new Entry(LocalDateTime.of(2022, 10, 9, 0, 0), EntryType.LECTURE, lecture);

        AdditionalEntry additionalEntry = new AdditionalEntry(new EntryRepositoryMock());
        String details = "test";
        LocalDateTime end = LocalDateTime.of(2022, 10, 10, 0, 0);
        entry.finishEntry(end, details);
        additionalEntry.addEntry(entry, lecture);
        assertEquals(end, entry.getEnd());
        assertEquals(details, entry.getDetails());
        assertEquals(lecture, entry.getLecture());

    }

}
