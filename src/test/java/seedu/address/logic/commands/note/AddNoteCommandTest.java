package seedu.address.logic.commands.note;

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

import javafx.collections.FXCollections;
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
import seedu.address.testutil.NoteBuilder;

public class AddNoteCommandTest {

    private ObservableList<Note> list = FXCollections.observableArrayList();

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddNoteCommand(null));
    }

    @Test
    public void execute_noteWithNoNoteFragments_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Note validNote = new NoteBuilder().build();

        CommandResult commandResult = new AddNoteCommand(validNote).execute(modelStub);

        assertEquals(String.format(AddNoteCommand.MESSAGE_SUCCESS, validNote.toStringWithNoteFragments()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }

    @Test
    public void execute_noteWithMultipleNoteFragments_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Note validNote = new NoteBuilder().withContent(NoteBuilder.EXTRA_CONTENT).build();

        CommandResult commandResult = new AddNoteCommand(validNote).execute(modelStub);

        assertEquals(String.format(AddNoteCommand.MESSAGE_SUCCESS, validNote.toStringWithNoteFragments()),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }

    @Test
    public void constructor_noteTitleOfOnlyWhiteSpace_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withTitle("").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withTitle(" ").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withTitle("  ").build());
    }

    @Test
    public void constructor_noteContentOfOnlyWhiteSpace_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withContent("").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withContent(" ").build());
        assertThrows(IllegalArgumentException.class, () -> new NoteBuilder().withContent("  ").build());
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note validNote = new NoteBuilder().build();
        AddNoteCommand addNoteCommand = new AddNoteCommand(validNote);
        ModelStub modelStub = new ModelStubWithNote(validNote);

        assertThrows(CommandException.class, AddNoteCommand.MESSAGE_DUPLICATE_NOTE, (
            ) -> addNoteCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Note sucks = new NoteBuilder().withTitle("Pipelining sucks").build();
        Note rocks = new NoteBuilder().withTitle("Pipelining rocks").build();
        AddNoteCommand addSucksCommand = new AddNoteCommand(sucks);
        AddNoteCommand addRocksCommand = new AddNoteCommand(rocks);

        // same object -> returns true
        assertTrue(addSucksCommand.equals(addSucksCommand));

        // same values -> returns true
        AddNoteCommand addSucksCommandCopy = new AddNoteCommand(sucks);
        assertTrue(addSucksCommand.equals(addSucksCommandCopy));

        // different types -> returns false
        assertFalse(addSucksCommand.equals(1));

        // null -> returns false
        assertFalse(addSucksCommand.equals(null));

        // different note -> returns false
        assertFalse(addSucksCommand.equals(addRocksCommand));
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

        @Override
        public void updateFilteredNoteList(Predicate<Note> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Note> getFilteredNoteList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteFlashcard(Flashcard flashcard) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
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
        public ArrayList<StudyBuddyCounter> getStatistics(ArrayList<Tag> tagList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T> String formatList(FilteredList<T> object) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCheatSheet(CheatSheet cheatSheet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCheatSheet(CheatSheet cheatSheet) {
            return false;
        }

        @Override
        public void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CheatSheet> getFilteredCheatSheetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCheatSheetList(Predicate<CheatSheet> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCheatSheet(CheatSheet cheatSheet) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Flashcard> getFilteredFlashcardList() {
            throw new AssertionError("This method should not be called.");
        }

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

        public ArrayList<String> getListOfTags() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithNote extends ModelStub {
        private final Note note;

        ModelStubWithNote(Note note) {
            requireNonNull(note);
            this.note = note;
        }

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return this.note.isSameNote(note);
        }
    }

    /**
     * A Model stub that always accepts the note being added.
     */
    private class ModelStubAcceptingNoteAdded extends ModelStub {
        final ArrayList<Note> notesAdded = new ArrayList<>();

        @Override
        public boolean hasNote(Note note) {
            requireNonNull(note);
            return notesAdded.stream().anyMatch(note::isSameNote);
        }

        @Override
        public void addNote(Note note) {
            requireNonNull(note);
            notesAdded.add(note);
            list.add(note);
        }

        @Override
        public ReadOnlyStudyBuddyPro getStudyBuddyPro() {
            return new StudyBuddyPro();
        }

        @Override
        public ObservableList<Note> getFilteredNoteList() {
            return list;
        }
    }
}
