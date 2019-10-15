package seedu.flashcard.model;

import java.nio.file.Path;

import seedu.flashcard.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getFlashcardListFilePath();
}
