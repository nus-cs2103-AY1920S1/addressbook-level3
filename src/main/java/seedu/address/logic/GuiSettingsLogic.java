package seedu.address.logic;

import seedu.address.commons.core.GuiSettings;

/**
 * API to read and set Gui settings
 */

public interface GuiSettingsLogic {

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
