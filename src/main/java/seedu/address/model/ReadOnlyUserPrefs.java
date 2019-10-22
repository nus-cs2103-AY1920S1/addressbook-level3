package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.aesthetics.Colour;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs extends ReadOnlyData {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    Colour getFontColour();

}
