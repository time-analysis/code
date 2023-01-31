package de.models;

public final class Lecture {

    private final String name;
    private final Semester semester;
    private final int lectureTime;
    private final int selfStudyTime;


    public Lecture(String name, Semester semester, int lectureTime, int selfStudyTime) {
        super();
        this.name = name;
        this.semester = semester;
        this.lectureTime = lectureTime;
        this.selfStudyTime = selfStudyTime;
    }

    public String getName() {
        return name;
    }

    public Semester getSemester() {
        return semester;
    }

    public int getLectureTime() {
        return lectureTime;
    }

    public int getSelfStudyTime() {
        return selfStudyTime;
    }
}
