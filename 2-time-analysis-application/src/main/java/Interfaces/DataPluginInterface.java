package useCases;

import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;

import java.util.List;

public interface DataPluginInterface {

    boolean persistEntry(EntryRessource entryRessource);

    void persistNewLecture(LectureResource lectureResource);

    List<LectureResource> getLectures();
}
