package seedu.deliverymans.model.addressbook;

import java.nio.file.Path;

import seedu.deliverymans.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
