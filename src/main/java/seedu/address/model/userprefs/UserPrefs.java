package seedu.address.model.userprefs;

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
    private Path patientAddressBookFilePath = Paths.get("data" , "patientDetails.json");
    private Path staffAddressBookFilePath = Paths.get("data" , "staffDetails.json");
    private Path appointmentBookFilePath = Paths.get("data" , "appointments.json");
    private Path dutyRosterBookFilePath = Paths.get("data" , "dutyRoster.json");

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
        setPatientAddressBookFilePath(newUserPrefs.getPatientAddressBookFilePath());
        setStaffAddressBookFilePath(newUserPrefs.getStaffAddressBookFilePath());
        setPatientAppointmentBookFilePath(newUserPrefs.getPatientAppointmentBookFilePath());
        setDutyRosterBookFilePath(newUserPrefs.getStaffAddressBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPatientAddressBookFilePath() {
        return patientAddressBookFilePath;
    }

    public void setPatientAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.patientAddressBookFilePath = addressBookFilePath;
    }

    public Path getPatientAppointmentBookFilePath() {
        return appointmentBookFilePath;
    }

    public void setPatientAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        this.appointmentBookFilePath = appointmentBookFilePath;
    }

    public Path getStaffAddressBookFilePath() {
        return staffAddressBookFilePath;
    }

    public void setStaffAddressBookFilePath(Path staffAddressBookFilePath) {
        requireNonNull(staffAddressBookFilePath);
        this.staffAddressBookFilePath = staffAddressBookFilePath;
    }

    public Path getDutyRosterBookFilePath() {
        return dutyRosterBookFilePath;
    }

    public void setDutyRosterBookFilePath(Path dutyRosterBookFilePath) {
        requireNonNull(dutyRosterBookFilePath);
        this.dutyRosterBookFilePath = dutyRosterBookFilePath;
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
                && patientAddressBookFilePath.equals(o.patientAddressBookFilePath)
                && staffAddressBookFilePath.equals(o.staffAddressBookFilePath)
                && appointmentBookFilePath.equals(o.appointmentBookFilePath)
                && dutyRosterBookFilePath.equals(o.dutyRosterBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, patientAddressBookFilePath,
            staffAddressBookFilePath, appointmentBookFilePath,
            dutyRosterBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal patient details data file location : " + patientAddressBookFilePath);
        sb.append("\nLocal staff details data file location : " + staffAddressBookFilePath);
        sb.append("\nLocal appointment data file location : " + appointmentBookFilePath);
        sb.append("\nLocal duty roster data file location : " + dutyRosterBookFilePath);
        return sb.toString();
    }

}
