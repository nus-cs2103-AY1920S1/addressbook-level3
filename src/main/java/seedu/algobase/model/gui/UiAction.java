package seedu.algobase.model.gui;

/**
 * A class representing details of actions that take place in the UI.
 */
public class UiAction {
    private final UiActionType actionType;
    private final UiActionDetails actionDetails;

    public UiAction(UiActionType actionType, UiActionDetails actionDetails) {
        this.actionType = actionType;
        this.actionDetails = actionDetails;
    }

    /**
     * Getter for the word of the action.
     */
    public UiActionType getActionWord() {
        return this.actionType;
    }

    /**
     * Getter for the details of the action.
     */
    public UiActionDetails getActionDetails() {
        return this.actionDetails;
    };
}
