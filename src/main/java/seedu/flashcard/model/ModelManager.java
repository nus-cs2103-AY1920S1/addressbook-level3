package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.CollectionUtil.requireAllNonNull;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.model.flashcard.Flashcard;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

/**
 * Represents the in memory model of the flashcard list data
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final FlashcardList flashcardList;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;

    public ModelManager() {
        this(new FlashcardList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyFlashcardList flashcardList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(flashcardList, userPrefs);
        logger.fine("Initializing with flashcard list: " + flashcardList + " and user prefs " + userPrefs);
        this.flashcardList = new FlashcardList(flashcardList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<Flashcard>(this.flashcardList.getAllFlashcards());
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return null;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return null;
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {

    }

    @Override
    public Path getFlashcardListFilePath() {
        return null;
    }

    @Override
    public void setFlashcardListFilePath(Path flashcardListFilePath) {

    }

    @Override
    public void setFlashcardList(ReadOnlyFlashcardList flashcardList) {

    }

    @Override
    public ReadOnlyFlashcardList getFlashcardList() {
        return null;
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        return false;
    }

    @Override
    public void deleteFlashcard(Flashcard flashcard) {

    }

    @Override
    public void addFlashcard(Flashcard flashcard) {

    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {

    }

    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return null;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {

    }
}
