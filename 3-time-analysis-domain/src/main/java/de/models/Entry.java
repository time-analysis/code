package de.models;

import java.time.LocalDateTime;

public final class Entry {

    private final LocalDateTime start;
    private final LocalDateTime end;
    private final EntryType type;
    private final String details;
    private final Lecture lecture;

    public Entry(LocalDateTime start, LocalDateTime end, EntryType type, String details, Lecture lecture) {
        super();
        if (start.isAfter(end)) {
            throw new IllegalStateException();
        }
        this.start = start;
        this.end = end;
        this.type = type;
        this.details = details;
        this.lecture = lecture;
    }

    public Entry(EntryType type, String details, Lecture lecture) {
        super();
        this.type = type;
        this.details = details;
        this.lecture = lecture;
        this.start = LocalDateTime.now();
        this.end = LocalDateTime.now();
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public EntryType getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public Lecture getLecture() {
        return lecture;
    }
}
