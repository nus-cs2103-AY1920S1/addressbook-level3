package seedu.address.logic.util;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public class AutoFillAction extends Action {

    private String text;

    public AutoFillAction(String text) {
        this.text = text;
    }

    /**
     * Runs the intended action
     */
    public boolean action() {
        return true;
    }

    public String toString() {
        return text;
    }

}
