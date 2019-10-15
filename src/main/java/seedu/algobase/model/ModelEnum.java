package seedu.algobase.model;

/**
 * Models displayed in the GUI.
 */
public enum ModelEnum {
    PROBLEM (0, "problem", "Problems"),
    TAG (1, "tag", "Tags"),
    PLAN (2, "plan", "Training Plans"),
    TASK (3, "task", "Tasks");

    private final int displayTabPaneIndex;
    private final String commandArgumentValue;
    private final String tabName;

    ModelEnum(int displayTabPaneIndex, String commandArgumentValue, String tabName) {
        this.displayTabPaneIndex = displayTabPaneIndex;
        this.commandArgumentValue = commandArgumentValue;
        this.tabName = tabName;
    }

    public int getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    public String getCommandArgumentValue() {
        return commandArgumentValue;
    }

    public String getTabName() {
        return tabName;
    }
}
