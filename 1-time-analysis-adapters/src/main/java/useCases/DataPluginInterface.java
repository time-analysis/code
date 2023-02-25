package useCases;

import models.EntryRessource;
import models.LectureResource;

import java.util.Map;

public interface DataPluginInterface {

    boolean persistEntry(EntryRessource entryRessource);

    void persistNewLecture(LectureResource lectureResource);
}
