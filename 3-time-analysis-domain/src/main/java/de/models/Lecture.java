package de.models;

import java.util.Objects;

public final class Lecture {

    private final String name;
    private final Semester semester;
    private final int lectureTime;
    private final int selfStudyTime;


    public Lecture(String name, Semester semester, int lectureTime, int selfStudyTime) {
        super();
        if (Objects.isNull(name)) throw new IllegalStateException("name can not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name of a lecture can not be empty");
        }
        this.name = name;
        if (Objects.isNull(semester)) throw new IllegalStateException("semester can not be null");
        this.semester = semester;
        if (lectureTime < 0 || selfStudyTime < 0) {
            throw new IllegalArgumentException("Time can not be less then zero");
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
        return name.equals(lecture.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
