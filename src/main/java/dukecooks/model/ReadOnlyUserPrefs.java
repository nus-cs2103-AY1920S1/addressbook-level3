package dukecooks.model;

import java.nio.file.Path;

import dukecooks.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getExercisesFilePath();

    Path getRecipesFilePath();

    Path getUserProfileFilePath();

    Path getHealthRecordsFilePath();

    Path getDiaryFilePath();

    Path getDashboardFilePath();

}
