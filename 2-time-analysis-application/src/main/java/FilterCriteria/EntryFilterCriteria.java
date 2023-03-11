package FilterCriteria;

import de.models.EntryType;

public class EntryFilterCriteria {
    private String lectureName;
    private EntryType entryType;

    public EntryFilterCriteria(String lectureName, EntryType entryType) {
        this.lectureName = lectureName;
        this.entryType = entryType;
    }

    public String getLectureName() {
        return lectureName;
    }

    public EntryType getEntryType() {
        return entryType;
    }
}
