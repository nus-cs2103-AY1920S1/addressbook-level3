package seedu.weme.model;

import java.nio.file.Path;

import seedu.weme.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getMemeBookFilePath();

}
