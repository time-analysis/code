package de.models;

import java.time.LocalDateTime;

public class Entry {

    private LocalDateTime start;
    private LocalDateTime end;
    private EntryType type;
    private String details;


    public Entry(LocalDateTime start, LocalDateTime end, EntryType type, String details) {
        super();
        if (start.isAfter(end)) {
            throw new IllegalStateException();
        }
        this.start = start;
        this.end = end;
        this.type = type;
        this.details = details;
    }

    public Entry(EntryType type, String details) {
        super();
        this.type = type;
        this.details = details;
    }


    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public EntryType getType() {
        return type;
    }

    public void setType(EntryType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
