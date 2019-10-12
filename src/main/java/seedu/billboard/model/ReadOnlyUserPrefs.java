package seedu.billboard.model;

import java.nio.file.Path;

import seedu.billboard.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getBillboardFilePath();

    Path getArchiveFilePath();

}
