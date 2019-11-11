package seedu.address.model.calendar;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyCalendarUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
