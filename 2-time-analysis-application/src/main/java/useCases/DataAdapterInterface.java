package useCases;

import de.models.Entry;
import de.models.Lecture;

public interface DataAdapterInterface {

    void mapEntryDataToPersist(Entry entry);

    void mapLectureDataToPersist(Lecture lecture);
}
