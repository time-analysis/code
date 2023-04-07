package renderModels;

public class SelfStudyTimeAndLectureTimeRenderModel {
    private String selfStudyTime;
    private String lectureTime;

    public SelfStudyTimeAndLectureTimeRenderModel(String selfStudyTime, String lectureTime) {
        this.selfStudyTime = selfStudyTime;
        this.lectureTime = lectureTime;
    }

    public String getSelfStudyTime() {
        return selfStudyTime;
    }

    public String getLectureTime() {
        return lectureTime;
    }
}
