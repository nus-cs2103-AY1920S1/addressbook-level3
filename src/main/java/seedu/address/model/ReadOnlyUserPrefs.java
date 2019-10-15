package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    AppSettings getAppSettings();

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Path getTimeBookFilePath();

    Path getCondensedModuleListFilePath();

    Path getDetailedModuleListFilePath();

    Path getAcademicCalendarFilePath();

    Path getHolidaysFilePath();

}
