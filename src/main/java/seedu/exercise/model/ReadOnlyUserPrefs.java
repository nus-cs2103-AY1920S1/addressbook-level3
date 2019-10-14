package seedu.exercise.model;

import java.nio.file.Path;

import seedu.exercise.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getExerciseBookFilePath();

    Path getPropertyManagerFilePath();

    Path getRegimeBookFilePath();

    Path getAllExerciseBookFilePath();
}
