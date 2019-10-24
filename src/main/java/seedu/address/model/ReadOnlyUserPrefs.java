package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.aesthetics.Background;
import seedu.address.model.aesthetics.Colour;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs extends ReadOnlyData {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

    /**
     * Returns the font colour to be set for this app.
     */
    Colour getFontColour();

    /**
     * Sets the font colour of this application and saves it to the user preferences file.
     */
    void setFontColour(Colour fontColour);

    /**
     * Returns the background to be set for this app.
     */
    Background getBackground();

    /**
     * Sets the background of this application and saves it to the user preferences file.
     */
    void setBackground(Background background);

}
