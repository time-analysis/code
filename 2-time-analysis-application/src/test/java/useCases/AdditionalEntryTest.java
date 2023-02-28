package useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;

class AdditionalEntryTest {
    DataAdapterInterface mock = new DataAdapterMock();

    @Test
    void EntryIsAddedToLecture() {
        Semester semester = new Semester("5. Semester", LocalDate.now(), LocalDate.now());
        Lecture lecture = new Lecture("ASE", semester, 0, 0);
        Entry e = new Entry(LocalDateTime.of(2022, 10, 9, 0, 0), EntryType.LECTURE, lecture);

        AdditionalEntry ae = new AdditionalEntry(mock);
        ae.addEntry(e, lecture);
        String details = "test";
        LocalDateTime end = LocalDateTime.of(2022, 10, 10, 0, 0);
        e.finishEntry(end, details);
        assertEquals(end, e.getEnd());
        assertEquals(details, e.getDetails());
        assertEquals(e.getLecture(), lecture);

    }

}
