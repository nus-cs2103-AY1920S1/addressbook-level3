package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author{lawncegoh}
class AddressTest {

    private Address address1 = new Address("Blk 111 Hougang");
    private Address address2 = new Address("Blk 999 Simei");
    private Address address1copy = new Address("Blk 111 Hougang");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));

        // blank address
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // missing parts
        assertFalse(Address.isValidAddress(" sadsadasd")); // first letter is a white space

        // valid address
        assertTrue(Address.isValidAddress("Blk 237 Hougang St 21"));
        assertTrue(Address.isValidAddress("14 Pasir Ris Drive #08-29"));
    }

    @Test
    public void equal() {
        //diff address gives false
        assertFalse(address1.equals(address2));

        //same value gives true
        assertTrue(address1.equals(address1copy));
    }

    @Test
    public void checkString() {
        assertEquals(address1.toString(), "Blk 111 Hougang");
    }

    @Test
    public void checkHashCode() {
        assertEquals(address1.hashCode(), address1.value.hashCode());
    }
}
