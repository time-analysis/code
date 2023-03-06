package useCases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

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

}
