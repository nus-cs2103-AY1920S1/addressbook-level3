package seedu.mark.model;

import java.nio.file.Path;

import seedu.mark.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getMarkFilePath();

}
