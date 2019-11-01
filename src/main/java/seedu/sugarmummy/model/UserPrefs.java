package seedu.sugarmummy.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data", "addressbook.json");
    private Path userListFilePath = Paths.get("data", "userList.json");
    private Path foodListFilePath = Paths.get("data", "foodlist.json");
    private Path recordListFilePath = Paths.get("data", "recordlist.json");
    private Path eventListFilePath = Paths.get("data", "eventlist.json");
    private Path reminderListFilePath = Paths.get("data", "reminderlist.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

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

    //=========== User List =============================================================

    public Path getUserListFilePath() {
        return userListFilePath;
    }

    public void setUserListFilePath(Path userListFilePath) {
        requireNonNull(userListFilePath);
        this.userListFilePath = userListFilePath;
    }

    //=========== Aesthetics =============================================================

    @Override
    public Colour getFontColour() {
        return new Colour(this.guiSettings.getFontColour());
    }

    @Override
    public void setFontColour(Colour fontColour) {
        requireNonNull(fontColour);
        this.guiSettings.setFontColour(fontColour);
    }

    @Override
    public Background getBackground() {
        return this.guiSettings.getBackground();
    }

    @Override
    public void setBackground(Background background) {
        requireNonNull(background);
        this.guiSettings.setBackground(background);
    }

    //=========== Food Map =============================================================


    public Path getFoodListFilePath() {
        return foodListFilePath;
    }

    //=========== Records =============================================================

    public void setFoodListFilePath(Path foodListFilePath) {
        this.foodListFilePath = foodListFilePath;
    }

    public Path getRecordListFilePath() {
        return recordListFilePath;
    }

    public void setRecordListFilePath(Path recordListFilePath) {
        this.recordListFilePath = recordListFilePath;
    }

    public Path getEventListFilePath() {
        return eventListFilePath;
    }

    public Path getReminderListFilePath() {
        return reminderListFilePath;
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
                && addressBookFilePath.equals(o.addressBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
