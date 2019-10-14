package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.common.ReferenceId;
import seedu.address.testutil.PersonBuilder;

public class EnqueueCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EnqueueCommand(null));
    }

    @Test
    public void equals() {
        ReferenceId alice = new PersonBuilder().withId("2322").withName("Alice").build().getReferenceId();
        ReferenceId bob = new PersonBuilder().withId("32323").withName("Bob").build().getReferenceId();
        EnqueueCommand addAliceCommand = new EnqueueCommand(alice);
        EnqueueCommand addBobCommand = new EnqueueCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        EnqueueCommand addAliceCommandCopy = new EnqueueCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }
}
