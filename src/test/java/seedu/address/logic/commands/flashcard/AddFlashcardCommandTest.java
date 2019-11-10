package seedu.address.logic.commands.flashcard;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.FunctionMode;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.StudyBuddyCounter;
import seedu.address.model.StudyBuddyItem;
import seedu.address.model.StudyBuddyPro;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.FlashcardBuilder;

public class AddFlashcardCommandTest {

    @Test
    public void constructor_nullFlashcard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFlashcardCommand(null));
    }

    @Test
    public void execute_flashcardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingFlashcardAdded modelStub = new ModelStubAcceptingFlashcardAdded();
        Flashcard validFlashcard = new FlashcardBuilder().build();

        CommandResult commandResult = new AddFlashcardCommand(validFlashcard).execute(modelStub);

        assertEquals(String.format(AddFlashcardCommand.MESSAGE_SUCCESS, validFlashcard),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validFlashcard), modelStub.flashcardsAdded);
    }

    @Test
    public void execute_duplicateFlashcard_throwsCommandException() {
        Flashcard validFlashcard = new FlashcardBuilder().build();
        AddFlashcardCommand addCommand = new AddFlashcardCommand(validFlashcard);
        ModelStub modelStub = new ModelStubWithFlashcard(validFlashcard);

        assertThrows(CommandException.class, AddFlashcardCommand.MESSAGE_DUPLICATE_FLASHCARD, () -> addCommand
                .execute(modelStub));
    }

    @Test
    public void equals() {
        Flashcard maths = new FlashcardBuilder().withTitle("Math Question 1").build();
        Flashcard cs = new FlashcardBuilder().withTitle("CS Question 1").build();
        AddFlashcardCommand addMathsCommand = new AddFlashcardCommand(maths);
        AddFlashcardCommand addCsCommand = new AddFlashcardCommand(cs);

        // same object -> returns true
        assertTrue(addMathsCommand.equals(addMathsCommand));

        // same values -> returns true
        AddFlashcardCommand addMathsCommandCopy = new AddFlashcardCommand(maths);
        assertTrue(addMathsCommand.equals(addMathsCommandCopy));

        // different types -> returns false
        assertFalse(addMathsCommand.equals(1));

        // null -> returns false
        assertFalse(addMathsCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(addMathsCommand.equals(addCsCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public Path getStudyBuddyProFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudyBuddyProFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void addNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudyBuddyPro(ReadOnlyStudyBuddyPro newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyStudyBuddyPro getStudyBuddyPro() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Tag> getFilteredTagList() {
            return null;
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //=============Tagging stuff===============================================
        @Override
        public ArrayList<String> collectTaggedItems(Predicate<StudyBuddyItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<String> collectTaggedCheatSheets(Predicate<CheatSheet> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<String> collectTaggedFlashcards(Predicate<Flashcard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<Flashcard> getTaggedFlashcards(Predicate<Flashcard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ArrayList<String> collectTaggedNotes(Predicate<Note> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //=============Person stuff===============================================
        @Override
        public ArrayList<StudyBuddyCounter> getStatistics(ArrayList<Tag> tagList) {
            throw new AssertionError("This method should not be called.");
        }

        //=============Flashcard stuff===============================================

        @Override
        public void addFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getFilteredFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String formatOutputListString(FunctionMode mode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T> String formatList(FilteredList<T> object) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
            throw new AssertionError("This method should not be called.");
        }

        //=============Note stuff===============================================

        @Override
        public boolean hasNote(Note note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteNote(Note target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNote(Note target, Note editedNote) {
            throw new AssertionError("This method should not be called.");
        }

        public ObservableList<Note> getFilteredNoteList() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateFilteredNoteList(Predicate<Note> predicate) {

        }

        //=============CheatSheet stuff===============================================
        @Override
        public boolean hasCheatSheet(CheatSheet cheatSheet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet) {
            throw new AssertionError("This method should not be called.");
        }

        public void addCheatSheet(CheatSheet cheatSheet) {
            throw new AssertionError("This method should not be called.");
        }

        public ObservableList<CheatSheet> getFilteredCheatSheetList() {
            throw new AssertionError("This method should not be called.");
        }

        public void updateFilteredCheatSheetList(Predicate<CheatSheet> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        public void deleteCheatSheet(CheatSheet cheatSheet) {
            throw new AssertionError("This method should not be called.");
        }

        public ArrayList<String> getListOfTags() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single flashcard.
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
        public void addFlashcard(Flashcard flashcard) {
            requireNonNull(flashcard);
            flashcardsAdded.add(flashcard);
        }

        @Override
        public ReadOnlyStudyBuddyPro getStudyBuddyPro() {
            return new StudyBuddyPro();
        }
    }
}
