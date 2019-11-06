package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
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

class EditPersonCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() throws DuplicateMappingException, DuplicatePersonException, DuplicateGroupException {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditPersonCommand(null, null));
    }

    @Test
    void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditPersonCommand(null, ALICE));
    }

    @Test
    void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditPersonCommand(ALICE.getName(), null));
    }

    @Test
    void execute_success() throws CommandException {
        CommandResult actualCommandResult =
                new EditPersonCommand(ALICE.getName(), ZACK).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditPersonCommand.MESSAGE_SUCCESS, ALICE.getName().toString()));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_descriptorNotEdited() throws CommandException {
        CommandResult actualCommandResult =
                new EditPersonCommand(ALICE.getName(), new PersonDescriptor()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditPersonCommand.MESSAGE_FAILURE,
                        EditPersonCommand.MESSAGE_NOT_EDITED));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_personDoesNotExist() throws CommandException {
        CommandResult actualCommandResult =
                new EditPersonCommand(ZACK.getName(), ALICE).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditPersonCommand.MESSAGE_FAILURE,
                        EditPersonCommand.MESSAGE_PERSON_NOT_FOUND));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void execute_duplicatePerson() throws CommandException {
        CommandResult actualCommandResult =
                new EditPersonCommand(ALICE.getName(), BENSON).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(String.format(EditPersonCommand.MESSAGE_FAILURE,
                        EditPersonCommand.MESSAGE_DUPLICATE_PERSON));

        assertEquals(actualCommandResult, expectedCommandResult);
    }

    @Test
    void equals_null() {
        assertFalse(new EditPersonCommand(ALICE.getName(), ZACK).equals(null));
    }

    @Test
    void equals_otherCommand() {

        assertFalse(new EditPersonCommand(ALICE.getName(), ZACK)
                .equals(new AddPersonCommand(ALICE)));
    }

    @Test
    void equals_differentPersonName() {
        assertFalse(new EditPersonCommand(ALICE.getName(), ZACK)
                .equals(new EditPersonCommand(BENSON.getName(), ZACK)));
    }

    @Test
    void equals_differentPersonDescriptor() {
        assertFalse(new EditPersonCommand(ALICE.getName(), ZACK)
                .equals(new EditPersonCommand(ALICE.getName(), BENSON)));
    }

    @Test
    void equals() {
        assertTrue(
                new EditPersonCommand(ALICE.getName(), ZACK)
                        .equals(new EditPersonCommand(ALICE.getName(), ZACK))
        );
    }
}
