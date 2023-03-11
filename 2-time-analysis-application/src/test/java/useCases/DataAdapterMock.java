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
    List<Entry> entryList;

    public void setEntryListToReturnWhenMapEntryRessourceListToEntryListIsCalled(List<Entry> entryList) {
        this.entryList = entryList;
    }

    @Override
    public EntryRessource mapEntryToEntryRessource(Entry entry) {
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

    @Override
    public List<Entry> mapEntryRessourceListToEntryList(List<EntryRessource> entryRessourceList) {
        return this.entryList;
    }

    @Override
    public List<Lecture> mapLectureRessourceListToLectureList(List<LectureResource> lectureResourceList) {
        return null;
    }

    @Override
    public List<Semester> mapSemesterRessourceListToSemesterList(List<SemesterRessource> semesterRessourceList) {
        return null;
    }

    @Override
    public List<EntryRessource> mapEntryListToEntryRessourceList(List<Entry> entryList) {
        return null;
    }

    @Override
    public List<LectureResource> mapLectureListToLectureListRessource(List<Lecture> lectureList) {
        return null;
    }

    @Override
    public List<SemesterRessource> mapSemesterListToSemesterRessourceList(List<Semester> semesterList) {
        return null;
    }
}
