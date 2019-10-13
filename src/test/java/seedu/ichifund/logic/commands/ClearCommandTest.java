package seedu.ichifund.logic.commands;

import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ichifund.testutil.TypicalPersons.getTypicalFundBook;

import org.junit.jupiter.api.Test;

import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ModelManager;
import seedu.ichifund.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyFundBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFundBook_success() {
        Model model = new ModelManager(getTypicalFundBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFundBook(), new UserPrefs());
        expectedModel.setFundBook(new FundBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
