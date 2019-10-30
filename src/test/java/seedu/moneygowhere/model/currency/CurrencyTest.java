package seedu.moneygowhere.model.currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CurrencyTest {

    @Test
    public void isSameCurrency() {
        Currency one = new Currency("SGD", "$", 1.00);
        Currency two = new Currency("SGD", "@", 1.00);
        Currency three = new Currency("MYR", "$", 1.00);
        Currency four = new Currency("SGD", "$", 1.05);

        // same
        assertTrue(one.isSameCurrency(one));

        // null
        assertFalse(one.isSameCurrency(null));

        // same name but different symbol -> same
        assertTrue(one.isSameCurrency(two));

        // same name but different rate -> same
        assertTrue(four.isSameCurrency(one));

        // different name but same symbol
        assertFalse(one.isSameCurrency(three));
    }

    @Test
    public void equals() {
        Currency one = new Currency("SGD", "$", 1.00);
        Currency two = new Currency("SGD", "@", 1.00);
        Currency three = new Currency("MYR", "$", 1.00);
        Currency four = new Currency("SGD", "$", 1.05);

        // same
        assertTrue(one == one);

        // null
        assertFalse(one == null);

        assertFalse(one.equals("Apple"));

        // same name but different symbol
        assertFalse(one.equals(two));

        // same name but different rate
        assertFalse(four.equals(one));

        // different name but same symbol
        assertFalse(one.equals(three));
    }

    @Test
    public void test_toString() {
        assertEquals("SGD ($): 1.000", new Currency("SGD", "$", 1.00).toString());
    }

    @Test
    public void test_compareTo() {
        Currency one = new Currency("SGD", "$", 1.00);
        Currency two = new Currency("SGD", "@", 1.00);

        assertEquals(0, one.compareTo(two));
    }
}
