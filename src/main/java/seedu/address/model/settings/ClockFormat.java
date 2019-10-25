package seedu.address.model.settings;

/**
 * Represents the current display format chosen by the user for task deadlines in +Work.
 */
public enum ClockFormat {
    TWELVE("12HR"), TWENTY_FOUR("24HR");

    public static final String MESSAGE_CONSTRAINTS =
        "Invalid clock format, please enter one of twelve or twenty_four.";

    private String displayName;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param displayName an alternate name for the theme
     */
    ClockFormat(String displayName) {
        this.displayName = displayName;
    }

    ClockFormat() {
        this.displayName = null;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
