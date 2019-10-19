package seedu.main.logic;

import seedu.address.commons.core.GuiSettings;

/**
 * API of the AddressBookLogic component
 */

public interface MainLogic {

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
