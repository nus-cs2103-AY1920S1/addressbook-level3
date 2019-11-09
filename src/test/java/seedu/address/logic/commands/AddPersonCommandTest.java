package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ZACK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.modelutil.TypicalModel;


public class AddPersonCommandTest {

    private ModelManager model;

    @BeforeEach
    void init() {
        model = TypicalModel.generateTypicalModel();
    }

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPersonCommand(null));
    }

    @Test
    void execute_personAcceptedByModel() throws CommandException, PersonNotFoundException {

        CommandResult actualCommandResult = new AddPersonCommand(ZACK).execute(model);
        Person person = model.findPerson(ZACK.getName());
        assertNotNull(person);
        CommandResult expectedCommandResult =
                new CommandResult(String.format(AddPersonCommand.MESSAGE_SUCCESS, person.getName().toString()));

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }

    @Test
    void execute_duplicatedPerson() throws CommandException {
        CommandResult actualCommandResult = new AddPersonCommand(ALICE).execute(model);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddPersonCommand.MESSAGE_FAILURE, AddPersonCommand.MESSAGE_DUPLICATE_PERSON));

        assertTrue(expectedCommandResult.equals(actualCommandResult));
    }


    @Test
    public void equals() {
        AddPersonCommand addAliceCommand = new AddPersonCommand(ALICE);
        AddPersonCommand addBobCommand = new AddPersonCommand(BENSON);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddPersonCommand addAliceCommandCopy = new AddPersonCommand(ALICE);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

}
