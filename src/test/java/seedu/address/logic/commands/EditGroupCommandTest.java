package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP2;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.exceptions.DuplicateGroupException;
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
    void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditGroupCommand(null, null));
    }

    @Test
    void constructor_nullGroupName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditGroupCommand(null, GROUP1));
    }

    @Test
    void constructor_nullGroupDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditGroupCommand(GROUP_NAME1, null));
    }

    @Test
    void execute_success() throws CommandException {

        CommandResult actualCommandResult =
                new EditGroupCommand(GROUP_NAME1, GROUP0).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditGroupCommand.MESSAGE_SUCCESS,
                        GROUP_NAME1.toString().trim()));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_duplicateGroup() throws CommandException {

        CommandResult actualCommandResult =
                new EditGroupCommand(GROUP_NAME1, GROUP2).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditGroupCommand.MESSAGE_FAILURE,
                        EditGroupCommand.MESSAGE_DUPLICATE_GROUP));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_descriptorNotEdited() throws CommandException {
        CommandResult actualCommandResult =
                new EditGroupCommand(GROUP_NAME1, new GroupDescriptor()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditGroupCommand.MESSAGE_FAILURE,
                        EditGroupCommand.MESSAGE_NOT_EDITED));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_groupDoesNotExist() throws CommandException {
        CommandResult actualCommandResult =
                new EditGroupCommand(GROUP_NAME0, GROUP1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditGroupCommand.MESSAGE_FAILURE,
                        EditGroupCommand.MESSAGE_GROUP_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new EditGroupCommand(GROUP_NAME1, GROUP0).equals(null));
    }

    @Test
    void equals_otherCommand() {

        assertFalse(new EditGroupCommand(GROUP_NAME1, GROUP0)
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentGroupName() {
        assertFalse(new EditGroupCommand(GROUP_NAME1, GROUP0)
                .equals(new EditGroupCommand(GROUP_NAME2, GROUP0)));
    }

    @Test
    void equals_differentGroupDescriptor() {
        assertFalse(new EditGroupCommand(GROUP_NAME1, GROUP0)
                .equals(new EditGroupCommand(GROUP_NAME1, GROUP2)));
    }

    @Test
    void equals() {
        assertTrue(
                new EditGroupCommand(GROUP_NAME1, GROUP0)
                        .equals(new EditGroupCommand(GROUP_NAME1, GROUP0))
        );
    }
}
