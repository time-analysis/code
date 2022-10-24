package useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;

class AdditionalEntryTest {

	@Test
	void EntryIsAddedToLecture() {
		Entry e = new Entry(LocalDateTime.now(), LocalDateTime.now(), EntryType.PRESENCE, "Test");
		Lecture lecture = new Lecture("ASE", new Semester(), 0, 0);
		
		AdditionalEntry ae = new AdditionalEntry(null); //TODO was muss hier übergeben werden ?? mock?
		ae.addEntry(e,lecture);
		assertEquals(lecture.getEntries().get(0), e);
	}

}
