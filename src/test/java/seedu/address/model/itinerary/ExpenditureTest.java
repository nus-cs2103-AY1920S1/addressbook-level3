package seedu.address.model.itinerary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExpenditureTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Expenditure(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidExpenditure = "";
        assertThrows(IllegalArgumentException.class, () -> new Expenditure(invalidExpenditure));
    }

    @Test
    public void isValid() {
        assertFalse(Expenditure.isValidExpenditure("")); // empty string
        assertFalse(Expenditure.isValidExpenditure(" ")); // spaces only
        assertFalse(Expenditure.isValidExpenditure("0.1234")); // containing more than one decimal place
        assertFalse(Expenditure.isValidExpenditure("Not a number")); // containing only alphabets

        // valid addresses
        assertTrue(Expenditure.isValidExpenditure("0")); // no decimal point
        assertTrue(Location.isValidLocation("1.0")); // one decimal point
        assertTrue(Location.isValidLocation("9.99")); // two decimal points
    }

}
