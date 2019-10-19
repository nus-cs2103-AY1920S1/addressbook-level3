package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;

class EditGroupCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditGroupCommand(null, null));
    }

    @Test
    public void constructor_nullGroupName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditGroupCommand(null, GROUP1));
    }

    @Test
    public void constructor_nullGroupDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditGroupCommand(GROUPNAME1, null));
    }

    @Test
    void execute_success() throws CommandException, GroupNotFoundException {
        Group group = model.findGroup(GROUPNAME1);

        CommandResult actualCommandResult =
                new EditGroupCommand(GROUPNAME1, GROUP0).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditGroupCommand.MESSAGE_SUCCESS, GROUPNAME1.toString().trim()));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_descriptorNotEdited() throws CommandException {
        CommandResult actualCommandResult =
                new EditGroupCommand(GROUPNAME1, new GroupDescriptor()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditGroupCommand.MESSAGE_FAILURE,
                        EditGroupCommand.MESSAGE_NOT_EDITED));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_groupDoesNotExist() throws CommandException {
        CommandResult actualCommandResult =
                new EditGroupCommand(GROUPNAME0, GROUP1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditGroupCommand.MESSAGE_FAILURE,
                        EditGroupCommand.MESSAGE_GROUP_NOT_FOUND));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }
}
