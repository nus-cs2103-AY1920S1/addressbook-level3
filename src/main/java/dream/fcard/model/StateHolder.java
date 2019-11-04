package dream.fcard.model;

/**
 * A class to manage state.
 */
public class StateHolder {
    private static State state;

    public static State getState() {
        if (state == null) {
            state = new State();
        }
        return state;
    }
}
