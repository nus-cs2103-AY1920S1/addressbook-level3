package cs.f10.t1.nursetraverse.model.patient;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    public void equals() {
        assertNotEquals(new Address("Blk 456, Den Road, #01-355"), new Address("Blk 456 Den Road #01-355"));
        assertNotEquals(new Address("-"), new Address("_"));
        assertNotEquals(new Address("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"),
                new Address("LENG Inc; 1234 Market St; San Francisco CA 2349879; USA"));

        assertEquals(new Address("Blk 456, Den Road, #01-355"), new Address("Blk 456, Den Road, #01-355"));
        assertEquals(new Address("-"), new Address("-"));
        assertEquals(new Address("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"),
                new Address("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
    }
}
