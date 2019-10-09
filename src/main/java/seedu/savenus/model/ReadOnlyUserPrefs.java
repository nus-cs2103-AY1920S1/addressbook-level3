package seedu.savenus.model;

import java.nio.file.Path;

import seedu.savenus.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getMenuFilePath();

}
