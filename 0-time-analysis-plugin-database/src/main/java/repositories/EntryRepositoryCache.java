package repositories;

import de.models.Entry;

import java.util.LinkedList;
import java.util.List;

public class EntryRepositoryCache extends EntryRepositryDecorator {
    List<Entry> pseudoCache = new LinkedList<>();

    public EntryRepositoryCache(EntryRepositoryInterface entryRepository) {
        super(entryRepository);
    }

    //getEntry by filtercriteria does not have to be overwritten, since it uses getEntrys() to fetch all Entries intern
    public void createEntry(Entry entry) {
        pseudoCache.add(entry);
        this.entryRepository.createEntry(entry);
    }


    public List<Entry> getEntrys() {
        return pseudoCache;
    }

    public void udpateEntry(Entry entry) {
        this.entryRepository.udpateEntry(entry);
        Entry toReplace = pseudoCache.stream().filter(e -> e.getStart().equals(entry.getStart())).findFirst().get();//todo better filter
        pseudoCache.remove(toReplace);
        pseudoCache.add(entry);
    }
}
