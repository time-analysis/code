package useCases;

import de.models.Entry;
import de.models.Lecture;

public class AdditionalEntry {
    DataAdapterInterface mapData;

    public AdditionalEntry(DataAdapterInterface mapData,DataP) {
        this.mapData = mapData;
    }

    public void addEntry(Entry entry, Lecture lecture) {
        Entry entryWithLection = new Entry(entry.getStart(), entry.getType(), lecture);
        entryWithLection.finishEntry(entry.getEnd(), entry.getDetails());
        mapData.mapEntryDataToPersist(entryWithLection);
    }

}
