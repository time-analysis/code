package useCases;

import ressourceModels.EntryRessourceType;

public class GetEntryTypes {
    public EntryRessourceType[] getEntryType() {
        return EntryRessourceType.values();
    }
}
