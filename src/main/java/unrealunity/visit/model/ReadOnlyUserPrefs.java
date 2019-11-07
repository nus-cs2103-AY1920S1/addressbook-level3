package unrealunity.visit.model;

import java.nio.file.Path;
import java.time.LocalDate;

import unrealunity.visit.commons.core.GuiSettings;
import unrealunity.visit.model.appointment.AppointmentTable;

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
