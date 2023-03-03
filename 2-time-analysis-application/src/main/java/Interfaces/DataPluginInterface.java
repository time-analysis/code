package Interfaces;

import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.util.List;
import java.util.Optional;

public interface DataPluginInterface {

    boolean persistEntry(EntryRessource entryRessource);

    void persistLecture(LectureResource lectureResource);

    void persistSemester(SemesterRessource semester);

    Optional<SemesterRessource> getSemesterByName(String semesterName);

    Optional<LectureResource> getLectureByName(String lectureName);

    List<LectureResource> getLectures();

    List<SemesterRessource> getSemesters();

    List<EntryRessource> getEntrysByLecture(LectureResource mapLectureToLectureRessource);

    List<EntryRessource> getEntrys();
    //Todo make use of optional
}
