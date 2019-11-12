package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.NoteBook;

public class ClearNoteCommandTest {

    @Test
    public void execute_emptyNoteBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearNoteCommand(), model, ClearNoteCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyNoteBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.setNoteBook(new NoteBook());

        assertCommandSuccess(new ClearNoteCommand(), model, ClearNoteCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
