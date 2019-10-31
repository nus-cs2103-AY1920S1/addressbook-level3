package seedu.address.logic.commands.note;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.note.TypicalNotes.getTypicalNotesRecord;

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
