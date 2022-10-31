package useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		Entry e = new Entry(LocalDateTime.now(), LocalDateTime.now(), EntryType.PRESENCE, "Test");
		Lecture lecture = new Lecture("ASE", new Semester(), 0, 0);
		
		AdditionalEntry ae = new AdditionalEntry(mock);
		ae.addEntry(e,lecture);
		assertEquals(lecture.getEntries().get(0), e);
	}

	@Test
	void EntryIsAddedToLectureFromTimer() {
		Entry e = new Entry(EntryType.PRESENCE, "Test");
		Lecture lecture = new Lecture("ASE", new Semester(), 0, 0);
		Timer.setEntry(e);
		Timer.toggleTimer();
		Timer.toggleTimer();
		AdditionalEntry ae = new AdditionalEntry(mock);
		ae.addEntryByTimer(lecture);
		assertEquals(lecture.getEntries().get(0), e);
	}

}
