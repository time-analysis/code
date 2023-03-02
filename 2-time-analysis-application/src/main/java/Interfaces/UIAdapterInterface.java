package Interfaces;

import de.models.Entry;
import de.models.Lecture;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.util.List;

public interface UIAdapterInterface {

    Lecture mapLectureRessourceToLecture(LectureResource lectureResource);
    Entry mapEntryRessourceToEntry(EntryRessource entryRessource);
    List<LectureResource> getAllLecturesOfCurrentSemester();
}
