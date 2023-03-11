package useCases;

import Interfaces.DataAdapterInterface;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DataAdapterMock implements DataAdapterInterface {
    @Override
    public EntryRessource mapEntryToEntryRessource(Entry entry) {
        //do nothing for now, pretend to map data...
        return null;
    }

    @Override
    public LectureResource mapLectureToLectureRessource(Lecture lecture) {
        return null;
    }

    @Override
    public SemesterRessource mapSemesterToSemesterRessource(Semester semester) {
        return null;
    }

    @Override
    public Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource) {
        return null;
    }

    @Override
    public Lecture mapLectureRessourceToLecture(LectureResource lectureResource) {
        return null;
    }

    @Override
    public Entry mapEntryRessourceToEntry(EntryRessource entryRessource) {
        Semester semester = new Semester("meinSemester", LocalDate.now(), LocalDate.now());
        Lecture lecture = new Lecture("MeineVL", semester, 10, 10);
        Entry entry = new Entry(LocalDateTime.of(2023, 1, 1, 10, 0), EntryType.SELFSTUDY, lecture);
        entry.finishEntry(LocalDateTime.of(2023, 1, 1, 12, 0), "details");
        return entry;
    }
}
