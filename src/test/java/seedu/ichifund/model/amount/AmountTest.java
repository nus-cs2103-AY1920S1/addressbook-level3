package seedu.ichifund.model.amount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class AmountTest {
    public static final Amount AMOUNT_POSITIVE_1 = new Amount("10.20");
    public static final Amount AMOUNT_POSITIVE_2 = new Amount("0.50");
    public static final Amount AMOUNT_POSITIVE_3 = new Amount("5");
    public static final Amount AMOUNT_NEGATIVE_1 = new Amount("-6.20");
    public static final Amount AMOUNT_NEGATIVE_2 = new Amount("-0.90");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("ab.cd"));
        assertFalse(Amount.isValidAmount(""));
        assertFalse(Amount.isValidAmount(" "));

        // invalid amount - dollars and cents format
        assertFalse(Amount.isValidAmount("50.0"));
        assertFalse(Amount.isValidAmount("50."));
        assertFalse(Amount.isValidAmount(".05"));

        // invalid amount - leading zeroes
        assertFalse(Amount.isValidAmount("050"));
        assertFalse(Amount.isValidAmount("050.00"));
        assertFalse(Amount.isValidAmount("00.00"));

        // invalid amount - too long
        assertFalse(Amount.isValidAmount("500000"));
        assertFalse(Amount.isValidAmount("500000.00"));

        // valid amount - positive
        assertTrue(Amount.isValidAmount("50.00"));
        assertTrue(Amount.isValidAmount("5"));
        assertTrue(Amount.isValidAmount("99999.99"));
        assertTrue(Amount.isValidAmount("0.50"));
        assertTrue(Amount.isValidAmount("0.00"));
        assertTrue(Amount.isValidAmount("0"));

        // valid amount - negative
        assertTrue(Amount.isValidAmount("-49.70"));
        assertTrue(Amount.isValidAmount("-99999.99"));
        assertTrue(Amount.isValidAmount("-5"));
        assertTrue(Amount.isValidAmount("-0.50"));
    }

    @Test
    public void getValueInCents() {
        Amount amount = new Amount("-5.70");
        assertEquals(amount.getValueInCents(), -570);

        amount = new Amount("5.70");
        assertEquals(amount.getValueInCents(), 570);
    }

    @Test
    public void isNegative() {
        // negative amount
        assertTrue(Amount.isNegative("-0.50"));
        assertTrue(Amount.isNegative("-10.50"));
        assertTrue(Amount.isNegative("-50"));

        // positive amount
        assertFalse(Amount.isNegative("0.50"));
        assertFalse(Amount.isNegative("10.50"));
        assertFalse(Amount.isNegative("50"));
    }

    @Test
    public void isZero() {
        // negative
        assertFalse(Amount.isZero("-0.50"));
        assertFalse(Amount.isZero("-10.50"));
        assertFalse(Amount.isZero("-50"));

        // positive
        assertFalse(Amount.isZero("0.50"));
        assertFalse(Amount.isZero("10.50"));
        assertFalse(Amount.isZero("50"));

        // zero
        assertTrue(Amount.isZero("0"));
        assertTrue(Amount.isZero("0.00"));
    }

    @Test
    public void testToString() {
        assertEquals(new Amount("0").toString(), "0.00");
        assertEquals(new Amount("1").toString(), "1.00");
        assertEquals(new Amount("1.05").toString(), "1.05");
        assertEquals(new Amount("0.09").toString(), "0.09");
        assertEquals(new Amount("-1").toString(), "-1.00");
        assertEquals(new Amount("-1.05").toString(), "-1.05");
        assertEquals(new Amount("-0.09").toString(), "-0.09");
    }

    @Test
    public void negate() {
        assertEquals(Amount.negate(new Amount("1.50")), new Amount ("-1.50"));
    }

    @Test
    public void add() {
        assertEquals(Amount.add(AMOUNT_POSITIVE_2, AMOUNT_POSITIVE_1).getValueInCents(),
                AMOUNT_POSITIVE_2.getValueInCents() + AMOUNT_POSITIVE_1.getValueInCents());
        assertEquals(Amount.add(AMOUNT_POSITIVE_2, AMOUNT_NEGATIVE_1).getValueInCents(),
                AMOUNT_POSITIVE_2.getValueInCents() + AMOUNT_NEGATIVE_1.getValueInCents());
        assertEquals(Amount.add(AMOUNT_NEGATIVE_2, AMOUNT_NEGATIVE_1).getValueInCents(),
                AMOUNT_NEGATIVE_2.getValueInCents() + AMOUNT_NEGATIVE_1.getValueInCents());
    }

    @Test
    public void subtract() {
        assertEquals(Amount.subtract(AMOUNT_POSITIVE_2, AMOUNT_POSITIVE_1).getValueInCents(),
                AMOUNT_POSITIVE_2.getValueInCents() - AMOUNT_POSITIVE_1.getValueInCents());
        assertEquals(Amount.subtract(AMOUNT_POSITIVE_2, AMOUNT_NEGATIVE_1).getValueInCents(),
                AMOUNT_POSITIVE_2.getValueInCents() - AMOUNT_NEGATIVE_1.getValueInCents());
        assertEquals(Amount.subtract(AMOUNT_NEGATIVE_2, AMOUNT_NEGATIVE_1).getValueInCents(),
                AMOUNT_NEGATIVE_2.getValueInCents() - AMOUNT_NEGATIVE_1.getValueInCents());
    }

    @Test
    public void addAll() {
        ArrayList<Amount> amounts = new ArrayList<>();
        amounts.add(AMOUNT_POSITIVE_1);
        amounts.add(AMOUNT_POSITIVE_2);
        amounts.add(AMOUNT_POSITIVE_3);
        amounts.add(AMOUNT_NEGATIVE_1);
        amounts.add(AMOUNT_NEGATIVE_2);
        assertEquals(Amount.addAll(amounts).getValueInCents(),
                AMOUNT_POSITIVE_1.getValueInCents()
                        + AMOUNT_POSITIVE_2.getValueInCents()
                        + AMOUNT_POSITIVE_3.getValueInCents()
                        + AMOUNT_NEGATIVE_1.getValueInCents()
                        + AMOUNT_NEGATIVE_2.getValueInCents());
    }
}
