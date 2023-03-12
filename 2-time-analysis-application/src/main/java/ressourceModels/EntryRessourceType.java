package ressourceModels;

public enum EntryRessourceType implements listeable {
    LECTURE, SELFSTUDY;

    @Override
    public String getDisplayName() {
        return name();
    }
}
