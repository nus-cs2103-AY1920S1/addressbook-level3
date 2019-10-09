package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    UserSettings getUserSettings();

    Path getCatalogFilePath();

    Path getLoanRecordsFilePath();

    Path getBorrowerRecordsFilePath();
}
