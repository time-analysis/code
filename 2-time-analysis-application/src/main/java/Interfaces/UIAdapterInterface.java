package Interfaces;

import de.models.Entry;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.time.Duration;
import java.time.LocalDateTime;

public interface UIAdapterInterface {

    Lecture mapLectureRessourceToLecture(LectureResource lectureResource);

    Entry mapEntryRessourceToEntry(EntryRessource entryRessource);

    String formatLocalDateTime(LocalDateTime time);

    String formatDuration(Duration duration);

    Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource);

    LectureResource mapLectureToLectureRessource(Lecture lecture);

    SemesterRessource mapSemesterToSemesterRessource(Semester semester);
}
