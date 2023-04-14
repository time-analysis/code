package repositories;

import de.models.Entry;
import filterCriteria.EntryFilterCriteria;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntryRepositoryCache extends EntryRepositryDecorator {
    List<Entry> pseudoCache = new LinkedList<>();

    public EntryRepositoryCache(EntryRepositoryInterface entryRepository) {
        super(entryRepository);
        pseudoCache = super.getEntrys();
    }

    @Override
    public List<Entry> getEntrys(EntryFilterCriteria criteria) {
        Stream<Entry> entryStream = pseudoCache.stream();
        if (!Objects.isNull(criteria.getEntryType())) {
            entryStream = entryStream.filter(entry -> entry.getType().name().equals(criteria.getEntryType().name()));
        }
        if (!Objects.isNull(criteria.getLectureName())) {
            entryStream = entryStream.filter(entry -> entry.getLecture().getName().equals(criteria.getLectureName()));
        }
        if (!Objects.isNull(criteria.getEntryStatus())) {
            entryStream = entryStream.filter(entry -> entry.getStatus().name().equals(criteria.getEntryStatus().name()));
        }
        return entryStream.collect(Collectors.toList());
    }

    @Override
    public void createEntry(Entry entry) {
        pseudoCache.add(entry);
        this.entryRepository.createEntry(entry);
    }

    @Override
    public List<Entry> getEntrys() {
        return pseudoCache;
    }

    @Override
    public void udpateEntry(Entry entry) {
        this.entryRepository.udpateEntry(entry);
        Entry toReplace = pseudoCache.stream().filter(e -> e.equals(entry)).findFirst().get();
        pseudoCache.remove(toReplace);
        pseudoCache.add(entry);
    }
}
