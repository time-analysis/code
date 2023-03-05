public class ActionAndDesciption {
    private Runnable action;
    private String desciption;

    public ActionAndDesciption(Runnable action, String desciption) {
        this.action = action;
        this.desciption = desciption;
    }

    public Runnable getAction() {
        return action;
    }

    public String getDesciption() {
        return desciption;
    }
}
