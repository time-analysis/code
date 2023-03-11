package ressourceModels;

public class SemesterRessource implements  listeable {
    private String name;
    private String start;
    private String end;

    public SemesterRessource(String name, String start, String end) {
        this.name = name;
        this.start = start;
        this.end = end;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}
