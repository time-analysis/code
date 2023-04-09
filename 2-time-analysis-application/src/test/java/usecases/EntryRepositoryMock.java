package usecases;

import de.models.Entry;
import filterCriteria.EntryFilterCriteria;
import repositories.EntryRepositoryInterface;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class EntryRepositoryMock implements EntryRepositoryInterface {
    private List<List<Entry>> inMemoryPersistence = new LinkedList<>();
    private Iterator<List<Entry>> iterator;

    public void addListOfEntryToReturnList(List<Entry> entries) {
        inMemoryPersistence.add(entries);
        this.iterator = inMemoryPersistence.iterator();
    }

    @Override
    public void createEntry(Entry entry) {

    }

    @Override
    public List<Entry> getEntrys() {
        return iterator.next();
    }

    @Override
    public List<Entry> getEntrys(EntryFilterCriteria filterCriteria) {
        return iterator.next();
    }

    @Override
    public void udpateEntry(Entry entry) {

    }
}
