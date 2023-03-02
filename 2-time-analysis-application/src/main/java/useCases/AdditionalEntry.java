package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.Lecture;

public class AdditionalEntry {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public AdditionalEntry(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public void addEntry(Entry entry, Lecture lecture) {
        Entry entryWithLecture = new Entry(entry.getStart(), entry.getType(), lecture);
        entryWithLecture.finishEntry(entry.getEnd(), entry.getDetails());
        dataPlugin.persistEntry(dataAdapter.mapEntryToEntryRessource(entryWithLecture));
    }

}
