package seedu.pluswork.model;

import java.nio.file.Path;

import seedu.pluswork.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getProjectDashboardFilePath();

    Path getUserSettingsFilePath();

}
