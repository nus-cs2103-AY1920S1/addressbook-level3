package cs.f10.t1.nursetraverse.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path patientBookFilePath = Paths.get("data" , "patientBook.json");
    private Path appointmentBookFilePath = Paths.get("data" , "appointmentBook.json");

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
        setPatientBookFilePath(newUserPrefs.getPatientBookFilePath());
        setAppointmentBookFilePath(newUserPrefs.getAppointmentBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPatientBookFilePath() {
        return patientBookFilePath;
    }

    public void setPatientBookFilePath(Path patientBookFilePath) {
        requireNonNull(patientBookFilePath);
        this.patientBookFilePath = patientBookFilePath;
    }

    public Path getAppointmentBookFilePath() {
        return appointmentBookFilePath;
    }

    public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        this.appointmentBookFilePath = appointmentBookFilePath;
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
                && patientBookFilePath.equals(o.patientBookFilePath)
                && appointmentBookFilePath.equals(o.appointmentBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, patientBookFilePath, appointmentBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + patientBookFilePath);
        sb.append("\nLocal appointments data file location : " + appointmentBookFilePath);
        return sb.toString();
    }

}
