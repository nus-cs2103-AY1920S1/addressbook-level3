package seedu.system.model;

import java.nio.file.Path;

import seedu.system.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getPersonDataFilePath();

    Path getCompetitionDataFilePath();

    Path getParticipationDataFilePath();

}
