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
    private Path intervieweeListFilePath = Paths.get("data", "interviewees.json");
    private Path interviewerListFilePath = Paths.get("data", "interviewers.json");

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
        setIntervieweeListFilePath(newUserPrefs.getIntervieweeListFilePath());
        setInterviewerListFilePath(newUserPrefs.getInterviewerListFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getIntervieweeListFilePath() {
        return this.intervieweeListFilePath;
    }

    @Override
    public Path getInterviewerListFilePath() {
        return this.interviewerListFilePath;
    }

    public void setIntervieweeListFilePath(Path intervieweeListFilePath) {
        requireNonNull(intervieweeListFilePath);
        this.intervieweeListFilePath = intervieweeListFilePath;
    }

    public void setInterviewerListFilePath(Path interviewerListFilePath) {
        requireNonNull(interviewerListFilePath);
        this.interviewerListFilePath = interviewerListFilePath;
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
                && this.intervieweeListFilePath.equals(o.intervieweeListFilePath)
                && this.interviewerListFilePath.equals(o.interviewerListFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, intervieweeListFilePath, interviewerListFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal interviewee data file location : " + intervieweeListFilePath);
        sb.append("\nLocal interviewer data file location : " + interviewerListFilePath);
        return sb.toString();
    }

}
