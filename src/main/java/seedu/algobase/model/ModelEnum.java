package seedu.algobase.model;

/**
 * Models displayed in the GUI.
 */
public enum ModelEnum {
    PROBLEM (0, "problem", "Problems"),
    TAG (1, "tag", "Tags"),
    PLAN (2, "plan", "Training Plans");

    private final int displayTabPaneIndex;
    private final String commandArgumentValue;
    private final String displayValue;

    ModelEnum(int displayTabPaneIndex, String commandArgumentValue, String displayValue) {
        this.displayTabPaneIndex = displayTabPaneIndex;
        this.commandArgumentValue = commandArgumentValue;
        this.displayValue = displayValue;
    }

    public int getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    public String getCommandArgumentValue() {
        return commandArgumentValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
