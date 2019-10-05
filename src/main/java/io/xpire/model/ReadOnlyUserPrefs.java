package io.xpire.model;

import java.nio.file.Path;

import io.xpire.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getXpireFilePath();
}
