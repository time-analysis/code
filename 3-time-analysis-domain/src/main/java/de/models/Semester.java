package de.models;

import java.time.LocalDate;

public final class Semester {

    private final String name;
    private final LocalDate start;
    private final LocalDate end;

    public Semester(String name, LocalDate start, LocalDate end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
