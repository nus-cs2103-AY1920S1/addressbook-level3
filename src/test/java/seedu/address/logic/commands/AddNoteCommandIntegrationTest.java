package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.NotesCommandTestUtil.VALID_STRING_COMMAND_ARG;
import static seedu.address.testutil.TypicalNotes.getTypicalNoteBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.CardBook;
import seedu.address.model.FileBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PasswordBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.Note;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddNoteCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new FileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
    }

    @Test
    public void execute_newNote_success() {
        Note validNote = new NoteBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new FileBook(),
                new CardBook(), getTypicalNoteBook(), new PasswordBook(), new UserPrefs());
        expectedModel.addNote(validNote);

        assertCommandSuccess(new AddNotesCommand(validNote,VALID_STRING_COMMAND_ARG), model,
                String.format(AddNotesCommand.MESSAGE_SUCCESS, validNote), expectedModel);
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note noteInList = model.getNoteBook().getNoteList().get(0);
        assertCommandFailure(new AddNotesCommand(noteInList,VALID_STRING_COMMAND_ARG), model,
                AddNotesCommand.MESSAGE_DUPLICATE_NOTE);
    }

}
