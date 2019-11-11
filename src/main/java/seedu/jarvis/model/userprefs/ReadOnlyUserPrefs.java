package seedu.jarvis.model.userprefs;

import java.nio.file.Path;

import seedu.jarvis.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHistoryManagerFilePath();

    Path getCcaTrackerFilePath();

    Path getCoursePlannerFilePath();

    Path getPlannerFilePath();

    Path getFinanceTrackerPath();

}
