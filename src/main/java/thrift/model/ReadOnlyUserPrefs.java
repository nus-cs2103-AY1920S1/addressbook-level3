package thrift.model;

import java.nio.file.Path;

import thrift.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getThriftFilePath();

}
