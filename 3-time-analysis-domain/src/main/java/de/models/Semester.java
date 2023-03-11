package de.models;

import java.time.LocalDate;
import java.util.Objects;

public final class Semester {

    private final String name;
    private final LocalDate start;
    private final LocalDate end;

    public Semester(String name, LocalDate start, LocalDate end) {

        if (Objects.isNull(name)) throw new IllegalStateException("name can not be null");
        if (Objects.isNull(start)) throw new IllegalStateException("start can not be null");
        if (Objects.isNull(end)) throw new IllegalStateException("end can not be null");
        if (start.isAfter(end)) {
            throw new IllegalStateException("start cannot be after end");
        } else if (name.length() == 0) {
            throw new IllegalArgumentException("name must contain at least one letter");
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return name.equals(semester.name) && start.equals(semester.start) && end.equals(semester.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, start, end);
    }
}
