package de.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Entry {

    private LocalDateTime start;
    private LocalDateTime end;
    private EntryType type;
    private String details;
    private Lecture lecture;
    private EntryStatus status;
    private UUID id;

    public Entry(LocalDateTime start, EntryType type, Lecture lecture) {
        this(start, type, lecture, UUID.randomUUID());
    }

    public Entry(LocalDateTime start, LocalDateTime end, EntryType type, Lecture lecture, String details) {
        this(start, type, lecture);
        finishEntry(end, details);
    }

    public Entry(LocalDateTime start, EntryType type, Lecture lecture, UUID id) {
        if (Objects.isNull(start)) throw new IllegalStateException("start can not be null");
        if (Objects.isNull(type)) throw new IllegalStateException("type can not be null");
        if (Objects.isNull(lecture)) throw new IllegalStateException("lecture can not be null");
        this.start = start;
        this.type = type;
        this.lecture = lecture;
        this.id = id;
        this.status = EntryStatus.RUNNING;
    }

    public Entry(LocalDateTime start, LocalDateTime end, EntryType type, Lecture lecture, String details, UUID id) {
        this(start, type, lecture, id);
        finishEntry(end, details);
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

    public EntryStatus getStatus() {
        return status;
    }

    public UUID getId() {
        return id;
    }

    public void finishEntry(LocalDateTime end, String details) {
        if (Objects.isNull(end)) throw new IllegalStateException("end can not be null");
        if (start.isAfter(end)) {
            throw new IllegalStateException("start can not be after end");
        }
        if (Objects.isNull(this.end) && status.equals(EntryStatus.RUNNING)) {
            this.end = end;
        } else {
            throw new UnsupportedOperationException("Entry is already finished");
        }
        if (details.length() == 0) {
            this.details = "No details available";
        } else {
            this.details = details;
        }
        this.status = EntryStatus.FINISHED;
    }

    public Duration calculateDuration() {
        if (Objects.isNull(this.end) || status.equals(EntryStatus.RUNNING)) {
            throw new UnsupportedOperationException("Entry needs to be finished in order to be able to calculate Duration");
        }

        return Duration.between(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return id.equals(entry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
