package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.testutil.modelutil.TypicalModel;

class FindPersonCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindPersonCommand(null));
    }

    @Test
    void execute_success() throws CommandException {
        CommandResult actualCommandResult =
                new FindPersonCommand(ALICE.getName()).execute(model);
        assertNotNull(actualCommandResult);
    }

    @Test
    void execute_failure() throws CommandException {
        CommandResult actualCommandResult =
                new FindPersonCommand(ZACK.getName()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(FindPersonCommand.MESSAGE_FAILURE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }
}
