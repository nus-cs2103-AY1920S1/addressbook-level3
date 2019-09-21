package seedu.tarence.model;

import java.nio.file.Path;

import seedu.tarence.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getStudentBookFilePath();

}
