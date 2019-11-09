package seedu.address.logic.quiz.commands;

import static seedu.address.logic.quiz.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.quiz.AddressQuizBook;
import seedu.address.model.quiz.Model;
import seedu.address.model.quiz.ModelQuizManager;
import seedu.address.model.quiz.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyModulo_success() {
        Model model = new ModelQuizManager();
        Model expectedModel = new ModelQuizManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyModulo_success() {
        Model model = new ModelQuizManager(new AddressQuizBook(), new UserPrefs());
        Model expectedModel = new ModelQuizManager(new AddressQuizBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressQuizBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
