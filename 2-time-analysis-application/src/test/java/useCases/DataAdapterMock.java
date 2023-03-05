package useCases;

import Interfaces.DataAdapterInterface;
import de.models.Entry;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

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
        return null;
    }
}
