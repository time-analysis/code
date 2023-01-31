package de.models;

import java.time.Duration;
import java.time.LocalDateTime;

public class Entry {

    private LocalDateTime start;
    private LocalDateTime end;
    private EntryType type;
    private String details;
    private Lecture lecture;

    public Entry(LocalDateTime start, EntryType type, String details, Lecture lecture) {
        this.start = start;
        this.type = type;
        this.details = details;
        this.lecture = lecture;
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

    public void setEnd(LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new IllegalStateException();
        }
        this.end = end;
    }

    public Duration calculateDuration() {
        return Duration.between(start, end);
    }
}
