package seedu.eatme.model;

import java.nio.file.Path;

import seedu.eatme.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getEateryListFilePath();

    Path getFeedListFilePath();

}
