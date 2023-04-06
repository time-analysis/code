package Repositories;

import FilterCriteria.EntryFilterCriteria;
import de.models.Entry;
import repositories.EntryRepositoryInterface;

import java.util.List;

public abstract class EntryRepositryDecorator implements EntryRepositoryInterface {
    protected EntryRepositoryInterface entryRepository;

    public EntryRepositryDecorator(EntryRepositoryInterface entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public void createEntry(Entry entry) {
        this.entryRepository.createEntry(entry);
    }

    @Override
    public List<Entry> getEntrys() {
        return this.entryRepository.getEntrys();
    }

    @Override
    public List<Entry> getEntrys(EntryFilterCriteria filterCriteria) {
        return this.entryRepository.getEntrys(filterCriteria);
    }

    @Override
    public void udpateEntry(Entry entry) {
        this.entryRepository.udpateEntry(entry);
    }
}
