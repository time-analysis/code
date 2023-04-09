package adapters;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import repositories.LectureRepositoryInterface;
import repositories.SemesterRepositoryInterface;
import ressourceModels.*;

import java.lang.module.FindException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class BaseAdapter {

    private String localDateTimeFormatString = "yyyy-MM-dd HH:mm";
    private String localDateFormatString = "yyyy-MM-dd";
    private SemesterRepositoryInterface semesterRepository;
    private LectureRepositoryInterface lectureRepository;

    public BaseAdapter(SemesterRepositoryInterface semesterRepository, LectureRepositoryInterface lectureRepository) {
        this.semesterRepository = semesterRepository;
        this.lectureRepository = lectureRepository;
    }

    public EntryRessource mapEntryToEntryRessource(Entry entry) {
        String start = LocalDateTimeToString(entry.getStart());
        String end = LocalDateTimeToString(entry.getEnd());
        String type = entry.getType().name();
        String lecture = entry.getLecture().getName();
        String details = entry.getDetails();
        String status = entry.getStatus().name();
        String id = entry.getId().toString();
        return new EntryRessource(start, end, EntryRessourceType.valueOf(type), details, lecture, EntryRessourceStatus.valueOf(status), id);
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
        //Optional<SemesterRessource> s = this.dataPlugin.getSemesterByName(lectureResource.getSemester()); //hier repository nutzen -> semester
        Optional<Semester> s = this.semesterRepository.getSemesterByName(lectureResource.getSemester());
        if (!s.isPresent()) {
            throw new FindException("Semester with name " + lectureResource.getSemester() + " was not found");
        }
        int lectureTime = lectureResource.getLectureTime();
        int selfStudyTime = lectureResource.getSelfStudyTime();
        return new Lecture(name, s.get(), lectureTime, selfStudyTime);
    }


    public Entry mapEntryRessourceToEntry(EntryRessource entryRessource) {
        LocalDateTime start = stringToLocalDateTime(entryRessource.getStart());
        EntryType type = EntryType.valueOf(entryRessource.getType().name());
        String uuid = entryRessource.getId();
        Optional<Lecture> lectureOptional = this.lectureRepository.getLectureByName(entryRessource.getLecture());
        if (!lectureOptional.isPresent()) {
            throw new FindException("Lecture with name " + entryRessource.getLecture() + " was not found");
        }
        if (Objects.isNull(uuid)) {
            if (entryRessource.getStatus().equals(EntryRessourceStatus.RUNNING)) {
                return new Entry(start, type, lectureOptional.get());
            } else {
                String details = entryRessource.getDetails();
                LocalDateTime end = stringToLocalDateTime(entryRessource.getEnd());
                return new Entry(start, end, type, lectureOptional.get(), details);
            }
        } else {
            if (entryRessource.getStatus().equals(EntryRessourceStatus.RUNNING)) {
                return new Entry(start, type, lectureOptional.get(), UUID.fromString(uuid));
            } else {
                String details = entryRessource.getDetails();
                LocalDateTime end = stringToLocalDateTime(entryRessource.getEnd());
                return new Entry(start, end, type, lectureOptional.get(), details, UUID.fromString(uuid));
            }
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
        if (Objects.isNull(time)) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormatString);
        return time.format(formatter);
    }

    public LocalDateTime stringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localDateTimeFormatString);
        return LocalDateTime.parse(time, formatter);
    }

    public String getLocalDateTimeFormatString() {
        return localDateTimeFormatString;
    }

    public String getLocalDateFormatString() {
        return localDateFormatString;
    }
}
