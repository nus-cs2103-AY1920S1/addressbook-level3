package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppData.getTypicalAppData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.note.Note;
import seedu.address.testutil.NoteBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddNoteCommand}.
 */
public class AddNoteCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAppData(), new UserPrefs());
    }

    @Test
    public void execute_newNote_success() {
        Note validNote = new NoteBuilder().build();

        Model expectedModel = new ModelManager(model.getAppData(), new UserPrefs());
        expectedModel.addNote(validNote);

        assertCommandSuccess(new AddNoteCommand(validNote), model,
                String.format(AddNoteCommand.MESSAGE_SUCCESS, validNote), expectedModel);
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        Note noteInList = model.getAppData().getNoteList().get(0);
        assertCommandFailure(new AddNoteCommand(noteInList), model, AddNoteCommand.MESSAGE_DUPLICATE_TITLE);
    }

}
