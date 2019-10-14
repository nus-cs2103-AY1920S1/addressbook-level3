package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.commons.core.GuiSettings;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represent the user's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    // TODO: make the following path reasonable
    private Path flashcardListFilePath = Paths.get("data", "flashcardlist.json");

    /**
     * Creates a {@code UserPrefs} with default values
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with prefs in {@code userPrefs}
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Reset the existing data of this {@code UserPrefs} with {@code newUserPrefs}
     * @param newUserPrefs
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setFlashcardListFilePath(newUserPrefs.getFlashcardListFilePath());
    }

    @Override
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    @Override
    public Path getFlashcardListFilePath() {
        return flashcardListFilePath;
    }

    public void setFlashcardListFilePath(Path flashcardListFilePath) {
        this.flashcardListFilePath = flashcardListFilePath;
    }
}
