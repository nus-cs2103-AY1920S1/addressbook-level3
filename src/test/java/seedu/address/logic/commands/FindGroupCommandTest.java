package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.testutil.modelutil.TypicalModel;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;

class FindGroupCommandTest {

    ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_nullGroupName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FindGroupCommand(null));
    }

    @Test
    void execute_success() throws CommandException {
        CommandResult actualCommandResult =
                new FindGroupCommand(GROUPNAME1).execute(model);
        assertNotNull(actualCommandResult);
    }

    @Test
    void execute_failure() throws CommandException {
        CommandResult actualCommandResult =
                new FindGroupCommand(GROUPNAME0).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(FindGroupCommand.MESSAGE_FAILURE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

}