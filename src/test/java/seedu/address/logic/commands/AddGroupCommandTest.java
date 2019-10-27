package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUPNAME0;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;



class AddGroupCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddGroupCommand(null));
    }

    @Test
    void execute_success() throws CommandException, GroupNotFoundException {

        CommandResult actualCommandResult = new AddGroupCommand(GROUP0).execute(model);
        Group group = model.findGroup(GROUPNAME0);
        assertNotNull(group);
        CommandResult expectedCommandResult = new CommandResult(String.format(AddGroupCommand.MESSAGE_SUCCESS,
                group.getGroupName().toString()));

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void execute_duplicateGroup() throws CommandException {

        CommandResult actualCommandResult = new AddGroupCommand(GROUP1).execute(model);
        CommandResult expectedCommandResult = new CommandResult(String.format(AddGroupCommand.MESSAGE_FAILURE,
                AddGroupCommand.MESSAGE_DUPLICATE_GROUP));
        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }


}
