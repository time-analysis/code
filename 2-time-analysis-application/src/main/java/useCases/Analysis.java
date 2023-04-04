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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

        List<Entry> entryRessourcesWithSelfStudyType = dataAdapter.mapEntryRessourceListToEntryList(dataPlugin.getEntrys(criteriaForSelfStudyTime));
        List<Entry> entryRessourcesWithLectureType = dataAdapter.mapEntryRessourceListToEntryList(dataPlugin.getEntrys(criteriaForLectureTime));

        Duration selfStudyTime, lectureTime;
        lectureTime = entryRessourcesWithLectureType.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        selfStudyTime = entryRessourcesWithSelfStudyType.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        return new SelfStudyTimeAndLectureTime(selfStudyTime, lectureTime);
    }

    public Duration getPresenceTime() {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = filterCriteriaBuilder.withType(EntryType.LECTURE).build();

        List<Entry> entryList = dataAdapter.mapEntryRessourceListToEntryList(dataPlugin.getEntrys(criteria));

        return entryList.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
    }

    public Duration getStudyTime() {
        EntryFilterCriteriaBuilder filterCriteriaBuilder = new EntryFilterCriteriaBuilder();
        EntryFilterCriteria criteria = filterCriteriaBuilder.withType(EntryType.SELFSTUDY).build();

        List<Entry> entryList = dataAdapter.mapEntryRessourceListToEntryList(dataPlugin.getEntrys(criteria));

        return entryList.stream().map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
    }

    public Duration getTimePerSemester(Semester semester) {
        List<Entry> entryList = dataAdapter.mapEntryRessourceListToEntryList(dataPlugin.getEntrys());
        Duration timePerSemester = entryList.stream().filter(entry -> entry.getLecture().getSemester().getName().equals(semester.getName())).map(Entry::calculateDuration).reduce(Duration::plus).orElse(Duration.ZERO);
        return timePerSemester;
    }

    public List<AnalysisResultForLecture> compareTimeTargetToActual() {
        List<Lecture> lectures = dataAdapter.mapLectureRessourceListToLectureList(dataPlugin.getLectures());
        List<AnalysisResultForLecture> resultList = new ArrayList<>();
        for (Lecture lecture : lectures) {
            SelfStudyTimeAndLectureTime time = getTimeSpentForLecture(lecture.getName());
            resultList.add(new AnalysisResultForLecture(dataAdapter.mapLectureToLectureRessource(lecture), time));
        }
        return resultList;
    }
}
