package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP2;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME0;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;

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
        Group group = model.findGroup(GROUP_NAME0);
        assertNotNull(group);
        CommandResult expectedCommandResult = new CommandResult(String.format(AddGroupCommand.MESSAGE_SUCCESS,
                group.getGroupName().toString()));

        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void execute_duplicateGroup() throws CommandException {

        CommandResult actualCommandResult = new AddGroupCommand(GROUP1).execute(model);
        CommandResult expectedCommandResult = new CommandResult(String.format(AddGroupCommand.MESSAGE_FAILURE,
                AddGroupCommand.MESSAGE_DUPLICATE_GROUP));
        assertEquals(expectedCommandResult, actualCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new AddGroupCommand(GROUP1).equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new AddGroupCommand(GROUP1)
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentDescriptor() {
        assertFalse(
                new AddGroupCommand(GROUP1).equals(new AddGroupCommand(GROUP2))
        );
    }


    @Test
    void equals_sameDescriptor() {
        assertTrue(
                new AddGroupCommand(GROUP1).equals(new AddGroupCommand(GROUP1))
        );
    }


}
