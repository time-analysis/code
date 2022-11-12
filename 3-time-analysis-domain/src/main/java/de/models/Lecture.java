package de.models;

public final class Lecture {

    private final String name;
    private final Semester semster;
    private final int presence;
    private final int study;


    public Lecture(String name, Semester semster, int presence, int study) {
        super();
        this.name = name;
        this.semster = semster;
        this.presence = presence;
        this.study = study;
    }

    public String getName() {
        return name;
    }

    public Semester getSemster() {
        return semster;
    }

    public int getPresence() {
        return presence;
    }

    public int getStudy() {
        return study;
    }
}
