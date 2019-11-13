package calofit.model;

import java.nio.file.Path;

import calofit.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getDishDatabaseFilePath();

    Path getMealLogFilePath();

    Path getCalorieBudgetFilePath();
}
