package ressourceModels;

public class EntryRessource implements listeable {
    private String start;
    private String end;
    private EntryRessourceType type;
    private String details;
    private String lecture;
    private EntryRessourceStatus status;
    private String id;

    public EntryRessource(String start, String end, EntryRessourceType type, String details, String lecture, EntryRessourceStatus status, String id) {
        this(start, type, lecture, status, id);
        this.end = end;
        this.details = details;
    }

    public EntryRessource(String start, EntryRessourceType type, String lecture, EntryRessourceStatus status, String id) {
        this(start, type, lecture, status);
        this.id = id;
    }

    public EntryRessource(String start, String end, EntryRessourceType type, String details, String lecture, EntryRessourceStatus status) {
        this(start, type, lecture, status);
        this.end = end;
        this.details = details;
    }

    public EntryRessource(String start, EntryRessourceType type, String lecture, EntryRessourceStatus status) {
        this.start = start;
        this.type = type;
        this.lecture = lecture;
        this.status = status;
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

    public EntryRessourceType getType() {
        return type;
    }

    public void setType(EntryRessourceType type) {
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

    public EntryRessourceStatus getStatus() {
        return status;
    }

    public void setStatus(EntryRessourceStatus status) {
        this.status = status;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String getDisplayName() {
        return String.format("Entry of type %s started at %s for lecture %s", type, start, lecture);
    }


}
