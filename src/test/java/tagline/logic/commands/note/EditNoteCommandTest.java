// @@author shiweing
package tagline.logic.commands.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.logic.commands.NoteCommandTestUtil.NON_EXISTING_NOTE_ID;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_CONTENT_PROTECTOR;
import static tagline.logic.commands.NoteCommandTestUtil.VALID_TITLE_INCIDENT;
import static tagline.logic.commands.NoteCommandTestUtil.assertCommandFailure;
import static tagline.logic.commands.note.EditNoteCommand.EditNoteDescriptor;
import static tagline.logic.commands.note.EditNoteCommand.MESSAGE_DUPLICATE_NOTE;
import static tagline.testutil.TypicalIndexes.INDEX_FIRST;
import static tagline.testutil.TypicalIndexes.INDEX_SECOND;
import static tagline.testutil.TypicalNotes.getTypicalNoteBook;

import org.junit.jupiter.api.Test;

import tagline.commons.core.Messages;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.ModelManager;
import tagline.model.UserPrefs;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.testutil.EditNoteDescriptorBuilder;
import tagline.testutil.NoteBuilder;

/**
 * Contains integration tests (interaction with the Model) and
 * unit tests for EditNoteCommand.
 */
class EditNoteCommandTest {

    private static final CommandResult.ViewType EDIT_NOTE_COMMAND_VIEW_TYPE = CommandResult.ViewType.NOTE;
    private Model model = new ModelManager(getTypicalNoteBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Note originalNote = model.getNoteBook().getNoteList().get(INDEX_FIRST.getZeroBased());
        Note editedNote = new NoteBuilder().withNoteId(originalNote.getNoteId().toLong())
                .withTimeLastEdited(originalNote.getTimeLastEdited()).build();

        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(editedNote).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(originalNote.getNoteId(), descriptor);

        // check result manually as TimeLastEdited is dynamically obtained,
        // resulting in failure for assertEquals
        try {
            CommandResult result = editNoteCommand.execute(model);
            Note actualEditedNote = model.getNoteBook().getNoteList().get(INDEX_FIRST.getZeroBased());

            assertEquals(result.getViewType(), EDIT_NOTE_COMMAND_VIEW_TYPE);
            assertTrue(actualEditedNote.isUniqueNote(editedNote));
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }

    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Note originalNote = model.getNoteBook().getNoteList().get(INDEX_FIRST.getZeroBased());
        Note editedNote = new NoteBuilder(originalNote).withTitle(VALID_TITLE_INCIDENT).build();

        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder(editedNote).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(originalNote.getNoteId(), descriptor);

        // check result manually as TimeLastEdited is dynamically obtained,
        // resulting in failure for assertEquals
        try {
            CommandResult result = editNoteCommand.execute(model);
            Note actualEditedNote = model.getNoteBook().getNoteList().get(INDEX_FIRST.getZeroBased());

            assertEquals(result.getViewType(), EDIT_NOTE_COMMAND_VIEW_TYPE);
            assertTrue(actualEditedNote.isUniqueNote(editedNote));
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_noFieldSpecified_failure() {
        Note originalNote = model.getNoteBook().getNoteList().get(INDEX_FIRST.getZeroBased());

        EditNoteDescriptor descriptor = new EditNoteDescriptor();
        EditNoteCommand editNoteCommand = new EditNoteCommand(originalNote.getNoteId(), descriptor);

        assertCommandFailure(editNoteCommand, model, MESSAGE_DUPLICATE_NOTE);
    }

    @Test
    public void execute_invalidNoteId_failure() {
        NoteId nonExistingId = NON_EXISTING_NOTE_ID;

        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder().withContent(VALID_CONTENT_INCIDENT).build();
        EditNoteCommand editNoteCommand = new EditNoteCommand(nonExistingId, descriptor);

        assertCommandFailure(editNoteCommand, model, Messages.MESSAGE_INVALID_NOTE_INDEX);
    }

    @Test
    public void equals() {
        EditNoteDescriptor descriptor = new EditNoteDescriptorBuilder().withContent(VALID_CONTENT_INCIDENT).build();
        final EditNoteCommand standardCommand = new EditNoteCommand(new NoteId(INDEX_FIRST.getOneBased()), descriptor);

        // same values -> return true
        EditNoteDescriptor copyDescriptor = new EditNoteDescriptorBuilder(descriptor).build();
        EditNoteCommand commandWithSameValues = new EditNoteCommand(
                new NoteId(INDEX_FIRST.getOneBased()), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different type -> returns false
        assertFalse(standardCommand.equals(new ListNoteCommand(null)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditNoteCommand(new NoteId(INDEX_SECOND.getOneBased()), descriptor)));

        // different descriptor -> returns false
        EditNoteDescriptor differentDescriptor = new EditNoteDescriptorBuilder()
                .withContent(VALID_CONTENT_PROTECTOR).build();
        assertFalse(standardCommand.equals(
                new EditNoteCommand(new NoteId(INDEX_FIRST.getOneBased()), differentDescriptor)));
    }
}
