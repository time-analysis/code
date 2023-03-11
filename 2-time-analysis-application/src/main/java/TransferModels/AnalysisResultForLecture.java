package TransferModels;

import ressourceModels.LectureResource;

public class AnalysisResultForLecture {
    private LectureResource lecture;
    private SelfStudyTimeAndLectureTime selfStudyTimeAndLectureTime;

    public AnalysisResultForLecture(LectureResource lecture, SelfStudyTimeAndLectureTime selfStudyTimeAndLectureTime) {
        this.lecture = lecture;
        this.selfStudyTimeAndLectureTime = selfStudyTimeAndLectureTime;
    }

    public LectureResource getLecture() {
        return lecture;
    }

    public SelfStudyTimeAndLectureTime getSelfStudyTimeAndLectureTime() {
        return selfStudyTimeAndLectureTime;
    }
}
