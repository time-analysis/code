package useCases;

import de.models.Entry;
import de.models.Lecture;

public class AdditionalEntry {
    DataAdapterInterface mapData;

    public AdditionalEntry(DataAdapterInterface mapData) {
        this.mapData = mapData;
    }

    public void addEntry(Entry entry, Lecture lecture) {
        Entry entryWithLection = new Entry(entry.getStart(), entry.getType(), entry.getDetails(), lecture);
        //TODO set end only for fixing the changed constructor
        entryWithLection.setEnd(entry.getEnd());
        mapData.mapEntryData(entryWithLection);
    }

//    public void addEntryByTimer(Lecture lecture) {
//        Timer.getEntry().setLecture(lecture);
//        mapData.mapEntryData(Timer.getEntry());
//    }
}
