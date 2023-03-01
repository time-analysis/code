package useCases;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

public class DataAdapter implements DataAdapterInterface {

    private DataPluginInterface dataPlugin;

    public DataAdapter(DataPluginInterface dataPlugin) { //todo darf man einen datenplugin im datenadapter nutzen?
        this.dataPlugin = dataPlugin;
    }

    public EntryRessource mapEntryToEntryRessource(Entry entry) {
        String start = entry.getStart().toString();
        String end = entry.getEnd().toString();
        String type = entry.getType().name();
        String lecture = entry.getLecture().getName();
        String details = entry.getDetails();
        return new EntryRessource(start, end, type, details, lecture);
    }

    @Override
    public LectureResource mapLectureDataToPersist(Lecture lecture) {
        String name = lecture.getName();
        String semester = lecture.getSemester().getName();
        int lectureTime = lecture.getLectureTime();
        int selfStudyTime = lecture.getSelfStudyTime();
        return new LectureResource(name, semester, lectureTime, selfStudyTime);
    }

    @Override
    public SemesterRessource mapSemesterToSemesterRessource(Semester semester) {
        String name = semester.getName();
        String start = semester.getStart().toString();
        String end = semester.getEnd().toString();
        return new SemesterRessource(name, start, end);
    }

    @Override
    public Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource) {
        String name = semesterRessource.getName();
        LocalDate start = stringToDatetime(semesterRessource.getStart());
        LocalDate end = stringToDatetime(semesterRessource.getEnd());
        return new Semester(name, start, end);
    }

    @Override
    public Lecture mapLectureRessourceToLecture(LectureResource lectureResource) {
        String name = lectureResource.getName();
        Optional<SemesterRessource> s = this.dataPlugin.getSemesterByName(lectureResource.getSemester());
        Semester semester = null; //todo obacht
        if (s.isPresent()) {
            semester = mapSemesterRessourceToSemester(s.get());
        } else {
            //todo handle error
        }
        int lectureTime = lectureResource.getLectureTime();
        int selfStudyTime = lectureResource.getSelfStudyTime();
        return new Lecture(name, semester, lectureTime, selfStudyTime);
    }

    private LocalDate stringToDatetime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(time, formatter);
    }

}
