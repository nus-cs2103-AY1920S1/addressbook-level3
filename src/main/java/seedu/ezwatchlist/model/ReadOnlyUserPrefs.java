package seedu.ezwatchlist.model;

import java.nio.file.Path;

import seedu.ezwatchlist.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getWatchListFilePath();

}
