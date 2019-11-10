package seedu.address.model.currency;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ModelTestUtil.VALID_CURRENCY_1;
import static seedu.address.model.ModelTestUtil.VALID_CURRENCY_2;
import static seedu.address.model.ModelTestUtil.VALID_NAME_CURRENCY_2;
import static seedu.address.model.ModelTestUtil.VALID_SYMBOL_CURRENCY_2;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Name;
import seedu.address.testutil.CustomisedCurrencyBuilder;

public class CurrencyTest {

    @Test
    public void isSameCustomisedCurrency() {
        // same object -> returns true
        assertTrue(VALID_CURRENCY_1.isSameCustomisedCurrency(VALID_CURRENCY_1));

        // null -> returns false
        assertFalse(VALID_CURRENCY_1.isSameCustomisedCurrency(null));

        // different name -> returns false
        CustomisedCurrency editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1)
                .setName(new Name(VALID_NAME_CURRENCY_2)).build();
        assertFalse(VALID_CURRENCY_1.isSameCustomisedCurrency(editedCustomisedCurrencyA));

        // same name, same rate, different symbol -> returns true
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1)
                .setSymbol(new Symbol(VALID_SYMBOL_CURRENCY_2))
                .build();
        assertTrue(VALID_CURRENCY_1.isSameCustomisedCurrency(editedCustomisedCurrencyA));

        // same name, same symbol, same rate -> returns true
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1).build();
        assertTrue(VALID_CURRENCY_1.isSameCustomisedCurrency(editedCustomisedCurrencyA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CustomisedCurrency expenseACopy = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1).build();
        assertTrue(VALID_CURRENCY_1.equals(expenseACopy));

        // same object -> returns true
        assertTrue(VALID_CURRENCY_1.equals(VALID_CURRENCY_1));

        // null -> returns false
        assertFalse(VALID_CURRENCY_1.equals(null));

        // different type -> returns false
        assertFalse(VALID_CURRENCY_1.equals(5));

        // different expense -> returns false
        assertFalse(VALID_CURRENCY_1.equals(VALID_CURRENCY_2));

        // different name -> returns false
        CustomisedCurrency editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1)
                .setName(new Name(VALID_NAME_CURRENCY_2)).build();
        assertFalse(VALID_CURRENCY_1.equals(editedCustomisedCurrencyA));

        // different rate -> returns false
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1).setRate(new Rate("2")).build();
        assertFalse(VALID_CURRENCY_1.equals(editedCustomisedCurrencyA));

        // different symbol -> returns false
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1)
                .setSymbol(new Symbol(VALID_SYMBOL_CURRENCY_2)).build();
        assertFalse(VALID_CURRENCY_1.equals(editedCustomisedCurrencyA));

    }

}
