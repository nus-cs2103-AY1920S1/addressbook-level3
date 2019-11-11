package seedu.flashcard.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.flashcard.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.IntegerProperty;

import javafx.collections.ObservableList;
import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.Quiz;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.ReadOnlyUserPrefs;
import seedu.flashcard.model.Statistics;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.tag.Tag;
import seedu.flashcard.testutil.FlashcardBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_flashcardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFlashcardAdded modelStub = new ModelStubAcceptingFlashcardAdded();
        Flashcard validFlashcard = new FlashcardBuilder().buildShortAnswerFlashcard();

        CommandResult commandResult = new AddCommand(validFlashcard).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validFlashcard), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFlashcard), modelStub.flashcardsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateFlashcard_throwsCommandException() {
        Flashcard validFlashcard = new FlashcardBuilder().buildMcqFlashcard();
        AddCommand addCommand = new AddCommand(validFlashcard);
        ModelStub modelStub = new ModelStubWithFlashcard(validFlashcard);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_FLASHCARD, () ->
                addCommand.execute(modelStub, commandHistory));
    }

    @Test
    public void equals() {
        Flashcard card1 = new FlashcardBuilder().withQuestion("Card1?").buildMcqFlashcard();
        Flashcard card2 = new FlashcardBuilder().withQuestion("Card2?").buildMcqFlashcard();
        AddCommand addCard1Command = new AddCommand(card1);
        AddCommand addCard2Command = new AddCommand(card2);

        // same object -> returns true
        assertTrue(addCard1Command.equals(addCard1Command));

        // same values -> returns true
        AddCommand addCard1CommandCopy = new AddCommand(card1);
        assertTrue(addCard1Command.equals(addCard1CommandCopy));

        // different types -> returns false
        assertFalse(addCard1Command.equals(1));

        // null -> returns false
        assertFalse(addCard1Command == null);

        // different person -> returns false
        assertFalse(addCard1Command.equals(addCard2Command));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public Predicate<Flashcard> getHasTagPredicate(Set<Tag> tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<Tag> getAllSystemTags() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFlashcardListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcardListFilePath(Path flashcardListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashcard(Flashcard card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcardList(ReadOnlyFlashcardList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFlashcardList getFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(Flashcard card) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashcard(Flashcard target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getFilteredFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean systemHasTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void systemRemoveTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateLastViewedFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Flashcard getLastViewedFlashcard() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String generateStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Statistics getStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Quiz getQuiz() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQuiz(List<Flashcard> quizableFlashcards) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setQuizDuration(Integer duration) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public IntegerProperty getDurationProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public IntegerProperty getTotalCardsProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public IntegerProperty getRemainingCardsProperty() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithFlashcard extends ModelStub {
        private final Flashcard flashcard;

        ModelStubWithFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            this.flashcard = flashcard;
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return this.flashcard.isSameFlashcard(flashcard);
        }
    }

    /**
     * A Model stub that always accept the flashcard being added.
     */
    private class ModelStubAcceptingFlashcardAdded extends ModelStub {
        final ArrayList<Flashcard> flashcardsAdded = new ArrayList<>();

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            return flashcardsAdded.stream().anyMatch(flashcard::isSameFlashcard);
        }

        @Override
        public void commitFlashcardList() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            flashcardsAdded.add(flashcard);
        }

        @Override
        public ReadOnlyFlashcardList getFlashcardList() {
            return new FlashcardList();
        }
    }

}
