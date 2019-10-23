package seedu.exercise.model;

import java.nio.file.Path;

import seedu.exercise.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getExerciseBookFilePath();

    Path getPropertyBookFilePath();

    Path getRegimeBookFilePath();

    Path getScheduleBookFilePath();

    Path getAllExerciseBookFilePath();
}
