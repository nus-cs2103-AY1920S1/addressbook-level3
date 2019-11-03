package dream.fcard.model;

public class StateHolder {
    static State state;

    public static void makeState() {
        state = new State();
    }

    public static State getState() {
        return state;
    }
}
