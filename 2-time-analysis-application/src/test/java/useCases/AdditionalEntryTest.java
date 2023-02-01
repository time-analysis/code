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
        Entry e = new Entry(LocalDateTime.now(), EntryType.PRESENCE, lecture);

        AdditionalEntry ae = new AdditionalEntry(mock);
        ae.addEntry(e, lecture);
        assertEquals(e.getLecture(), lecture);
    }

//    @Test
//    void EntryIsAddedToLectureFromTimer() {
//        Entry e = new Entry(EntryType.PRESENCE, "Test");
//        Lecture lecture = new Lecture("ASE", new Semester(), 0, 0);
//        Timer.setEntry(e);
//        Timer.toggleTimer();
//        Timer.toggleTimer();
//        AdditionalEntry ae = new AdditionalEntry(mock);
//        ae.addEntryByTimer(lecture);
//        assertEquals(lecture.getEntries().get(0), e);
//    }

}
