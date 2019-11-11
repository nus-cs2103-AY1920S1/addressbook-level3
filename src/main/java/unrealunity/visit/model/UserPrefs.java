package unrealunity.visit.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import javafx.collections.ObservableList;
import unrealunity.visit.commons.core.GuiSettings;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.appointment.Appointment;
import unrealunity.visit.model.appointment.AppointmentTable;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private AliasTable aliasTable;

    /**
     * AppointmentTable to handle Appointment JSON data.
     */
    private AppointmentTable appointmentTable;

    private LocalDate lastUpdate;
    private Path addressBookFilePath = Paths.get("data" , "visit.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
        aliasTable = AliasTable.getDefaultAliasTable();
        appointmentTable = AppointmentTable.getDefaultAppointments();
        lastUpdate = LocalDate.now();
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
        setAliasTable(newUserPrefs.getAliasTable());
        lastUpdate = newUserPrefs.getLastUpdate();
        setAppointmentsTable(newUserPrefs.getAppointmentTable());
    }

    public AliasTable getAliasTable() {
        return aliasTable;
    }

    public void setAliasTable(AliasTable aliasTable) {
        requireNonNull(aliasTable);
        this.aliasTable = aliasTable;
    }

    /**
     * Returns the currently loaded AppointmentTable in UserPrefs.
     *
     * @return The currently loaded AppointmentTable in UserPrefs.
     */
    public AppointmentTable getAppointmentTable() {
        return appointmentTable;
    }

    /**
     * Loads a new AppointmentsTable and calculates if any days need to be
     * decreased due to days passed based on lastUpdate of JSON.
     *
     */
    public void setAppointmentsTable(AppointmentTable appointmentTable) {
        requireNonNull(appointmentTable);
        this.appointmentTable = appointmentTable;
        int dateDiff = Period.between(LocalDate.now(), lastUpdate).getDays();
        if (dateDiff < 0) {
            this.appointmentTable.cascadeDay(Math.abs(dateDiff));
        }
        lastUpdate = LocalDate.now();
        appointmentTable.sortAppointments(); // Sort appointments on launch
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { // this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.equals(o.addressBookFilePath)
                && aliasTable.equals(o.aliasTable)
                && appointmentTable.equals(o.appointmentTable)
                && lastUpdate.equals(o.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, aliasTable, appointmentTable, lastUpdate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nAlias table : " + aliasTable);
        sb.append("\nAppointments table : " + appointmentTable);
        sb.append("\nLast update : " + lastUpdate);
        return sb.toString();
    }

    public boolean removeAlias(String alias) {
        return aliasTable.removeAlias(alias);
    }

    public void addAlias(String alias, String aliasTo) {
        aliasTable.addAlias(alias, aliasTo);
    }

    public String applyAlias(String commandText) {
        return aliasTable.applyAlias(commandText);
    }

    public String getAliases(boolean reusable) {
        return aliasTable.getAlias(reusable);
    }

    /**
     * Returns an ObservableList version of the Appointments for UI usage.
     * This is necessary as AppointmentTable is loaded on launch and
     * AppointmentList uses this data to propagate the UI.
     *
     * @return ObservableList of Appointment objects
     */
    public ObservableList<Appointment> getAppointmentList() {
        return appointmentTable.getAppointmentList();
    }

    /**
     * Adds a new Appointment.
     *
     * @param type The type of appointment. 0 = Reminder, 1 = Follow-Up.
     * @param description The description of the Appointment.
     * @param days How many days the Appointment has remaining.
     */
    public void addAppointment(int type, String description, int days) throws CommandException {
        appointmentTable.addAppointment(type, description, days);
    }

    /**
     * Deletes an appointment from VISIT.
     *
     * @param description The description of the appointment to delete.
     * @param days Optional number of days to specifically target the exact appointment to delete.
     */
    public void deleteAppointment(String description, int days) {
        appointmentTable.deleteAppointment(description, days);
    }

    /**
     * Sorts the list of appointments by days remaining, then name.
     */
    public void sortAppointments() {
        appointmentTable.sortAppointments();
    }

    /**
     * Outputs the Appointments to readable String.
     * Used in the Message of the Day output.
     */
    public String outputAppointments() {
        return appointmentTable.outputAppointments();
    }

    /**
     * Reset Appointment Data completely.
     */
    public void resetAppointments() {
        appointmentTable = AppointmentTable.getDefaultAppointments();
    }

    /**
     * Gets the date that the JSON was last updated.
     */
    public LocalDate getLastUpdate() {
        return lastUpdate;
    }
}
