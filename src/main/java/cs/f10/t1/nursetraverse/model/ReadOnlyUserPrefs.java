package cs.f10.t1.nursetraverse.model;

import java.nio.file.Path;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getPatientBookFilePath();

    Path getAppointmentBookFilePath();
}
