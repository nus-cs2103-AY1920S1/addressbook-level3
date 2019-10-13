package seedu.flashcard.model;

import seedu.flashcard.commons.core.GuiSettings;

import java.nio.file.Path;

/**
 * Represent the user's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    @Override
    public GuiSettings getGuiSettings() {
        return null;
    }

    @Override
    public Path getFlashcardListFilePath() {
        return null;
    }
}
