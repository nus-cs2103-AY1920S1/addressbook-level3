package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.modelutil.TypicalModel;

class EditUserCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new EditUserCommand(null));
    }

    @Test
    void execute_success() throws CommandException {

        CommandResult actualCommandResult =
                new EditUserCommand(ZACK).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditUserCommand.MESSAGE_SUCCESS,
                        ZACK.getName().toString()));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_notEdited() throws CommandException {

        CommandResult actualCommandResult =
                new EditUserCommand(new PersonDescriptor()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditUserCommand.MESSAGE_FAILURE,
                        EditUserCommand.MESSAGE_NOT_EDITED));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new EditUserCommand(ZACK).equals(null));
    }

    @Test
    void equals_otherCommand() {

        assertFalse(new EditUserCommand(ZACK)
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentPersonDescriptor() {
        assertFalse(new EditUserCommand(ZACK)
                .equals(new EditUserCommand(ALICE)));
    }

    @Test
    void equals() {
        assertTrue(new EditUserCommand(ZACK).equals(new EditUserCommand(ZACK)));
    }
}
