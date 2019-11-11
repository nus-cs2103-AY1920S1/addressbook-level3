package seedu.weme.model;

import java.nio.file.Path;

import seedu.weme.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getDataFolderPath();

    Path getDataFilePath();

    Path getMemeImagePath();

    Path getExportPath();

    Path getTemplateImagePath();

}
