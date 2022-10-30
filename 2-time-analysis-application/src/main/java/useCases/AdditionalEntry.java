package useCases;

import de.models.Entry;
import de.models.Lecture;

public class AdditionalEntry {
    DataAdapterInterface mapData;


    public AdditionalEntry(DataAdapterInterface mapData) {
        this.mapData = mapData;
    }

    public void addEntry(Entry entry, Lecture lecture) {
        lecture.addEntry(entry);
        mapData.mapData(entry, lecture);
    }

    public void addEntryByTimer(Lecture lecture) {
        lecture.addEntry(Timer.getEntry());
        mapData.mapData(Timer.getEntry(), lecture);
    }

}
