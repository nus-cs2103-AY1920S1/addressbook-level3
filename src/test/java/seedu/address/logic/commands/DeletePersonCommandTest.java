package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.testutil.modelutil.TypicalModel;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

class DeletePersonCommandTest {

    ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeletePersonCommand(null));
    }

    @Test
    void execute_success() throws CommandException {

        CommandResult actualCommandResult =
                new DeletePersonCommand(ALICE.getName()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(DeletePersonCommand.MESSAGE_SUCCESS);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_failure() throws CommandException {

        CommandResult actualCommandResult =
                new DeletePersonCommand(ZACK.getName()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(DeletePersonCommand.MESSAGE_FAILURE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }
}