package useCases;

import de.models.Entry;
import de.models.Lecture;
import repositories.EntryRepositoryInterface;

import java.time.LocalDateTime;

public class AdditionalEntry {
    private EntryRepositoryInterface entryRepository;

    public AdditionalEntry(EntryRepositoryInterface entryRepository) {
       this.entryRepository = entryRepository;
    }

    public void addEntry(Entry entry, Lecture lecture) {
        Entry entryWithLecture = new Entry(entry.getStart(), entry.getEnd(), entry.getType(), lecture, entry.getDetails());
        entryRepository.createEntry(entryWithLecture);
    }

    public void startEntry(Entry entry) {
        entryRepository.createEntry(entry);
    }

    public void finishEntry(Entry entry, LocalDateTime end, String details) {
        entry.finishEntry(end,details);
        entryRepository.udpateEntry(entry);
    }

}
