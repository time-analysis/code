package filterCriteria;

import de.models.EntryStatus;
import de.models.EntryType;

public class EntryFilterCriteria {
    private String lectureName;
    private EntryType entryType;
    private EntryStatus entryStatus;

    public EntryFilterCriteria(String lectureName, EntryType entryType, EntryStatus entryStatus) {
        this.lectureName = lectureName;
        this.entryType = entryType;
        this.entryStatus = entryStatus;
    }

    public String getLectureName() {
        return lectureName;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public EntryStatus getEntryStatus() {
        return entryStatus;
    }
}
