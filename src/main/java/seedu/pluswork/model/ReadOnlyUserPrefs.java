package seedu.pluswork.model;

import seedu.pluswork.commons.core.GuiSettings;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getProjectDashboardFilePath();

    Path getUserSettingsFilePath();

}
