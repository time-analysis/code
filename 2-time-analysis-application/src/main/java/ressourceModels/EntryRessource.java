package ressourceModels;

public class EntryRessource {
    private String start;
    private String end;
    private String type;
    private String details;
    private String lecture;
    private String status;

    public EntryRessource(String start, String end, String type, String details, String lecture, String status) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.details = details;
        this.lecture = lecture;
        this.status = status;
    }

    public EntryRessource() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
