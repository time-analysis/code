package de.models;

public final class Lecture {

    private final String name;
    private final Semester semester;
    private final int lectureTime;
    private final int selfStudyTime;


    public Lecture(String name, Semester semester, int lectureTime, int selfStudyTime) {
        super();
        if (name.length() == 0) {
            throw new IllegalArgumentException("Name einer Vorlesung darf nicht leer sein!");
        }
        this.name = name;
        this.semester = semester;
        if (lectureTime < 0 || selfStudyTime < 0) {
            throw new IllegalArgumentException("Zeit darf nicht kleiner Null sein!");
        }
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
