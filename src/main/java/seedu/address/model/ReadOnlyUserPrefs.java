package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.AppointmentTable;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    AliasTable getAliasTable();

    AppointmentTable getAppointmentTable();

    LocalDate getLastUpdate();

}
