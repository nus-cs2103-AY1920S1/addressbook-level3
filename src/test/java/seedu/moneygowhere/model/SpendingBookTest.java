package seedu.moneygowhere.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;
import static seedu.moneygowhere.testutil.TypicalSpendings.BILL_REMINDER;
import static seedu.moneygowhere.testutil.TypicalSpendings.CATFOOD;
import static seedu.moneygowhere.testutil.TypicalSpendings.DESSERT;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.util.CurrencyDataUtil;
import seedu.moneygowhere.testutil.SpendingBuilder;

public class SpendingBookTest {

    private final SpendingBook spendingBook = new SpendingBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), spendingBook.getSpendingList());
        assertEquals(Collections.emptyList(), spendingBook.getReminderList());
        assertEquals(new Budget(1000), spendingBook.getBudget());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        SpendingBook newData = getTypicalSpendingBook();
        spendingBook.resetData(newData);
        assertEquals(newData, spendingBook);
    }

    @Test
    public void hasSpending_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingBook.hasSpending(null));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingBook.hasReminder(null));
    }

    @Test
    public void hasSpending_spendingNotInAddressBook_returnsFalse() {
        assertFalse(spendingBook.hasSpending(APPLE));
    }

    @Test
    public void hasReminder_reminderNotInAddressBook_returnsFalse() {
        assertFalse(spendingBook.hasReminder(BILL_REMINDER));
    }

    @Test
    public void hasSpending_spendingInAddressBook_returnsTrue() {
        spendingBook.addSpending(APPLE);
        assertTrue(spendingBook.hasSpending(APPLE));

        List<Spending> spendings = new ArrayList<>(Arrays.asList(BANANA, CATFOOD, DESSERT));
        spendingBook.addSpending(spendings);

        assertTrue(spendingBook.hasSpending(BANANA));
        assertTrue(spendingBook.hasSpending(CATFOOD));
        assertTrue(spendingBook.hasSpending(DESSERT));

    }

    @Test
    public void hasReminder_reminderInAddressBook_returnsTrue() {
        spendingBook.addReminder(BILL_REMINDER);
        assertTrue(spendingBook.hasReminder(BILL_REMINDER));
    }

    @Test
    public void hasSpending_spendingWithSameIdentityFieldsInAddressBook_returnsTrue() {
        spendingBook.addSpending(APPLE);
        Spending editedAlice = new SpendingBuilder(APPLE).withRemark(VALID_REMARK_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(spendingBook.hasSpending(editedAlice));
    }

    @Test
    public void getSpendingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> spendingBook.getSpendingList().remove(0));
    }

    @Test
    public void setBudget_validInput_success() {
        Budget budget = new Budget(1000);
        spendingBook.setBudget(budget);
        assertTrue(spendingBook.getBudget().equals(budget));
    }

    /**
     * A stub ReadOnlyAddressBook whose spendings list can violate interface constraints.
     */
    private static class SpendingBookStub implements ReadOnlySpendingBook {
        private final ObservableList<Spending> spendings = FXCollections.observableArrayList();
        private final ObservableList<Reminder> reminders = FXCollections.observableArrayList();
        private final Budget budget = new Budget(0);
        private final ObservableList<Currency> currencies =
                FXCollections.observableArrayList(CurrencyDataUtil.getSampleCurrencies());
        private ObjectProperty<Currency> currencyInUse;

        SpendingBookStub(Collection<Spending> spendings) {
            this.spendings.setAll(spendings);
        }

        @Override
        public ObservableList<Spending> getSpendingList() {
            return spendings;
        }

        @Override
        public ObservableList<Reminder> getReminderList() {
            return reminders;
        }

        @Override
        public ObservableList<Currency> getCurrencies() {
            return currencies;
        }

        @Override
        public Currency getCurrencyInUse() {
            return currencyInUse.getValue();
        }

        @Override
        public void registerCurrencyChangedListener(ChangeListener<Currency> currencyChangeListener) {
            currencyInUse.addListener(currencyChangeListener);
        }

        @Override
        public Budget getBudget() {
            return budget;
        }
    }

}
