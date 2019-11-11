package seedu.flashcard.model;

import static java.util.Objects.requireNonNull;
import static seedu.flashcard.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * Represents the in memory model of the flashcard list data
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;
    private Flashcard viewedFlashcard;
    private Statistics desiredStats;
    private Quiz quiz;
    private VersionedFlashcardList versionedFlashcardList;
    private final SimpleObjectProperty<Flashcard> selectedFlashcard = new SimpleObjectProperty<>();


    /**
     * Default initializer
     */
    public ModelManager() {
        this(new FlashcardList(), new UserPrefs());
    }

    /**
     * Initializes a MOdelManager with the given flashcard list and userPrefs
     */
    public ModelManager(ReadOnlyFlashcardList flashcardList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(flashcardList, userPrefs);
        this.versionedFlashcardList = new VersionedFlashcardList(flashcardList);
        logger.fine("Initializing with flashcard list: " + flashcardList + " and user prefs " + userPrefs);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<Flashcard>(versionedFlashcardList.getFlashcardList());
        this.viewedFlashcard = null;
        this.desiredStats = new Statistics();
        this.quiz = new Quiz();
        filteredFlashcards.addListener(this::ensureSelectedFlashcardIsValid);

    }

    @Override
    public Predicate<Flashcard> getHasTagPredicate(Set<Tag> tag) {
        requireNonNull(tag);
        return flashcard -> flashcard.hasAnyTag(tag);
    }

    @Override
    public Set<Tag> getAllSystemTags() {
        return versionedFlashcardList.getAllFlashcardTags();
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFlashcardListFilePath() {
        return userPrefs.getFlashcardListFilePath();
    }

    @Override
    public void setFlashcardListFilePath(Path flashcardListFilePath) {
        requireNonNull(flashcardListFilePath);
        userPrefs.setFlashcardListFilePath(flashcardListFilePath);
    }

    @Override
    public void setFlashcardList(ReadOnlyFlashcardList flashcardList) {
        versionedFlashcardList.resetData(flashcardList);
    }

    @Override
    public ReadOnlyFlashcardList getFlashcardList() {
        return versionedFlashcardList;
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return versionedFlashcardList.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Flashcard flashcard) {
        versionedFlashcardList.removeFlashcard(flashcard);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        versionedFlashcardList.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);
        versionedFlashcardList.setFlashcard(target, editedFlashcard);
    }

    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public boolean systemHasTag(Tag tag) {
        return versionedFlashcardList.flashcardsHasTag(tag);
    }

    @Override
    public void systemRemoveTag(Tag tag) {
        versionedFlashcardList.flashcardsRemoveTag(tag);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    @Override
    public void updateLastViewedFlashcard(Flashcard flashcard) {
        this.viewedFlashcard = flashcard;
    }

    @Override
    public Flashcard getLastViewedFlashcard() {
        return this.viewedFlashcard;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ModelManager)) {
            return false;
        }
        ModelManager obj = (ModelManager) other;
        return versionedFlashcardList.equals(obj.versionedFlashcardList)
                && userPrefs.equals(obj.userPrefs)
                && filteredFlashcards.equals(obj.filteredFlashcards)
                && quiz.equals(obj.quiz);
    }

    @Override
    public String generateStatistics() {
        desiredStats.calculate(filteredFlashcards);
        return desiredStats.getResults();
    }

    @Override
    public Statistics getStatistics() {
        return desiredStats;
    }

    @Override
    public Quiz getQuiz() {
        return quiz;
    }

    @Override
    public void setQuiz(List<Flashcard> quizableFlashcards) {
        quiz.setQuizList(quizableFlashcards);
    }

    @Override
    public void setQuizDuration(Integer duration) {
        quiz.setDuration(duration);
    }

    @Override
    public boolean canUndoFlashcardList() {
        return versionedFlashcardList.canUndo();
    }

    @Override
    public boolean canRedoFlashcardList() {
        return versionedFlashcardList.canRedo();
    }

    @Override
    public void undoFlashcardList() {
        versionedFlashcardList.undo();
    }

    @Override
    public void redoFlashcardList() {
        versionedFlashcardList.redo();
    }

    @Override
    public void commitFlashcardList() {
        versionedFlashcardList.commit();
    }

    @Override
    public IntegerProperty getDurationProperty() {
        return quiz.getDurationProperty();
    }

    @Override
    public IntegerProperty getTotalCardsProperty() {
        return quiz.totalCardsProperty();
    }

    @Override
    public IntegerProperty getRemainingCardsProperty() {
        return quiz.remainingCardsProperty();
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedFlashcardIsValid(ListChangeListener.Change<? extends Flashcard> change) {
        while (change.next()) {
            if (selectedFlashcard.getValue() == null) {
                // null is always a valid selected flashcard, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedFlashcardReplaced = change.wasReplaced() && change.getAddedSize()
                    == change.getRemovedSize()
                    && change.getRemoved().contains(selectedFlashcard.getValue());
            if (wasSelectedFlashcardReplaced) {
                // Update selectedFlashcard to its new value.
                int index = change.getRemoved().indexOf(selectedFlashcard.getValue());
                selectedFlashcard.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedFlashcardRemoved = change.getRemoved().stream()
                    .anyMatch(removedFlashcard -> selectedFlashcard.getValue().isSameFlashcard(removedFlashcard));
            if (wasSelectedFlashcardRemoved) {
                // Select the flashcard that came before it in the list,
                // or clear the selection if there is no such flashcard.
                selectedFlashcard.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }
}
