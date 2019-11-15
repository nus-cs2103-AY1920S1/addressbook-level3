package seedu.pluswork.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.model.settings.ClockFormat;
import seedu.pluswork.model.settings.Theme;

/**
 * Represents user settings of +Work.
 */
public class UserSettings implements ReadOnlyUserSettings {

    private Path userSettingsFilePath = Paths.get("data", "plusworksettings.json");

    // default settings
    private Theme theme = Theme.DARK;
    private ClockFormat clockFormat = ClockFormat.TWENTY_FOUR;

    /**
     * Creates a {@code UserSettings} with default values.
     */
    public UserSettings() {
    }

    /**
     * Creates a {@code UserSettings} with the settings in {@code userSettings}
     */
    public UserSettings(ReadOnlyUserSettings userSettings) {
        this();
        resetData(userSettings);
    }

    /**
     * Resets the existing data of this {@code UserSettings} with {@code newUserSettings}.
     */
    public void resetData(ReadOnlyUserSettings newUserSettings) {
        requireNonNull(newUserSettings);
        setClockFormat(newUserSettings.getClockFormat());
        setTheme(newUserSettings.getTheme());
        setUserSettingsFilePath(newUserSettings.getUserSettingsFilePath());
    }

    @Override
    public Path getUserSettingsFilePath() {
        return userSettingsFilePath;
    }

    public void setUserSettingsFilePath(Path newUserSettingsFilePath) {
        requireNonNull(userSettingsFilePath);
        userSettingsFilePath = newUserSettingsFilePath;
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme newTheme) {
        theme = newTheme;
    }

    @Override
    public ClockFormat getClockFormat() {
        return clockFormat;
    }

    public void setClockFormat(ClockFormat newClockFormat) {
        DateTimeUtil.switchDisplayFormat(newClockFormat.getDisplayFormatter());
        clockFormat = newClockFormat;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserSettings)) { //this handles null as well.
            return false;
        }

        UserSettings o = (UserSettings) other;

        return theme.equals(o.theme)
                && clockFormat.equals(o.clockFormat)
                && userSettingsFilePath.equals(o.userSettingsFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userSettingsFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current theme : " + theme);
        sb.append("Current clockFormat : " + clockFormat.getDisplayName());
        sb.append("\nLocal data file location : " + userSettingsFilePath);
        return sb.toString();
    }
}
