package seedu.sugarmummy.logic.commands;

import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.bio.BioCommand.SHOWING_BIO_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.sugarmummy.logic.commands.bio.BioCommand;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ModelManager;

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
