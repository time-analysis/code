package transferModels;

import java.time.Duration;

public class SelfStudyTimeAndLectureTime {
    private Duration selfStudyTime;
    private Duration lectureTime;

    public SelfStudyTimeAndLectureTime(Duration selfStudyTime, Duration lectureTime) {
        this.selfStudyTime = selfStudyTime;
        this.lectureTime = lectureTime;
    }

    public Duration getSelfStudyTime() {
        return selfStudyTime;
    }

    public Duration getLectureTime() {
        return lectureTime;
    }
}
