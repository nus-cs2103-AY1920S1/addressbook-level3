package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.testutil.modelutil.TypicalModel;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

class EditPersonCommandTest {

    ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditPersonCommand(null, null));
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditPersonCommand(null, ALICE));
    }

    @Test
    public void constructor_nullDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditPersonCommand(ALICE.getName(), null));
    }

    @Test
    void execute_success() throws CommandException {
        Person person = model.findPerson(ALICE.getName());

        CommandResult actualCommandResult =
                new EditPersonCommand(ALICE.getName(), ZACK).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(EditPersonCommand.MESSAGE_SUCCESS + person.details());

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_descriptorNotEdited() throws CommandException {
        CommandResult actualCommandResult =
                new EditPersonCommand(ALICE.getName(), new PersonDescriptor()).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(EditPersonCommand.MESSAGE_NOT_EDITED);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }

    @Test
    void execute_personDoesNotExist() throws CommandException {
        CommandResult actualCommandResult =
                new EditPersonCommand(ZACK.getName(), ALICE).execute(model);

        CommandResult expectedCommandResult =
                new CommandResult(EditPersonCommand.MESSAGE_FAILURE);

        assertTrue(actualCommandResult.equals(expectedCommandResult));
    }
}
