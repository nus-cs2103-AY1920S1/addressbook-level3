package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.Test;

import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;

public class MemeClearCommandTest {

    @Test
    public void execute_emptyMemeBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyMemeBook_success() {
        Model model = new ModelManager(getTypicalMemeBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalMemeBook(), new UserPrefs());
        expectedModel.setMemeBook(new MemeBook());

        assertCommandSuccess(new MemeClearCommand(), model, MemeClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
