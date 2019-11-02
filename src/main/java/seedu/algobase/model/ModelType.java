package seedu.algobase.model;

import seedu.algobase.commons.core.index.Index;

/**
 * Models displayed in the GUI.
 */
public enum ModelType {
    PROBLEM (0, "problem", "Problems"),
    TAG (1, "tag", "Tags"),
    PLAN (2, "plan", "Training Plans"),
    FINDRULE (3, "findrule", "Find Rules"),
    TASK (4, "task", "Tasks");


    private final Index displayTabPaneIndex;
    private final String commandArgumentValue;
    private final String tabName;
    public static final int numberOfTabs = 4;

    ModelType(int displayTabPaneIndex, String commandArgumentValue, String tabName) {
        this.displayTabPaneIndex = Index.fromZeroBased(displayTabPaneIndex);
        this.commandArgumentValue = commandArgumentValue;
        this.tabName = tabName;
    }

    public Index getDisplayTabPaneIndex() {
        return displayTabPaneIndex;
    }

    public String getCommandArgumentValue() {
        return commandArgumentValue;
    }

    public String getTabName() {
        return tabName;
    }
}
