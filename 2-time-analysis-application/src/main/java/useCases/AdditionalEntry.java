package useCases;

import de.models.Entry;
import de.models.Lecture;

public class AdditionalEntry {
    DataAdapterInterface mapData;

    public AdditionalEntry(DataAdapterInterface mapData) {
        this.mapData = mapData;
    }

    public void addEntry(Entry entry, Lecture lecture) {
        entry.setLecture(lecture);
        mapData.mapData(entry, lecture);
    }

    public void addEntryByTimer(Lecture lecture) {
        Timer.getEntry().setLecture(lecture);
        mapData.mapData(Timer.getEntry(), lecture);
    }
}
