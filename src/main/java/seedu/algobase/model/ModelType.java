package seedu.algobase.model;

import seedu.algobase.commons.core.index.Index;

/**
 * Models displayed in the GUI.
 */
public enum ModelType {
    PROBLEM (0, "problem", "Problems"),
    TAG (1, "tag", "Tags"),
    PLAN (2, "plan", "Training Plans"),
    TASK (3, "task", "Tasks"),
    FINDRULE (4, "findrule", "Find Rules");

    private final Index displayTabPaneIndex;
    private final String commandArgumentValue;
    private final String tabName;

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
