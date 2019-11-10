package seedu.address.model.currency;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ModelTestUtil.VALID_CURRENCY_1;
import static seedu.address.model.ModelTestUtil.VALID_CURRENCY_2;
import static seedu.address.model.ModelTestUtil.VALID_RATE_CURRENCY_2;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import seedu.address.model.currency.exceptions.DuplicateCurrencyException;
import seedu.address.model.currency.exceptions.CurrencyNotFoundException;
import seedu.address.testutil.CustomisedCurrencyBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CurrencyListTest {



    @Test
    void contains_nullCurrency_throwsNullPointerException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(NullPointerException.class, () -> currencyList.contains(null));
    }

    @Test
    void contains_expenseNotInList_returnsFalse() {
        CurrencyList currencyList = new CurrencyList();
        assertFalse(currencyList.contains(VALID_CURRENCY_1));
    }

    @Test
    void contains_expenseInList_returnsTrue() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            assertTrue(currencyList.contains(VALID_CURRENCY_1));
        });
    }

    @Test
    void contains_expenseWithSameIdentityFieldsInList_returnsTrue() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            CustomisedCurrency editedCurrencyA = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1)
                    .setRate(new Rate(VALID_RATE_CURRENCY_2))
                    .build();
            assertTrue(currencyList.contains(editedCurrencyA));
        });
    }

    @Test
    void add_nullCurrency_throwsNullPointerException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(NullPointerException.class, () -> currencyList.add(null));
    }

    @Test
    void add_duplicateCurrency_throwsDuplicateCurrencyException() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            assertThrows(DuplicateCurrencyException.class, () -> currencyList.add(VALID_CURRENCY_1));
        });
    }

    @Test
    void setCurrency_nullTargetCurrency_throwsNullPointerException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(NullPointerException.class, () -> currencyList.set(null, VALID_CURRENCY_1));
    }

    @Test
    void setCurrency_nullEditedCurrency_throwsNullPointerException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(NullPointerException.class, () -> currencyList.set(VALID_CURRENCY_1, null));
    }

    @Test
    void setCurrency_targetCurrencyNotInList_throwsCurrencyNotFoundException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(CurrencyNotFoundException.class, () -> currencyList.set(VALID_CURRENCY_1, VALID_CURRENCY_1));
    }

    @Test
    void setCurrency_editedCurrencyIsSameCurrency_success() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            currencyList.set(VALID_CURRENCY_1, VALID_CURRENCY_1);
            CurrencyList expectedUniqueCurrencyList = new CurrencyList();
            expectedUniqueCurrencyList.add(VALID_CURRENCY_1);
            assertEquals(expectedUniqueCurrencyList, currencyList);
        });
    }

    @Test
    void setCurrency_editedCurrencyHasSameIdentity_success() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            CustomisedCurrency editedCurrency = CustomisedCurrencyBuilder.of(VALID_CURRENCY_1)
                    .setRate(new Rate(VALID_RATE_CURRENCY_2))
                    .build();
            currencyList.set(VALID_CURRENCY_1, editedCurrency);
            CurrencyList expectedUniqueCurrencyList = new CurrencyList();
            expectedUniqueCurrencyList.add(editedCurrency);
            assertEquals(expectedUniqueCurrencyList, currencyList);
        });
    }

    @Test
    void setCurrency_editedCurrencyHasDifferentIdentity_success() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            currencyList.set(VALID_CURRENCY_1, VALID_CURRENCY_2);
            CurrencyList expectedUniqueCurrencyList = new CurrencyList();
            expectedUniqueCurrencyList.add(VALID_CURRENCY_2);
            assertEquals(expectedUniqueCurrencyList, currencyList);
        });
    }

    @Test
    public void setCurrency_editedCurrencyHasNonUniqueIdentity_throwsDuplicateCurrencyException() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            currencyList.add(VALID_CURRENCY_2);
            assertThrows(DuplicateCurrencyException.class, () -> currencyList.set(VALID_CURRENCY_1, VALID_CURRENCY_2));
        });
    }

    @Test
    public void remove_nullCurrency_throwsNullPointerException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(NullPointerException.class, () -> currencyList.remove((CustomisedCurrency) null));
    }

    @Test
    public void remove_expenseDoesNotExist_throwsCurrencyNotFoundException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(CurrencyNotFoundException.class, () -> currencyList.remove(VALID_CURRENCY_1));
    }

    @Test
    public void remove_existingCurrency_removesCurrency() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            currencyList.remove(VALID_CURRENCY_1);
            CurrencyList expectedUniqueCurrencyList = new CurrencyList();
            assertEquals(expectedUniqueCurrencyList, currencyList);
        });
    }

    @Test
    public void setCurrencies_uniqueCurrencyList_replacesOwnListWithProvidedUniqueCurrencyList() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            List<CustomisedCurrency> expectedUniqueCurrencyList = new ArrayList<CustomisedCurrency>();
            expectedUniqueCurrencyList.add(VALID_CURRENCY_2);
            currencyList.set(expectedUniqueCurrencyList);
            assertEquals(expectedUniqueCurrencyList, currencyList.asUnmodifiableObservableList());
        });
    }
    //-------------------------------------------------------------------

    @Test
    public void setCurrencies_nullList_throwsNullPointerException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(NullPointerException.class, () -> currencyList.set(null));
    }

    @Test
    public void setCurrencies_list_replacesOwnListWithProvidedList() {
        CurrencyList currencyList = new CurrencyList();
        assertDoesNotThrow(() -> {
            currencyList.add(VALID_CURRENCY_1);
            List<CustomisedCurrency> expenses = Collections.singletonList(VALID_CURRENCY_2);
            currencyList.set(expenses);
            CurrencyList expectedUniqueCurrencyList = new CurrencyList();
            expectedUniqueCurrencyList.add(VALID_CURRENCY_2);
            assertEquals(expectedUniqueCurrencyList, currencyList);
        });
    }

    @Test
    public void setCurrencies_listWithDuplicateCurrencies_throwsDuplicateCurrencyException() {
        CurrencyList currencyList = new CurrencyList();
        List<CustomisedCurrency> listWithDuplicateCurrencies = Arrays.asList(VALID_CURRENCY_1, VALID_CURRENCY_1);
        assertThrows(DuplicateCurrencyException.class, () -> currencyList.set(listWithDuplicateCurrencies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        CurrencyList currencyList = new CurrencyList();
        assertThrows(UnsupportedOperationException.class, () ->
                currencyList.asUnmodifiableObservableList().remove(0));
    }

}
