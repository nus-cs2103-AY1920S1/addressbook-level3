package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME0;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;



class DeleteGroupCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteGroupCommand(null));
    }

    @Test
    void execute_success() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteGroupCommand(GROUP_NAME1).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteGroupCommand.MESSAGE_SUCCESS, GROUP_NAME1.toString()));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_failure() throws CommandException {

        CommandResult actualCommandResult =
                new DeleteGroupCommand(GROUP_NAME0).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(DeleteGroupCommand.MESSAGE_FAILURE,
                        DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND));

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void equals_null() {
        assertFalse(new DeleteGroupCommand(GROUP_NAME1).equals(null));
    }

    @Test
    void equals_otherCommand() {
        assertFalse(new DeleteGroupCommand(GROUP_NAME1)
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentGroupName() {
        assertFalse(
                new DeleteGroupCommand(GROUP_NAME1).equals(
                        new DeleteGroupCommand(GROUP_NAME2)
                )
        );
    }

    @Test
    void equals() {
        assertTrue(
                new DeleteGroupCommand(GROUP_NAME1).equals(
                        new DeleteGroupCommand(GROUP_NAME1)
                )
        );
    }
}
