package useCases;

import FilterCriteria.EntryFilterCriteria;
import FilterCriteria.EntryFilterCriteriaBuilder;
import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.EntryStatus;

import java.util.List;

public class GetEntries {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public GetEntries(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public List<Entry> getUnfinishedEntries() {
        EntryFilterCriteriaBuilder entryFilterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria entryFilterCriteria = entryFilterCriteriaBuilder.withStatus(EntryStatus.RUNNING).build();
        return dataAdapter.mapEntryRessourceListToEntryList(dataPlugin.getEntrys(entryFilterCriteria));
    }
}
