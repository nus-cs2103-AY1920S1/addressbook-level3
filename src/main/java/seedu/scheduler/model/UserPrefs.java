package seedu.scheduler.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.scheduler.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path intervieweeListFilePath = Paths.get("data", "interviewees.json");
    private Path interviewerListFilePath = Paths.get("data", "interviewers.json");
    private String startTime = "10:00";
    private String endTime = "21:00";
    private int durationPerSlot = 30;
    private String organisation = "Committee of Schedulers";
    private String interviewLocation = "Level 3, Block YI2";
    private List<String> ccEmails = Arrays.asList("test@example.com", "president@comsch.invalid.tld");
    private String emailSubject = "Committee of Schedulers (ComSch) Interview";
    private String emailAdditionalInformation = "Thank you.";

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
        setDurationPerSlot(newUserPrefs.getDurationPerSlot());
        setStartTime(newUserPrefs.getStartTime());
        setEndTime(newUserPrefs.getEndTime());
        setCcEmails(newUserPrefs.getCcEmails());
        setEmailAdditionalInformation(newUserPrefs.getEmailAdditionalInformation());
    }

    @Override
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

    @Override
    public String getStartTime() {
        return this.startTime;
    }

    @Override
    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public int getDurationPerSlot() {
        return this.durationPerSlot;
    }

    public void setDurationPerSlot(int durationPerSlot) {
        this.durationPerSlot = durationPerSlot;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
    public String getOrganisation() {
        return this.organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    @Override
    public String getInterviewLocation() {
        return this.interviewLocation;
    }

    public void setInterviewLocation(String interviewLocation) {
        this.interviewLocation = interviewLocation;
    }

    @Override
    public List<String> getCcEmails() {
        return this.ccEmails;
    }

    public void setCcEmails(List<String> ccEmails) {
        this.ccEmails = ccEmails;
    }

    @Override
    public String getEmailSubject() {
        return this.emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    @Override
    public String getEmailAdditionalInformation() {
        return this.emailAdditionalInformation;
    }

    public void setEmailAdditionalInformation(String emailAdditionalInformation) {
        this.emailAdditionalInformation = emailAdditionalInformation;
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
        sb.append("\nStart Time : " + startTime);
        sb.append("\nEnd Time : " + endTime);
        sb.append("\nDuration per slot : " + durationPerSlot);
        sb.append("\nOrganisation name : " + organisation);
        sb.append("\nInterview location : " + interviewLocation);
        sb.append("\nCc emails : " + ccEmails.toString());
        sb.append("\nEmail subject : " + emailSubject);
        sb.append("\nAdditional information : " + emailAdditionalInformation);
        return sb.toString();
    }

}
