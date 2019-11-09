//@@author wongsm7
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.queue.EnqueueCommand;
import seedu.address.model.ReferenceId;
import seedu.address.testutil.PersonBuilder;

public class EnqueueCommandTest {
    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EnqueueCommand(null));
    }

    @Test
    public void equals() {
        ReferenceId alice = new PersonBuilder().withPatientId(VALID_ID_AMY).withName("Alice").build().getReferenceId();
        ReferenceId bob = new PersonBuilder().withPatientId(VALID_ID_BOB).withName("Bob").build().getReferenceId();
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
