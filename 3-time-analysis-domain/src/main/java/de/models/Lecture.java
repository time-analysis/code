package de.models;

import java.util.ArrayList;
import java.util.List;

public class Lecture {

    private String name;
    private Semester semster;
    private int presence;
    private int study;


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

    public void setName(String name) {
        this.name = name;
    }

    public Semester getSemster() {
        return semster;
    }

    public void setSemster(Semester semster) {
        this.semster = semster;
    }

    public int getPresence() {
        return presence;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public int getStudy() {
        return study;
    }

    public void setStudy(int study) {
        this.study = study;
    }
}
