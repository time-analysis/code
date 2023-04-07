package repositories;

import filterCriteria.EntryFilterCriteria;
import de.models.Entry;

import java.util.List;

public interface EntryRepositoryInterface {
    public void createEntry(Entry entry);

    public List<Entry> getEntrys();

    public List<Entry> getEntrys(EntryFilterCriteria filterCriteria);

    public void udpateEntry(Entry entry);
}
