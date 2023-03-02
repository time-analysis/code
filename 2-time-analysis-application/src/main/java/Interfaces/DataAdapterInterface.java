package Interfaces;

import de.models.Entry;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

public interface DataAdapterInterface {

    EntryRessource mapEntryToEntryRessource(Entry entry);

    LectureResource mapLectureToLectureRessource(Lecture lecture);

    SemesterRessource mapSemesterToSemesterRessource(Semester semester);

    Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource);

    Lecture mapLectureRessourceToLecture(LectureResource lectureResource);
}
