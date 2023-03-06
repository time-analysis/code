package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.EntryType;

public class GetEntryTypes {

    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public GetEntryTypes(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public EntryType[] getEntryType() {
        return EntryType.values();
    }
}
