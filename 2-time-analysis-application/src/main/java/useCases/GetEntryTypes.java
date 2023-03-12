package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import ressourceModels.EntryRessourceType;

public class GetEntryTypes {

    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public GetEntryTypes(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public EntryRessourceType[] getEntryType() {
        return EntryRessourceType.values();
    }
}
