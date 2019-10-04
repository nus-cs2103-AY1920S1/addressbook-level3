package mams.model;

import java.nio.file.Path;

import mams.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getMamsFilePath();

}
