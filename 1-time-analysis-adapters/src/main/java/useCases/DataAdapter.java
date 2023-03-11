package useCases;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.util.List;

public class DataAdapter implements DataAdapterInterface {
    private BaseAdapter baseAdapter;

    public DataAdapter(DataPluginInterface dataPlugin) { //todo darf man einen datenplugin im datenadapter nutzen?
        this.baseAdapter = new BaseAdapter(dataPlugin);
    }

    @Override
    public EntryRessource mapEntryToEntryRessource(Entry entry) {
        return baseAdapter.mapEntryToEntryRessource(entry);
    }

    @Override
    public LectureResource mapLectureToLectureRessource(Lecture lecture) {
        return baseAdapter.mapLectureToLectureRessource(lecture);
    }

    @Override
    public SemesterRessource mapSemesterToSemesterRessource(Semester semester) {
        return baseAdapter.mapSemesterToSemesterRessource(semester);
    }

    @Override
    public Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource) {
        return baseAdapter.mapSemesterRessourceToSemester(semesterRessource);
    }

    @Override
    public Lecture mapLectureRessourceToLecture(LectureResource lectureResource) {
        return baseAdapter.mapLectureRessourceToLecture(lectureResource);
    }

    @Override
    public Entry mapEntryRessourceToEntry(EntryRessource entryRessource) {
        return baseAdapter.mapEntryRessourceToEntry(entryRessource);
    }

    public List<Entry> mapEntryRessourceListToEntryList(List<EntryRessource> entryRessourceList) {
        return baseAdapter.mapEntryRessourceListToEntryList(entryRessourceList);
    }

    public List<Lecture> mapLectureRessourceListToLectureList(List<LectureResource> lectureResourceList) {
        return baseAdapter.mapLectureRessourceListToLectureList(lectureResourceList);
    }

    public List<Semester> mapSemesterRessourceListToSemesterList(List<SemesterRessource> semesterRessourceList) {
        return baseAdapter.mapSemesterRessourceListToSemesterList(semesterRessourceList);
    }

    public List<EntryRessource> mapEntryListToEntryRessourceList(List<Entry> entryList) {
        return baseAdapter.mapEntryListToEntryRessourceList(entryList);
    }

    public List<LectureResource> mapLectureListToLectureListRessource(List<Lecture> lectureList) {
        return baseAdapter.mapLectureListToLectureListRessource(lectureList);
    }

    public List<SemesterRessource> mapSemesterListToSemesterRessourceList(List<Semester> semesterList) {
        return baseAdapter.mapSemesterListToSemesterRessourceList(semesterList);
    }

}
