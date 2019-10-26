package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import budgetbuddy.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path loansFilePath = Paths.get("data", "loans.json");
    private Path ruleFilePath = Paths.get("data", "rules.json");

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
        setLoansFilePath(newUserPrefs.getLoansFilePath());
        setRuleFilePath(newUserPrefs.getRuleFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getLoansFilePath() {
        return loansFilePath;
    }

    public void setLoansFilePath(Path loansFilePath) {
        requireNonNull(loansFilePath);
        this.loansFilePath = loansFilePath;
    }

    public Path getRuleFilePath() {
        return ruleFilePath;
    }

    public void setRuleFilePath(Path ruleFilePath) {
        requireNonNull(ruleFilePath);
        this.ruleFilePath = ruleFilePath;
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
                && loansFilePath.equals(o.loansFilePath)
                && ruleFilePath.equals(o.ruleFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, loansFilePath, ruleFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLoans data file location : " + loansFilePath);
        sb.append("\nRule data file location : " + ruleFilePath);
        return sb.toString();
    }

}
