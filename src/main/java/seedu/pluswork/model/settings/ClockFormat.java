package seedu.pluswork.model.settings;

import java.time.format.DateTimeFormatter;

import seedu.pluswork.commons.util.DateTimeUtil;

/**
 * Represents the current display format chosen by the user for task deadlines in +Work.
 */
public enum ClockFormat {
    TWELVE("12HR", DateTimeUtil.getDisplayFormatterTwelveHour()),
    TWENTY_FOUR("24HR", DateTimeUtil.getDisplayFormatterTwentyFourHour());

    public static final String DISPLAY_NAME_TWELVE = "12HR";
    public static final String DISPLAY_NAME_TWENTY_FOUR = "24HR";

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid clock format, please enter one of twelve or twenty_four.";

    private String displayName;
    private DateTimeFormatter formatter;

    /**
     * Enum constructor to give UI-friendly display names.
     * Cannot be called by other components.
     *
     * @param displayName an alternate name for the theme
     */
    ClockFormat(String displayName, DateTimeFormatter formatter) {
        this.displayName = displayName;
        this.formatter = formatter;
    }

    ClockFormat() {
        this.displayName = null;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public DateTimeFormatter getDisplayFormatter() {
        return this.formatter;
    }
}
