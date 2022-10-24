import java.time.LocalDateTime;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import useCases.AdditionalEntry;
import useCases.DataAdapter;
import useCases.DataPlugin;

public class Main {

	public static void main(String[] args) {
		Entry e = new Entry(LocalDateTime.now(), LocalDateTime.now(), EntryType.PRESENCE, "Test");
		Lecture lecture = new Lecture("ASE", new Semester(), 0, 0);
		
		DataPlugin wd = new DataPlugin();
		DataAdapter md = new DataAdapter(wd);
		AdditionalEntry ae = new AdditionalEntry(md);
		ae.addEntry(e,lecture);
	}
}
