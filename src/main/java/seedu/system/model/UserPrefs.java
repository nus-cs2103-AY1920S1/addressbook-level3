package seedu.system.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.system.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path personDataFilePath = Paths.get("data" , "personData.json");
    private Path competitionDataFilePath = Paths.get("data" , "competitionData.json");
    private Path participationDataFilePath = Paths.get("data" , "participationData.json");

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
        setPersonDataFilePath(newUserPrefs.getPersonDataFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPersonDataFilePath() {
        return personDataFilePath;
    }

    public void setPersonDataFilePath(Path dataFilePath) {
        requireNonNull(dataFilePath);
        this.personDataFilePath = dataFilePath;
    }

    public Path getCompetitionDataFilePath() {
        return competitionDataFilePath;
    }

    public void setCompetitionDataFilePath(Path dataFilePath) {
        requireNonNull(dataFilePath);
        this.competitionDataFilePath = dataFilePath;
    }

    public Path getParticipationDataFilePath() {
        return participationDataFilePath;
    }

    public void setParticipationDataFilePath(Path dataFilePath) {
        requireNonNull(dataFilePath);
        this.participationDataFilePath = dataFilePath;
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
                && personDataFilePath.equals(o.personDataFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, personDataFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + personDataFilePath);
        return sb.toString();
    }

}
