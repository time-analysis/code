package usecases;

import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import filterCriteria.EntryFilterCriteria;
import filterCriteria.EntryFilterCriteriaBuilder;
import repositories.EntryRepositoryInterface;
import repositories.LectureRepositoryInterface;
import transferModels.AnalysisResultForLecture;
import transferModels.SelfStudyTimeAndLectureTime;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Analysis {
    private EntryRepositoryInterface entryRepository;
    private LectureRepositoryInterface lectureRepository;

    public Analysis(EntryRepositoryInterface entryRepository, LectureRepositoryInterface lectureRepository) {
        this.entryRepository = entryRepository;
        this.lectureRepository = lectureRepository;
    }

    public SelfStudyTimeAndLectureTime getTimeSpentForLecture(String lectureName) {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteriaForSelfStudyTime = filterCriteriaBuilder.withLectureName(lectureName).withType(EntryType.SELFSTUDY).build();
        EntryFilterCriteria criteriaForLectureTime = filterCriteriaBuilder.withLectureName(lectureName).withType(EntryType.LECTURE).build();

        List<Entry> entryRessourcesWithSelfStudyType = entryRepository.getEntrys(criteriaForSelfStudyTime);
        List<Entry> entryRessourcesWithLectureType = entryRepository.getEntrys(criteriaForLectureTime);

        Duration selfStudyTime, lectureTime;
        lectureTime = entryRessourcesWithLectureType.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        selfStudyTime = entryRessourcesWithSelfStudyType.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        return new SelfStudyTimeAndLectureTime(selfStudyTime, lectureTime);
    }

    public Duration getPresenceTime() {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = filterCriteriaBuilder.withType(EntryType.LECTURE).build();

        List<Entry> entryList = entryRepository.getEntrys(criteria);

        return entryList.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
    }

    public Duration getStudyTime() {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = filterCriteriaBuilder.withType(EntryType.SELFSTUDY).build();

        List<Entry> entryList = entryRepository.getEntrys(criteria);

        return entryList.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
    }

    public Duration getTimePerSemester(Semester semester) {
        List<Entry> entryList = entryRepository.getEntrys();
        Duration timePerSemester = entryList.stream().filter(entry -> entry.getLecture().getSemester().getName().equals(semester.getName())).map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        return timePerSemester;
    }

    public List<AnalysisResultForLecture> compareTimeTargetToActual() {
        List<Lecture> lectures = lectureRepository.getLectures();
        List<AnalysisResultForLecture> resultList = new ArrayList<>();
        for (Lecture lecture : lectures) {
            SelfStudyTimeAndLectureTime time = getTimeSpentForLecture(lecture.getName());
            resultList.add(new AnalysisResultForLecture(lecture, time));
        }
        return resultList;
    }
}
