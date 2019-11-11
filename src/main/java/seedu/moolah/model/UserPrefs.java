package seedu.moolah.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.alias.AliasMappings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path mooLahFilePath = Paths.get("data" , "moolah.json");
    private AliasMappings aliasMappings = new AliasMappings();

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
        setMooLahFilePath(newUserPrefs.getMooLahFilePath());
        setAliasMappings(newUserPrefs.getAliasMappings());
    }

    @Override
    public ReadOnlyUserPrefs copy() {
        return new UserPrefs(this);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getMooLahFilePath() {
        return mooLahFilePath;
    }

    public void setMooLahFilePath(Path mooLahFilePath) {
        requireNonNull(mooLahFilePath);
        this.mooLahFilePath = mooLahFilePath;
    }

    public AliasMappings getAliasMappings() {
        return aliasMappings;
    }

    public void setAliasMappings(AliasMappings aliasMappings) {
        requireNonNull(aliasMappings);
        this.aliasMappings = aliasMappings;
    }

    public boolean removeAliasWithName(String name) {
        return aliasMappings.removeAlias(name);
    }

    /**
     * Add a user defined {@code Alias} to the user prefs' {@code AliasMappings}
     * @param alias
     */
    public void addUserAlias(Alias alias) {
        requireNonNull(alias);
        this.aliasMappings = aliasMappings.addAlias(alias);
    }

    public Alias getAlias(String aliasName) {
        requireNonNull(aliasName);
        return this.aliasMappings.getAlias(aliasName);
    }

    @Override
    public boolean hasAlias(String aliasName) {
        return this.aliasMappings.aliasWithNameExists(aliasName);
    }

    /**
     * Returns true if the {@code String aliasName} is a reserved command word, and false otherwise.
     * @param alias
     */
    public boolean aliasNameIsReserved(Alias alias) {
        requireNonNull(alias);
        return this.aliasMappings.aliasUsesReservedName(alias);
    }

    /**
     * Returns true if the {@code String commandWord} of an {@code Alias} is an alias name mapped to an
     * existing {@code Alias}, and false otherwise.
     * @param alias
     */
    public boolean aliasCommandWordIsAlias(Alias alias) {
        requireNonNull(alias);
        return this.aliasMappings.aliasCommandWordIsAlias(alias);
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
                && mooLahFilePath.equals(o.mooLahFilePath)
                && aliasMappings.equals(o.aliasMappings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, mooLahFilePath, aliasMappings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + mooLahFilePath);
        return sb.toString();
    }

}
