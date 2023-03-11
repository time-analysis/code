package useCases;

import Interfaces.DataPluginInterface;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class BaseAdapter {

    private DataPluginInterface dataPlugin;
    private String localDateTimeFormatString = "yyyy-MM-dd HH:mm";
    private String localDateFormatString = "yyyy-MM-dd";

    public BaseAdapter(DataPluginInterface dataPlugin) {
        this.dataPlugin = dataPlugin;
    }

    public EntryRessource mapEntryToEntryRessource(Entry entry) {
        String start = LocalDateTimeToString(entry.getStart());
        String end = LocalDateTimeToString(entry.getEnd());
        String type = entry.getType().name();
        String lecture = entry.getLecture().getName();
        String details = entry.getDetails();
        return new EntryRessource(start, end, type, details, lecture);
    }


    public LectureResource mapLectureToLectureRessource(Lecture lecture) {
        String name = lecture.getName();
        String semester = lecture.getSemester().getName();
        int lectureTime = lecture.getLectureTime();
        int selfStudyTime = lecture.getSelfStudyTime();
        return new LectureResource(name, semester, lectureTime, selfStudyTime);
    }


    public SemesterRessource mapSemesterToSemesterRessource(Semester semester) {
        String name = semester.getName();
        String start = LocalDateToString(semester.getStart());
        String end = LocalDateToString(semester.getEnd());
        return new SemesterRessource(name, start, end);
    }


    public Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource) {
        String name = semesterRessource.getName();
        LocalDate start = stringToDatetime(semesterRessource.getStart());
        LocalDate end = stringToDatetime(semesterRessource.getEnd());
        return new Semester(name, start, end);
    }


    public Lecture mapLectureRessourceToLecture(LectureResource lectureResource) {
        String name = lectureResource.getName();
        Optional<SemesterRessource> s = this.dataPlugin.getSemesterByName(lectureResource.getSemester());
        Semester semester = null;
        if (s.isPresent()) {
            semester = mapSemesterRessourceToSemester(s.get());
        } else {
            //todo handle error
        }
        int lectureTime = lectureResource.getLectureTime();
        int selfStudyTime = lectureResource.getSelfStudyTime();
        return new Lecture(name, semester, lectureTime, selfStudyTime);
    }


    public Entry mapEntryRessourceToEntry(EntryRessource entryRessource) {
        String details = entryRessource.getDetails();
        LocalDateTime start = stringToLocalDateTime(entryRessource.getStart());
        LocalDateTime end = stringToLocalDateTime(entryRessource.getEnd());
        EntryType type = EntryType.valueOf(entryRessource.getType());
        Lecture lecture = null;
        Optional<LectureResource> lectureResourceOptional = this.dataPlugin.getLectureByName(entryRessource.getLecture());
        if (lectureResourceOptional.isPresent()) {
            lecture = mapLectureRessourceToLecture(lectureResourceOptional.get());
        } else {
            //todo handle error
        }
        Entry entry = new Entry(start, type, lecture);
        entry.finishEntry(end, details);
        return entry;
    }

    private LocalDate stringToDatetime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateFormatString);
        return LocalDate.parse(time, formatter);
    }

    private String LocalDateToString(LocalDate time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateFormatString);
        return time.format(formatter);
    }

    private String LocalDateTimeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormatString);
        return time.format(formatter);
    }

    private LocalDateTime stringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormatString);
        return LocalDateTime.parse(time, formatter);
    }
}
