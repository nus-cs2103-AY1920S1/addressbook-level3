package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.testutil.modelutil.TypicalModel;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME0;

class AddGroupCommandTest {

    ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGroupCommand(null));
    }

    @Test
    void execute_success() throws CommandException {

        CommandResult actualCommandResult = new AddGroupCommand(GROUP0).execute(model);
        Group group = model.findGroup(GROUPNAME0);
        assertNotNull(group);
        CommandResult expectedCommandResult = new CommandResult(AddGroupCommand.MESSAGE_SUCCESS + group.details());

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void execute_duplicateGroup() throws CommandException {

        CommandResult actualCommandResult = new AddGroupCommand(GROUP1).execute(model);
        CommandResult expectedCommandResult = new CommandResult(AddGroupCommand.MESSAGE_FAILURE);
        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }


}