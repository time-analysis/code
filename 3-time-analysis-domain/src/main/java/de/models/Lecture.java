package de.models;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return lectureTime == lecture.lectureTime && selfStudyTime == lecture.selfStudyTime && name.equals(lecture.name) && semester.equals(lecture.semester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, semester, lectureTime, selfStudyTime);
    }
}
