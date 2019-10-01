package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.personutil.PersonBuilder;

public class ShowCommandTest {

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        ShowCommand showAliceCommand = new ShowCommand(alice.getName());
        ShowCommand showBobCommand = new ShowCommand(bob.getName());

        //same object -> true
        assertTrue(showAliceCommand.equals(showAliceCommand));

        //same values -> true
        ShowCommand showAliceDuplicateCommand = new ShowCommand(alice.getName());
        assertTrue(showAliceCommand.equals(showAliceCommand));

        //different show commands -> false
        assertFalse(showAliceCommand.equals(showBobCommand));

        //null -> false
        assertFalse(showAliceCommand.equals(null));

        //different types -> false
        assertFalse(showAliceCommand.equals(1));
    }
}
