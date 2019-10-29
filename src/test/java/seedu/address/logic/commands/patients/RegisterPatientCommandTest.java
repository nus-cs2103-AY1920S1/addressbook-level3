package seedu.address.logic.commands.patients;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.utils.ModelAcceptingPersonAddedStub;
import seedu.address.logic.commands.utils.ModelStub;
import seedu.address.logic.commands.utils.ModelWithPersonStub;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RegisterPatientCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RegisterPatientCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelAcceptingPersonAddedStub modelStub = new ModelAcceptingPersonAddedStub();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new RegisterPatientCommand(validPerson).execute(modelStub);

        assertEquals(String.format(RegisterPatientCommand.MESSAGE_SUCCESS, validPerson),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        RegisterPatientCommand registerPatientCommand = new RegisterPatientCommand(validPerson);
        ModelStub modelStub = new ModelWithPersonStub(validPerson);

        //assertCommandFailure(addCommand, modelStub, AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertThrows(CommandException.class,
            RegisterPatientCommand.MESSAGE_DUPLICATE_PERSON, () -> registerPatientCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        RegisterPatientCommand addAliceCommand = new RegisterPatientCommand(alice);
        RegisterPatientCommand addBobCommand = new RegisterPatientCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        RegisterPatientCommand addAliceCommandCopy = new RegisterPatientCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
