package de.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Entry {

    private LocalDateTime start;
    private LocalDateTime end;
    private EntryType type;
    private String details;
    private Lecture lecture;

    public Entry(LocalDateTime start, EntryType type, Lecture lecture) {
        if (Objects.isNull(start)) throw new IllegalStateException("start can not be null");
        if (Objects.isNull(type)) throw new IllegalStateException("type can not be null");
        if (Objects.isNull(lecture)) throw new IllegalStateException("lecture can not be null");
        this.start = start;
        this.type = type;
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

    public void finishEntry(LocalDateTime end, String details) {
        if (start.isAfter(end)) {
            throw new IllegalStateException();
        }
        if (Objects.isNull(this.end)) {
            this.end = end;
        } else {
            System.out.println("end is already set!");
        }
        if (details.length() == 0) {
            this.details = "No details available";
        } else {
            this.details = details;
        }

    }

    public Duration calculateDuration() {
        return Duration.between(start, end);
    }
}
