package seedu.scheduler.model;

import java.nio.file.Path;

import seedu.scheduler.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getIntervieweeListFilePath();

    Path getInterviewerListFilePath();

}
