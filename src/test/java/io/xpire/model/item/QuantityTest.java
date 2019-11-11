package io.xpire.model.item;

import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_APPLE;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_EGG;
import static io.xpire.testutil.TypicalItemsFields.VALID_QUANTITY_GRAPE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuantityTest {

    /**
     * Stub is used because quantity zero can only be created within Quantity class itself.
     */
    private static final Quantity QUANTITY_ZERO_STUB = new Quantity("1").deductQuantity(new Quantity("1"));

    @Test
    public void isSameQuantity() {

        //same object -> returns true
        assertTrue(VALID_QUANTITY_APPLE.equals(VALID_QUANTITY_APPLE));

        //null -> returns false
        assertFalse(VALID_QUANTITY_APPLE.equals(null));

        //different object, same quantity -> returns true
        assertTrue(VALID_QUANTITY_APPLE.equals(VALID_QUANTITY_GRAPE));

        //different object, different quantity -> returns false
        assertFalse(VALID_QUANTITY_APPLE.equals(VALID_QUANTITY_EGG));
    }

    @Test
    public void isValidQuantity() {
        //input is positive integer -> returns true
        assertTrue(Quantity.isValidQuantity("2"));

        //input is negative integer -> returns false
        assertFalse(Quantity.isValidQuantity("-4"));

        //input is positive double -> returns false
        assertFalse(Quantity.isValidQuantity("-3.14159265"));

        //input is zero -> returns false
        assertFalse(Quantity.isValidQuantity("0"));

        //input is a character -> returns false
        assertFalse(Quantity.isValidQuantity("a"));

        //input is greater than maximum allowed value
        assertFalse(Quantity.isValidQuantity("100001"));

        //input is greater than maximum allowed value
        assertFalse(Quantity.isNumericButExceedQuantity("999999"));
    }

    @Test
    public void isNumericButExceedQuantity() {

        //input exceeds accepted input length -> true
        assertTrue(Quantity.isNumericButExceedQuantity("1000000"));

        //input is signed and exceeds accepted input length -> false
        assertFalse(Quantity.isNumericButExceedQuantity("-1000000"));

        //input is of accepted input length -> false
        assertFalse(Quantity.isNumericButExceedQuantity("999999"));

        //input is of accepted input length -> false
        assertFalse(Quantity.isNumericButExceedQuantity("000000"));

        //input is of accepted input length but signed -> false
        assertFalse(Quantity.isNumericButExceedQuantity("-1"));

    }


    @Test
    public void isLessThan() {

        Quantity toCompare = new Quantity("10");

        // toCompare is more than input quantity -> false
        assertFalse(toCompare.isLessThan(new Quantity("2")));

        // toCompare is equals to input quantity -> true
        assertFalse(toCompare.isLessThan(new Quantity("10")));

        // toCompare is less than input quantity -> true
        assertTrue(toCompare.isLessThan(new Quantity("11")));

    }

    @Test
    public void quantityIsZero() {
        //quantity is zero -> returns true
        assertTrue(Quantity.quantityIsZero(QUANTITY_ZERO_STUB));

        //quantity is positive integer -> returns false
        assertFalse(Quantity.quantityIsZero(new Quantity("2")));
    }

    @Test
    public void deductQuantity() {
        Quantity testQuantity = new Quantity("3");

        // quantityToDeduct is equal to xpireItem quantity -> returns 0 for quantity
        Quantity toDeduct = new Quantity("3");
        Quantity expectedQuantity = QUANTITY_ZERO_STUB;
        assertTrue(expectedQuantity.equals(testQuantity.deductQuantity(toDeduct)));

        // quantityToDeduct is less than xpireItem quantity -> returns a valid quantity
        toDeduct = new Quantity("1");
        expectedQuantity = new Quantity("2");
        assertTrue(expectedQuantity.equals(testQuantity.deductQuantity(toDeduct)));
    }

    @Test
    public void increaseQuantity() {
        Quantity testQuantity = new Quantity("6");

        // quantity added results in maximum possible quantity -> returns valid quantity
        Quantity expectedQuantity = new Quantity("100000");
        assertTrue(expectedQuantity.equals(testQuantity.increaseQuantity(new Quantity("99994"))));
    }

}
