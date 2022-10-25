package useCases;

import de.models.Entry;
import de.models.Lecture;

public class DataAdapterMock implements DataAdapterInterface{
    @Override
    public void mapData(Entry entry, Lecture lecture) {
        //do nothing for now, pretend to map data...
    }
}
