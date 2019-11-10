package seedu.address.model.currency;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AFRICA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SYMBOL_POUND;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Name;
import seedu.address.testutil.CustomisedCurrencyBuilder;

public class CurrencyTest {

    public static final CustomisedCurrency CURRENCY_A = CustomisedCurrencyBuilder.newInstance()
            .setName(new Name("CustomisedCurrency A"))
            .setSymbol(new Symbol("1"))
            .setRate(new Rate("0.01"))
            .build();
    public static final CustomisedCurrency CURRENCY_B = CustomisedCurrencyBuilder.newInstance()
            .setName(new Name("CustomisedCurrency B"))
            .setSymbol(new Symbol("&"))
            .setRate(new Rate("10000"))
            .build();

    @Test
    public void isSameCustomisedCurrency() {
        // same object -> returns true
        assertTrue(CURRENCY_A.isSameCustomisedCurrency(CURRENCY_A));

        // null -> returns false
        assertFalse(CURRENCY_A.isSameCustomisedCurrency(null));

        // different name -> returns false
        CustomisedCurrency editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(CURRENCY_A)
                .setName(new Name(VALID_NAME_AFRICA)).build();
        assertFalse(CURRENCY_A.isSameCustomisedCurrency(editedCustomisedCurrencyA));

        // same name, same rate, different symbol -> returns true
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(CURRENCY_A).setSymbol(new Symbol(VALID_SYMBOL_POUND))
                .build();
        assertTrue(CURRENCY_A.isSameCustomisedCurrency(editedCustomisedCurrencyA));

        // same name, same symbol, same rate -> returns true
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(CURRENCY_A).build();
        assertTrue(CURRENCY_A.isSameCustomisedCurrency(editedCustomisedCurrencyA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        CustomisedCurrency expenseACopy = CustomisedCurrencyBuilder.of(CURRENCY_A).build();
        assertTrue(CURRENCY_A.equals(expenseACopy));

        // same object -> returns true
        assertTrue(CURRENCY_A.equals(CURRENCY_A));

        // null -> returns false
        assertFalse(CURRENCY_A.equals(null));

        // different type -> returns false
        assertFalse(CURRENCY_A.equals(5));

        // different expense -> returns false
        assertFalse(CURRENCY_A.equals(CURRENCY_B));

        // different name -> returns false
        CustomisedCurrency editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(CURRENCY_A)
                .setName(new Name(VALID_NAME_BALI)).build();
        assertFalse(CURRENCY_A.equals(editedCustomisedCurrencyA));

        // different rate -> returns false
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(CURRENCY_A).setRate(new Rate("2")).build();
        assertFalse(CURRENCY_A.equals(editedCustomisedCurrencyA));

        // different symbol -> returns false
        editedCustomisedCurrencyA = CustomisedCurrencyBuilder.of(CURRENCY_A)
                .setSymbol(new Symbol(VALID_SYMBOL_POUND)).build();
        assertFalse(CURRENCY_A.equals(editedCustomisedCurrencyA));

    }

}
