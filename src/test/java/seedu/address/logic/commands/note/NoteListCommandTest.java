package seedu.address.logic.commands.note;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.note.TypicalNotes.getTypicalNotesRecord;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class NoteListCommandTest {

    private Model model = new ModelManager();

    public NoteListCommandTest() {
        model.setNotesRecord(getTypicalNotesRecord());
    }

    @Test
    public void execute_validList_showsEverything() {
        Model expectedModel = new ModelManager();
        expectedModel.setNotesRecord(getTypicalNotesRecord());

        assertCommandSuccess(
                new NoteListCommand(),
                model,
                new CommandResult(NoteListCommand.MESSAGE_SUCCESS + model.getNotesRecord()),
                expectedModel);
    }
}
