package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    public void isValidAddress_emptyString() {
        assertTrue(Address.isValidAddress(""));
    }

    @Test
    public void isValidAddress_exampleAddress() {
        assertTrue(Address.isValidAddress("Blk 123 Example St #01-23 S(123456)"));
    }

    @Test
    public void testToString() {
        assertEquals("Blk 123 Example St #01-23 S(123456)", (new
                Address("Blk 123 Example St #01-23 S(123456)")).toString());
    }

    @Test
    public void testEquals_sameAddress() {
        assertEquals(new Address("Blk 123 Example St #01-23 S(123456)"),
                new Address("Blk 123 Example St #01-23 S(123456)"));
    }

    @Test
    public void testEquals_differentAddress() {
        assertNotEquals(new Address("Blk 123 Example St #01-23 S(123456)"),
                new Address("Blk 456 Example St #01-23 S(123456)"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new Address("Blk 456 Example St #01-23 S(123456)"));
    }

    @Test
    public void testHashCode_sameAddress() {
        assertEquals(new Address("Blk 123 Example St #01-23 S(123456)").hashCode(),
                new Address("Blk 123 Example St #01-23 S(123456)").hashCode());
    }

    @Test
    public void testHashCode_differentAddress() {
        assertNotEquals(new Address("Blk 123 Example St #01-23 S(123456)").hashCode(),
                new Address("Blk 456 Example St #01-23 S(123456)").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new Address("Blk 456 Example St #01-23 S(123456)").hashCode());
    }

}
