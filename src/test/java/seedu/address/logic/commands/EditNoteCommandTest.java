package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showNoteAtIndex;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditNoteCommand.EditNoteDescriptor;
import seedu.address.model.AppData;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.Note;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for EditNoteCommand.
 */
public class EditNoteCommandTest {
    private Model model = new ModelManager(getTypicalAppData(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Note editedNote = new NoteBuilder().build();
        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(editedNote).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());
        expectedModel.setNote(model.getFilteredNoteList().get(0), editedNote);

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastNote = Index.fromOneBased(model.getFilteredNoteList().size());
        Note lastNote = model.getFilteredNoteList().get(indexLastNote.getZeroBased());

        NoteBuilder noteInList = new NoteBuilder(lastNote);
        Note editedNote = noteInList.withTitle(VALID_TITLE_BOB).build();

        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder()
                .withTitle(VALID_TITLE_BOB).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(indexLastNote, descriptor);

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());
        expectedModel.setNote(lastNote, editedNote);

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST,
                new EditNoteCommand.EditNoteDescriptor());
        Note editedNote = model.getFilteredNoteList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showNoteAtIndex(model, INDEX_FIRST);

        Note noteInFilteredList = model.getFilteredNoteList().get(INDEX_FIRST.getZeroBased());
        Note editedNote = new NoteBuilder(noteInFilteredList).withTitle(VALID_TITLE_BOB).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST,
                new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).build());

        String expectedMessage = String.format(EditNoteCommand.MESSAGE_EDIT_NOTE_SUCCESS, editedNote);

        Model expectedModel = new ModelManager(new AppData(model.getAppData()), new UserPrefs());
        expectedModel.setNote(model.getFilteredNoteList().get(0), editedNote);

        assertCommandSuccess(editNoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateNoteUnfilteredList_failure() {
        Note firstNote = model.getFilteredNoteList().get(INDEX_FIRST.getZeroBased());
        EditNoteCommand.EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(firstNote).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editNoteCommand, model, EditNoteCommand.MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void execute_duplicateNoteFilteredList_failure() {
        showNoteAtIndex(model, INDEX_FIRST);

        // edit note in filtered list into a duplicate in address book
        Note noteInList = model.getAppData().getNoteList().get(INDEX_SECOND.getZeroBased());
        EditNoteCommand editNoteCommand = new EditNoteCommand(INDEX_FIRST,
                new EditNoteDescriptorBuilder(noteInList).build());

        assertCommandFailure(editNoteCommand, model, EditNoteCommand.MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void execute_invalidNoteIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredNoteList().size() + 1);
        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidNoteIndexFilteredList_failure() {
        showNoteAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAppData().getNoteList().size());

        EditNoteCommand editNoteCommand = new EditNoteCommand(outOfBoundIndex,
                new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).build());

        assertCommandFailure(editNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditNoteCommand standardCommand = new EditNoteCommand(INDEX_FIRST, DESC_AMY);

        // same values -> returns true
        EditNoteDescriptor copyDescriptor = new EditNoteCommand.EditNoteDescriptor(DESC_AMY);
        EditNoteCommand commandWithSameValues = new EditNoteCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearNoteCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditNoteCommand(INDEX_SECOND, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditNoteCommand(INDEX_FIRST, DESC_BOB)));
    }
}
