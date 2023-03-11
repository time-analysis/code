package FilterCriteria;

import de.models.EntryType;

public class EntryFilterCriteriaBuilder {
    private String lectureName;
    private EntryType entryType;
    public static EntryFilterCriteria NO_FILTER_CRITERIA = new EntryFilterCriteria(null, null);

    public EntryFilterCriteriaBuilder withLectureName(String lectureName) {
        this.lectureName = lectureName;
        return this;
    }

    public EntryFilterCriteriaBuilder withType(EntryType type) {
        this.entryType = type;
        return this;
    }

    public EntryFilterCriteria build() {
        return new EntryFilterCriteria(this.lectureName, this.entryType);
    }
}
