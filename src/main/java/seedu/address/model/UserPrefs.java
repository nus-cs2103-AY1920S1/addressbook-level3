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
    private Path studyBuddyProFilePath = Paths.get("data" , "studybuddypro.json");
    private Path cheatSheetFilePath = Paths.get("data", "cheatsheets.json");
    private Path flashcardFilePath = Paths.get("data", "flashcards.json");
    private Path noteFilePath = Paths.get("data", "notes.json");

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
        setStudyBuddyProFilePath(newUserPrefs.getStudyBuddyProFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getStudyBuddyProFilePath() {
        return studyBuddyProFilePath;
    }

    public Path getCheatSheetFilePath() {
        return cheatSheetFilePath;
    }

    public Path getFlashcardFilePath() {
        return flashcardFilePath;
    }

    public Path getNoteFilePath() {
        return noteFilePath;
    }

    public void setStudyBuddyProFilePath(Path studyBuddyProFilePath) {
        requireNonNull(studyBuddyProFilePath);
        this.studyBuddyProFilePath = studyBuddyProFilePath;
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
                && studyBuddyProFilePath.equals(o.studyBuddyProFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, studyBuddyProFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + studyBuddyProFilePath);
        return sb.toString();
    }

}
