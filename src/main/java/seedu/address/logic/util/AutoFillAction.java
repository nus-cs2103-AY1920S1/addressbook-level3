package seedu.address.logic.util;

/**
 * Represents an AutoFill Action to transfer information from Logic to Ui.
 */
public class AutoFillAction extends Action {

    private String text;

    /**
     * Constructs AutoFillAction object.
     * @param text to display
     */
    public AutoFillAction(String text) {
        this.text = text;
    }

    /**
     * Runs the action.
     * @return boolean true if successful
     */
    public boolean action() {
        return true;
    }

    /**
     * Gets String representation of action for AutoFill display.
     * @return String value of action
     */
    public String toString() {
        return text;
    }

}
