package seedu.address.logic.commands;

import static seedu.address.logic.commands.BioCommand.SHOWING_BIO_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

class BioCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_bio_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_BIO_MESSAGE, false, true,
                false, false);
        assertCommandSuccess(new BioCommand(), model, expectedCommandResult, expectedModel);
    }
}
