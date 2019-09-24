package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserAliases;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    UserAliases getUserAliases();

}
