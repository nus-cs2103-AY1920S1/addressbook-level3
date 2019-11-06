package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path athletickFilePath = Paths.get("data" , "athletick.json");
    private Path performanceFilePath = Paths.get("data", "events.json");
    private Path attendanceFilePath = Paths.get("data", "attendance.json");
    private Path imageFilePath = Paths.get("images", "text.txt");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAthletickFilePath(newUserPrefs.getAthletickFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAthletickFilePath() {
        return athletickFilePath;
    }

    public void setAthletickFilePath(Path athletickFilePath) {
        requireNonNull(athletickFilePath);
        this.athletickFilePath = athletickFilePath;
    }

    public Path getPerformanceFilePath() {
        return performanceFilePath;
    }

    public void setPerformanceFilePath(Path performanceFilePath) { // for tests, not implemented yet
        requireNonNull(performanceFilePath);
        this.performanceFilePath = performanceFilePath;
    }

    public Path getAttendanceFilePath() {
        return attendanceFilePath;
    }

    public Path getImageFilePath() {
        return imageFilePath;
    }

    public void setAttendanceFilePath(Path attendanceFilePath) {
        requireNonNull(attendanceFilePath);
        this.attendanceFilePath = attendanceFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && athletickFilePath.equals(o.athletickFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, athletickFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + athletickFilePath);
        return sb.toString();
    }

}
