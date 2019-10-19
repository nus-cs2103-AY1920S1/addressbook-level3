package seedu.main.model;

import seedu.address.commons.core.GuiSettings;

/**
 * Main Model to get and set {@Code UserPrefs} and {@Code GuiSettings}.
 */
public interface MainModel {

    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    ReadOnlyUserPrefs getUserPrefs();

    GuiSettings getGuiSettings();

    void setGuiSettings(GuiSettings guiSettings);

}
