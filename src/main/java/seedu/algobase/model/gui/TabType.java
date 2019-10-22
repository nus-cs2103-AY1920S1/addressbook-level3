package seedu.algobase.model.gui;

/**
 * Types of tabs in the GUI.
 */
public enum TabType {
    DISPLAY("Display"),
    DETAILS("Details");

    private String name;

    TabType(String readableName) {
        this.name = readableName;
    }

    public String getName() {
        return name;
    }
}
