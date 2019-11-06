package seedu.revision.model;

import java.nio.file.Path;

import seedu.revision.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getRevisionToolFilePath();

}
