package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.EmailCommand.MESSAGE_NOT_IMPLEMENTED_YET;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class EmailCommandTest {
    private Model model = new ModelManager();

    @Test
    public void execute_email_failure() {
        assertCommandFailure(new EmailCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
