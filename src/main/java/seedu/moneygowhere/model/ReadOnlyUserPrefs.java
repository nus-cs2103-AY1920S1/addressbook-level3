package seedu.moneygowhere.model;

import java.nio.file.Path;

import seedu.moneygowhere.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getSpendingBookFilePath();

}
