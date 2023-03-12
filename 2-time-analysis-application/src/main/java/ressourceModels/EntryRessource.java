package ressourceModels;

public class EntryRessource implements listeable {
    private String start;
    private String end;
    private String type;
    private String details;
    private String lecture;
    private String status;

    public EntryRessource(String start, String end, String type, String details, String lecture, String status) {
        this(start, type, lecture, status);
        this.end = end;
        this.details = details;
    }

    public EntryRessource(String start, String type, String lecture,String status) {
        this.start = start;
        this.type = type;
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

    @Override
    public String getDisplayName() {
        return String.format("Entry of type %s started at %s for lecture %s",type,start,lecture);
    }


}
