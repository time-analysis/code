package useCases;

import de.models.Entry;
import de.models.Lecture;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;

public interface DataAdapterInterface {

    EntryRessource mapEntryDataToPersist(Entry entry);

    LectureResource mapLectureDataToPersist(Lecture lecture);
}
