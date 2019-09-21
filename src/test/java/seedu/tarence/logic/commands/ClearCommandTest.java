package seedu.tarence.logic.commands;

import static seedu.tarence.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tarence.testutil.TypicalPersons.getTypicalStudentBook;

import org.junit.jupiter.api.Test;

import seedu.tarence.model.Model;
import seedu.tarence.model.ModelManager;
import seedu.tarence.model.StudentBook;
import seedu.tarence.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyStudentBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyStudentBook_success() {
        Model model = new ModelManager(getTypicalStudentBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalStudentBook(), new UserPrefs());
        expectedModel.setStudentBook(new StudentBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
