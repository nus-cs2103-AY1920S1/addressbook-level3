package seedu.module.model;

import java.nio.file.Path;

import seedu.module.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getModuleBookFilePath();

}
