package tagline.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import tagline.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path noteBookFilePath = Paths.get("data", "notebook.json");
    private Path groupBookFilePath = Paths.get("data", "groupbook.json");

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
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setNoteBookFilePath(newUserPrefs.getNoteBookFilePath());
        setGroupBookFilePath(newUserPrefs.getGroupBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
    }

    public Path getNoteBookFilePath() {
        return noteBookFilePath;
    }

    public void setNoteBookFilePath(Path noteBookFilePath) {
        requireNonNull(noteBookFilePath);
        this.noteBookFilePath = noteBookFilePath;
    }

    public Path getGroupBookFilePath() {
        return groupBookFilePath;
    }

    public void setGroupBookFilePath(Path groupBookFilePath) {
        requireNonNull(groupBookFilePath);
        this.groupBookFilePath = groupBookFilePath;
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
                && addressBookFilePath.equals(o.addressBookFilePath)
                && noteBookFilePath.equals(o.noteBookFilePath)
                && groupBookFilePath.equals(o.groupBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, noteBookFilePath, groupBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data addressbook file location : " + addressBookFilePath);
        sb.append("\nLocal data notebook file location : " + noteBookFilePath);
        sb.append("\nLocal data groupbook file location : " + groupBookFilePath);
        return sb.toString();
    }

}
