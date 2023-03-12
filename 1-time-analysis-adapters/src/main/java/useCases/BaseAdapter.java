package useCases;

import Interfaces.DataPluginInterface;
import de.models.*;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String status = entry.getStatus().name();
        return new EntryRessource(start, end, type, details, lecture, status);
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
        //Optional<SemesterRessource> se = new GetLectures(dataAdapter,dataPlugin).getLectures().stream().map(this::mapSemesterToSemesterRessource).collect(Collectors.toList());
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
        LocalDateTime start = stringToLocalDateTime(entryRessource.getStart());
        EntryType type = EntryType.valueOf(entryRessource.getType());
        Lecture lecture = null;
        Optional<LectureResource> lectureResourceOptional = this.dataPlugin.getLectureByName(entryRessource.getLecture());
        if (lectureResourceOptional.isPresent()) {
            lecture = mapLectureRessourceToLecture(lectureResourceOptional.get());
        } else {
            //todo handle error
        }
        if(entryRessource.getStatus().equals(EntryStatus.RUNNING.name())){
            return new Entry(start, type, lecture);
        }else{
        String details = entryRessource.getDetails();
        LocalDateTime end = stringToLocalDateTime(entryRessource.getEnd());
        return new Entry(start,end,type,lecture,details);
        }
    }

    public List<Entry> mapEntryRessourceListToEntryList(List<EntryRessource> entryRessourceList) {
        return entryRessourceList.stream().map(this::mapEntryRessourceToEntry).collect(Collectors.toList());
    }

    public List<Lecture> mapLectureRessourceListToLectureList(List<LectureResource> lectureResourceList) {
        return lectureResourceList.stream().map(this::mapLectureRessourceToLecture).collect(Collectors.toList());
    }

    public List<Semester> mapSemesterRessourceListToSemesterList(List<SemesterRessource> semesterRessourceList) {
        return semesterRessourceList.stream().map(this::mapSemesterRessourceToSemester).collect(Collectors.toList());
    }

    public List<EntryRessource> mapEntryListToEntryRessourceList(List<Entry> entryList) {
        return entryList.stream().map(this::mapEntryToEntryRessource).collect(Collectors.toList());
    }

    public List<LectureResource> mapLectureListToLectureListRessource(List<Lecture> lectureList) {
        return lectureList.stream().map(this::mapLectureToLectureRessource).collect(Collectors.toList());
    }

    public List<SemesterRessource> mapSemesterListToSemesterRessourceList(List<Semester> semesterList) {
        return semesterList.stream().map(this::mapSemesterToSemesterRessource).collect(Collectors.toList());
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
        if(Objects.isNull(time)) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormatString);
        return time.format(formatter);
    }

    public LocalDateTime stringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormatString);
        return LocalDateTime.parse(time, formatter);
    }
}
