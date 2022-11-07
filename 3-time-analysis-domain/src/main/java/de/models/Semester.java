package de.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Semester {

    private String name;
    private LocalDate start;
    private LocalDate end;

    public Semester(String name, LocalDate start, LocalDate end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

}
