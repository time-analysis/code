package useCases;

import FilterCriteria.EntryFilterCriteria;
import FilterCriteria.EntryFilterCriteriaBuilder;
import Interfaces.DataAdapterInterface;
import Interfaces.DataPluginInterface;
import TransferModels.AnalysisResultForLecture;
import TransferModels.SelfStudyTimeAndLectureTime;
import de.models.Entry;
import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;
import ressourceModels.EntryRessource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Analysis {
    private DataAdapterInterface dataAdapter;
    private DataPluginInterface dataPlugin;

    public Analysis(DataAdapterInterface dataAdapter, DataPluginInterface dataPlugin) {
        this.dataAdapter = dataAdapter;
        this.dataPlugin = dataPlugin;
    }

    public SelfStudyTimeAndLectureTime getTimeSpentForLecture(String lectureName) {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteriaForSelfStudyTime = filterCriteriaBuilder.withLectureName(lectureName).withType(EntryType.SELFSTUDY).build();
        EntryFilterCriteria criteriaForLectureTime = filterCriteriaBuilder.withLectureName(lectureName).withType(EntryType.LECTURE).build();

        List<Entry> entryRessourcesWithSelfStudyType = dataPlugin.getEntrys(criteriaForSelfStudyTime).stream().map(dataAdapter::mapEntryRessourceToEntry).collect(Collectors.toList());
        List<Entry> entryRessourcesWithLectureType = dataPlugin.getEntrys(criteriaForLectureTime).stream().map(dataAdapter::mapEntryRessourceToEntry).collect(Collectors.toList());

        Duration selfStudyTime, lectureTime;
        lectureTime = entryRessourcesWithLectureType.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        selfStudyTime = entryRessourcesWithSelfStudyType.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        return new SelfStudyTimeAndLectureTime(selfStudyTime, lectureTime);
    }

    public Duration getPresenceTime() {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = filterCriteriaBuilder.withType(EntryType.LECTURE).build();

        List<Entry> entryList = dataPlugin.getEntrys(criteria).stream().map(dataAdapter::mapEntryRessourceToEntry).collect(Collectors.toList());

        return entryList.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
    }

    public Duration getStudyTime() {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = filterCriteriaBuilder.withType(EntryType.SELFSTUDY).build();

        List<Entry> entryList = dataPlugin.getEntrys(criteria).stream().map(dataAdapter::mapEntryRessourceToEntry).collect(Collectors.toList());

        return entryList.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
    }

    public Duration getTimePerSemester(Semester semester) {
        List<EntryRessource> entryRessourceList = dataPlugin.getEntrys();
        List<Entry> entryList = entryRessourceList.stream().map(dataAdapter::mapEntryRessourceToEntry).collect(Collectors.toList());
        Duration timePerSemester = entryList.stream().filter(entry -> entry.getLecture().getSemester().getName().equals(semester.getName())).map(Entry::calculateDuration).reduce(Duration::plus).get();
        return timePerSemester; //todo reicht java.duration oder braucht man ein eigenes objekt?
    }

    public List<AnalysisResultForLecture> compareTimeTargetToActual() {
        List<Lecture> lectures = dataPlugin.getLectures().stream().map(dataAdapter::mapLectureRessourceToLecture).collect(Collectors.toList());
        List<AnalysisResultForLecture> resultList = new ArrayList<>();
        for (Lecture lecture : lectures) {
            SelfStudyTimeAndLectureTime time = getTimeSpentForLecture(lecture.getName());
            resultList.add(new AnalysisResultForLecture(dataAdapter.mapLectureToLectureRessource(lecture), time));
        }
        return resultList;
    }
}
