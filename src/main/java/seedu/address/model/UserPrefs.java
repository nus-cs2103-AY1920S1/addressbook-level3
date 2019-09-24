package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.Alias;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserAliases;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private UserAliases userAliases = new UserAliases();

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
        setUserAliases(newUserPrefs.getUserAliases());
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

    public UserAliases getUserAliases() {
        return userAliases;
    }

    public void setUserAliases(UserAliases userAliases) {
        requireNonNull(userAliases);
        this.userAliases = userAliases;
    }

    public void addUserAlias(Alias alias) {
        requireNonNull(alias);
        this.userAliases = userAliases.addAlias(alias);
    }

    public Alias getUserAlias(String aliasName) {
        requireNonNull(aliasName);
        return this.userAliases.getAlias(aliasName);
    }

    public boolean aliasNameIsReserved(String aliasName) {
        requireNonNull(aliasName);
        return this.userAliases.aliasNameIsReserved(aliasName);
    }

    public boolean aliasCommandWordIsAlias(String commandWord) {
        requireNonNull(commandWord);
        return this.userAliases.aliasCommandWordIsAlias(commandWord);
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
                && userAliases.equals(o.userAliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, userAliases);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
