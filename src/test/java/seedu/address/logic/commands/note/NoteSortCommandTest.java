package seedu.address.logic.commands.note;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.note.TypicalNotes.getTypicalNotesRecord;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.note.Note;

public class NoteSortCommandTest {

    private Model model = new ModelManager();

    public NoteSortCommandTest() {
        model.setNotesRecord(getTypicalNotesRecord());
    }

    @Test
    public void execute_sortList_sortsCorrectly() {
        Model expectedModel = new ModelManager();
        expectedModel.setNotesRecord(getTypicalNotesRecord());
        expectedModel.sortNotesRecord(new Note.NoteComparator());

        assertCommandSuccess(new NoteSortCommand(), model, new CommandResult(NoteSortCommand.MESSAGE_SUCCESS),
                expectedModel);
    }
}
