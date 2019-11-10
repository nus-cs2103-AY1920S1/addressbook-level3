package seedu.address.financialtracker.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ExpenseTest {

    @Test
    void testNullCountryField() {
        Expense ep1 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"));

        assertTrue(ep1.getCountry() == null);
    }

    @Test
    void testEquals() {
        Expense ep1 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        Expense ep2 = new Expense(new Date("27102016"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));
        Expense ep3 = new Expense(new Date("27102011"), new Time("1720"), new Amount("4"), new Description("breakfast"),
                new Type("Food"), new Country("Singapore"));

        //same object -> true
        assertTrue(ep1.equals(ep1));

        //same field -> true
        assertTrue(ep1.equals(ep2));

        //different field ->false
        assertFalse(ep1.equals(ep3));
    }
}