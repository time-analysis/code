package Interfaces;

import de.models.Entry;
import de.models.Lecture;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface UIAdapterInterface {

    Lecture mapLectureRessourceToLecture(LectureResource lectureResource);

    Entry mapEntryRessourceToEntry(EntryRessource entryRessource);

    List<LectureResource> getAllLecturesOfCurrentSemester();

    List<SemesterRessource> getAllSemesters();

    String formatLocalDateTime(LocalDateTime time);

    String formatDuration(Duration duration);
}
