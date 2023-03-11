package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.Lecture;

import java.time.LocalDateTime;

public class AdditionalEntry {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public AdditionalEntry(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public void addEntry(Entry entry, Lecture lecture) {
        Entry entryWithLecture = new Entry(entry.getStart(), entry.getEnd(), entry.getType(), lecture, entry.getDetails());
        dataPlugin.persistEntry(dataAdapter.mapEntryToEntryRessource(entryWithLecture));
    }

    public void startEntry(Entry entry) {

    }

    public void finishEntry(Entry entry, LocalDateTime end, String details) {

    }

}
