package seedu.address.model.settings;

/**
 * Represents the current theme selected by the user for +Work.
 */
public enum Theme {
    DARK("DARK"), LIGHT("LIGHT");

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid theme code, please enter one of light or dark.";

    private String displayName;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param displayName an alternate name for the theme
     */
    Theme(String displayName) {
        this.displayName = displayName;
    }

    Theme() {
        this.displayName = null;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
