package renderModels;

import TransferModels.SelfStudyTimeAndLectureTime;
import ressourceModels.LectureResource;

public class AnalysisResultForLectureRenderModel {

    private LectureResource lecture;
    private SelfStudyTimeAndLectureTimeRenderModel selfStudyTimeAndLectureTime;

    public AnalysisResultForLectureRenderModel(LectureResource lecture, SelfStudyTimeAndLectureTimeRenderModel selfStudyTimeAndLectureTime) {
        this.lecture = lecture;
        this.selfStudyTimeAndLectureTime = selfStudyTimeAndLectureTime;
    }

    public LectureResource getLecture() {
        return lecture;
    }

    public SelfStudyTimeAndLectureTimeRenderModel getSelfStudyTimeAndLectureTime() {
        return selfStudyTimeAndLectureTime;
    }
}
