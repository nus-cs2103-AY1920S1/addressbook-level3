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
    private Path addressBookFilePath = Paths.get("data" , "addressbook.json");
    private Path teamListFilePath = Paths.get("data" , "teamlist.json");
    private Path participantListFilePath = Paths.get("data" , "participantlist.json");
    private Path issueListFilePath = Paths.get("data" , "issuelist.json");
    private Path mentorListFilePath = Paths.get("data" , "mentorlist.json");

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
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    //TODO: Remove this method after integration
    public Path getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public Path getTeamListFilePath() {
        return teamListFilePath;
    }

    public Path getParticipantListFilePath() {
        return participantListFilePath;
    }

    public Path getMentorListFilePath() {
        return mentorListFilePath;
    }

    public Path getIssueListFilePath() {
        return issueListFilePath;
    }

    public void setTeamListFilePath(Path teamListFilePath) {
        requireNonNull(teamListFilePath);
        this.teamListFilePath = teamListFilePath;
    }

    public void setParticipantListFilePath(Path participantListFilePath) {
        requireNonNull(participantListFilePath);
        this.participantListFilePath = participantListFilePath;
    }

    public void setMentorListFilePath(Path mentorListFilePath) {
        requireNonNull(mentorListFilePath);
        this.mentorListFilePath = mentorListFilePath;
    }

    public void setIssueListFilePath(Path issueListFilePath) {
        requireNonNull(issueListFilePath);
        this.issueListFilePath = issueListFilePath;
    }

    //TODO: Remove this method after integration
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath = addressBookFilePath;
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
