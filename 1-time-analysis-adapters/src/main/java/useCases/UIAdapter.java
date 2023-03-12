package useCases;

import Interfaces.DataPluginInterface;
import Interfaces.UIAdapterInterface;
import de.models.Entry;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UIAdapter implements UIAdapterInterface {


    private BaseAdapter baseAdapter;

    public UIAdapter(DataPluginInterface dataPlugin) {
        this.baseAdapter = new BaseAdapter(dataPlugin);
    }


    public Lecture mapLectureRessourceToLecture(LectureResource lectureResource) {
        return baseAdapter.mapLectureRessourceToLecture(lectureResource);
    }

    public Entry mapEntryRessourceToEntry(EntryRessource entryRessource) {
        return baseAdapter.mapEntryRessourceToEntry(entryRessource);
    }

    public String formatLocalDateTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }

    @Override
    public String formatDuration(Duration duration) {
        return String.format("%s Days %s Hours %s Minutes %s Seconds", duration.toDaysPart(), duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }
    @Override
    public LocalDateTime stringToLocalDateTime(String time){
        return baseAdapter.stringToLocalDateTime(time);
    }

    @Override
    public Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource) {
        return baseAdapter.mapSemesterRessourceToSemester(semesterRessource);
    }

    @Override
    public LectureResource mapLectureToLectureRessource(Lecture lecture) {
        return baseAdapter.mapLectureToLectureRessource(lecture);
    }

    @Override
    public SemesterRessource mapSemesterToSemesterRessource(Semester semester) {
        return baseAdapter.mapSemesterToSemesterRessource(semester);
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
