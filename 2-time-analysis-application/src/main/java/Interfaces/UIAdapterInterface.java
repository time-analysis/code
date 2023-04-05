package Interfaces;

import TransferModels.AnalysisResultForLecture;
import TransferModels.SelfStudyTimeAndLectureTime;
import de.models.Entry;
import de.models.Lecture;
import de.models.Semester;
import renderModels.AnalysisResultForLectureRenderModel;
import renderModels.SelfStudyTimeAndLectureTimeRenderModel;
import ressourceModels.EntryRessource;
import ressourceModels.LectureResource;
import ressourceModels.SemesterRessource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface UIAdapterInterface {

    Lecture mapLectureRessourceToLecture(LectureResource lectureResource);

    Entry mapEntryRessourceToEntry(EntryRessource entryRessource);

    String formatLocalDateTime(LocalDateTime time);

    String formatDuration(Duration duration);

    LocalDateTime stringToLocalDateTime(String time);

    Semester mapSemesterRessourceToSemester(SemesterRessource semesterRessource);

    LectureResource mapLectureToLectureRessource(Lecture lecture);

    SemesterRessource mapSemesterToSemesterRessource(Semester semester);

    List<Entry> mapEntryRessourceListToEntryList(List<EntryRessource> entryRessourceList);

    List<Lecture> mapLectureRessourceListToLectureList(List<LectureResource> lectureResourceList);

    List<Semester> mapSemesterRessourceListToSemesterList(List<SemesterRessource> semesterRessourceList);

    List<EntryRessource> mapEntryListToEntryRessourceList(List<Entry> entryList);

    List<LectureResource> mapLectureListToLectureListRessource(List<Lecture> lectureList);

    List<SemesterRessource> mapSemesterListToSemesterRessourceList(List<Semester> semesterList);

    SelfStudyTimeAndLectureTimeRenderModel selfStudyTimeAndLectureTimeToRenderModel(SelfStudyTimeAndLectureTime selfStudyTimeAndLectureTime);
    AnalysisResultForLectureRenderModel analysisResultForLectureToRenderModel(AnalysisResultForLecture analysisResultForLecture);
    List<AnalysisResultForLectureRenderModel> analysisResultForLectureListToModelList(List<AnalysisResultForLecture> analysisResultForLectureList);
}
