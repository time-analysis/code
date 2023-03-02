package ressourceModels;

import de.models.Semester;

public class LectureResource {
    private String name;
    private String semester;
    private int lectureTime;
    private int selfStudyTime;

    public LectureResource(String name, String semester, int lectureTime, int selfStudyTime) {
        this.name = name;
        this.semester = semester;
        this.lectureTime = lectureTime;
        this.selfStudyTime = selfStudyTime;
    }

    public LectureResource() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getLectureTime() {
        return lectureTime;
    }

    public void setLectureTime(int lectureTime) {
        this.lectureTime = lectureTime;
    }

    public int getSelfStudyTime() {
        return selfStudyTime;
    }

    public void setSelfStudyTime(int selfStudyTime) {
        this.selfStudyTime = selfStudyTime;
    }
}
