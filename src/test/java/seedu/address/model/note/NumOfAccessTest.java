package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;

public class NumOfAccessTest {

    @Test
    public void constructor_invalidNumOfAccess_throwsIllegalArgumentException() {
        int invalidNumOfAccess = -1;
        assertThrows(IllegalArgumentException.class, () -> new NumOfAccess(invalidNumOfAccess));
    }

    @Test
    public void invalidNumOfAccess() {
        // invalid numOfAccess
        assertFalse(NumOfAccess.isValidNumOfAccess(-1)); // negative number
        assertFalse(NumOfAccess.isValidNumOfAccess(-100)); // large negative number

        // valid numOfAccess
        assertTrue(NumOfAccess.isValidNumOfAccess(0));
        assertTrue(NumOfAccess.isValidNumOfAccess(1));
        assertTrue(NumOfAccess.isValidNumOfAccess(100)); //large positive number
    }

    @Test
    public void update() {
        int oldNumOfAccess = 4;
        int updatedNumOfAccess = oldNumOfAccess + 1;
        assertTrue(new NumOfAccess(updatedNumOfAccess).equals(new NumOfAccess(oldNumOfAccess).update()));
    }
}
