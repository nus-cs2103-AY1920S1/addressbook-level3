package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.personutil.PersonBuilder;

public class ShowPersonCommandTest {

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        ShowPersonCommand showAliceCommand = new ShowPersonCommand(alice.getName());
        ShowPersonCommand showBobCommand = new ShowPersonCommand(bob.getName());

        //same object -> true
        assertTrue(showAliceCommand.equals(showAliceCommand));

        //same values -> true
        ShowPersonCommand showAliceDuplicateCommand = new ShowPersonCommand(alice.getName());
        assertTrue(showAliceCommand.equals(showAliceCommand));

        //different show commands -> false
        assertFalse(showAliceCommand.equals(showBobCommand));

        //null -> false
        assertFalse(showAliceCommand.equals(null));

        //different types -> false
        assertFalse(showAliceCommand.equals(1));
    }
}
