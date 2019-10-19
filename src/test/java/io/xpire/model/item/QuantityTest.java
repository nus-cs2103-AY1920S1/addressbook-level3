package io.xpire.model.item;

import static io.xpire.logic.CommandParserItemUtil.VALID_QUANTITY_APPLE;
import static io.xpire.logic.CommandParserItemUtil.VALID_QUANTITY_BANANA;

import static io.xpire.logic.CommandParserItemUtil.VALID_QUANTITY_EXPIRING_FISH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.xpire.logic.parser.exceptions.ParseException;

public class QuantityTest {

    @Test
    public void isSameQuantity() {

        //same object -> returns true
        assertTrue(VALID_QUANTITY_APPLE.equals(VALID_QUANTITY_APPLE));

        //null -> returns false
        assertFalse(VALID_QUANTITY_APPLE.equals(null));

        //different object, same quantity -> returns true
        assertTrue(VALID_QUANTITY_APPLE.equals(VALID_QUANTITY_EXPIRING_FISH));

        //different object, different quantity -> returns false
        assertFalse(VALID_QUANTITY_APPLE.equals(VALID_QUANTITY_BANANA));
    }

    @Test
    public void isValidInputQuantity() {
        //input is positive integer -> returns true
        assertTrue(Quantity.isValidInputQuantity("2"));

        //input is negative integer -> returns false
        assertFalse(Quantity.isValidInputQuantity("-4"));

        //input is positive double -> returns false
        assertFalse(Quantity.isValidInputQuantity("-3.14159265"));

        //input is zero -> returns false
        assertFalse(Quantity.isValidInputQuantity("0"));

        //input is a character -> returns false
        assertFalse(Quantity.isValidInputQuantity("a"));
    }

    @Test
    public void isValidQuantity() {
        //input is positive integer -> returns true
        assertTrue(Quantity.isValidQuantity("2"));

        //input is negative integer -> returns false
        assertFalse(Quantity.isValidQuantity("-4"));

        //input is positive double -> returns false
        assertFalse(Quantity.isValidQuantity("-3.14159265"));

        //input is zero -> returns true
        assertTrue(Quantity.isValidQuantity("0"));

        //input is a character -> returns false
        assertFalse(Quantity.isValidQuantity("a"));
    }

    @Test
    public void quantityIsZero() {

        //quantity is zero -> returns true
        assertTrue(Quantity.quantityIsZero(new Quantity("0", true)));

        //quantity is positive integer -> returns false
        assertFalse(Quantity.quantityIsZero(new Quantity("2")));

    }


    @Test
    public void deductQuantity() throws ParseException {

        Quantity testQuantity = new Quantity("3");

        //quantityToDeduct is greater than item quantity -> throws exception
        assertThrows(ParseException.class, ()-> testQuantity.deductQuantity(new Quantity("4")));

        //quantityToDeduct is equal to item quantity -> returns 0 for quantity
        Quantity toDeduct = new Quantity("3");
        Quantity expectedQuantity = new Quantity("0", true);
        assertTrue(expectedQuantity.equals(testQuantity.deductQuantity(toDeduct)));

        //quantityToDeduct is less than item quantity -> returns a valid quantity
        toDeduct = new Quantity("1");
        expectedQuantity = new Quantity("2");
        assertTrue(expectedQuantity.equals(testQuantity.deductQuantity(toDeduct)));
    }
}
