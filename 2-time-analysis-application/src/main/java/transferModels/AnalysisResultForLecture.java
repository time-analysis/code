package transferModels;

import de.models.Lecture;

public class AnalysisResultForLecture {
    private Lecture lecture;
    private SelfStudyTimeAndLectureTime selfStudyTimeAndLectureTime;

    public AnalysisResultForLecture(Lecture lecture, SelfStudyTimeAndLectureTime selfStudyTimeAndLectureTime) {
        this.lecture = lecture;
        this.selfStudyTimeAndLectureTime = selfStudyTimeAndLectureTime;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public SelfStudyTimeAndLectureTime getSelfStudyTimeAndLectureTime() {
        return selfStudyTimeAndLectureTime;
    }
}
