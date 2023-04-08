public class ActionAndDesciption implements Command {
    private Runnable action;
    private String desciption;

    public ActionAndDesciption(Runnable action, String desciption) {
        this.action = action;
        this.desciption = desciption;
    }

    @Override
    public void run() {
        action.run();
    }

    @Override
    public String getDescription() {
        return desciption;
    }

}
