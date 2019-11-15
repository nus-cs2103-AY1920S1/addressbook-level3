package seedu.guilttrip.model;

import java.nio.file.Path;

import seedu.guilttrip.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getGuiltTripFilePath();

}
