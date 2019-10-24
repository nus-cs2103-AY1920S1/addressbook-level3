package seedu.jarvis.model.userprefs;

import java.nio.file.Path;

import seedu.jarvis.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Path getHistoryManagerFilePath();

    Path getCcaTrackerFilePath();

}
