package seedu.address.logic.quiz.commands;

import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalQuestion.getTypicalAddressQuizBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.ModelQuizManager;
import seedu.address.model.quiz.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelQuizManager();
        Model expectedModel = new ModelQuizManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelQuizManager(getTypicalAddressQuizBook(), new UserPrefs());
        Model expectedModel = new ModelQuizManager(getTypicalAddressQuizBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressQuizBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
