package seedu.algobase.model;

import java.nio.file.Path;

import seedu.algobase.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAlgoBaseFilePath();

}
