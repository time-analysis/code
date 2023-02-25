package models;

import de.models.EntryType;
import de.models.Lecture;
import de.models.Semester;

import java.time.LocalDateTime;

public class EntryRessource {
    private String start;
    private String end;
    private String type;
    private String details;
    private String lecture;

    public EntryRessource(LocalDateTime start, LocalDateTime end, EntryType type, String details, Lecture lecture) {
        this.start = start.toString();
        this.end = end.toString();
        this.type = type.toString();
        this.details = details;
        this.lecture = lecture.getName();
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }
}
