package seedu.travezy.model;

import seedu.travezy.commons.core.GuiSettings;

/**
 * Main Model to get and set {@Code UserPrefs} and {@Code GuiSettings}.
 */
public interface UserPrefsModel {

    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    ReadOnlyUserPrefs getUserPrefs();

    GuiSettings getGuiSettings();

    void setGuiSettings(GuiSettings guiSettings);

}
