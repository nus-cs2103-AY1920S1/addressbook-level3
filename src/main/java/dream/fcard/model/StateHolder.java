package dream.fcard.model;

/**
 * A class to manage state.
 */
public class StateHolder {
    private static State state;

    public static void makeState() {
        state = new State();
    }

    public static State getState() {
        return state;
    }
}
