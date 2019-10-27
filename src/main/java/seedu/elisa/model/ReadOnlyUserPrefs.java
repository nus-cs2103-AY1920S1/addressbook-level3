package seedu.elisa.model;

import java.nio.file.Path;

import seedu.elisa.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getItemStorageFilePath();

}
