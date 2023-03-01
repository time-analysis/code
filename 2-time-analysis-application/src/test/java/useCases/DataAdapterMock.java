package useCases;

import Interfaces.DataAdapterInterface;
import de.models.Entry;

public class DataAdapterMock implements DataAdapterInterface {
    @Override
    public void mapEntryToEntryRessource(Entry entry) {
        //do nothing for now, pretend to map data...
    }
}
