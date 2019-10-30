package seedu.moneygowhere.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_COST_BOB;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BILL_REMINDER;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.SpendingBuilder;

public class AddressBookTest {

    private final SpendingBook addressBook = new SpendingBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getSpendingList());
        assertEquals(Collections.emptyList(), addressBook.getReminderList());
        assertEquals(new Budget(0), addressBook.getBudget());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        SpendingBook newData = getTypicalSpendingBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void hasSpending_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasSpending(null));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasReminder(null));
    }

    @Test
    public void hasSpending_spendingNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasSpending(APPLE));
    }

    @Test
    public void hasReminder_reminderNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasReminder(BILL_REMINDER));
    }

    @Test
    public void hasSpending_spendingInAddressBook_returnsTrue() {
        addressBook.addSpending(APPLE);
        assertTrue(addressBook.hasSpending(APPLE));
    }

    @Test
    public void hasReminder_reminderInAddressBook_returnsTrue() {
        addressBook.addReminder(BILL_REMINDER);
        assertTrue(addressBook.hasReminder(BILL_REMINDER));
    }

    @Test
    public void hasSpending_spendingWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addSpending(APPLE);
        Spending editedAlice = new SpendingBuilder(APPLE).withCost(VALID_COST_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasSpending(editedAlice));
    }

    @Test
    public void getSpendingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getSpendingList().remove(0));
    }

    @Test
    public void setBudget_validInput_success() {
        Budget budget = new Budget(1000);
        addressBook.setBudget(budget);
        assertTrue(addressBook.getBudget().equals(budget));
    }

    /**
     * A stub ReadOnlyAddressBook whose spendings list can violate interface constraints.
     */
    private static class SpendingBookStub implements ReadOnlySpendingBook {
        private final ObservableList<Spending> spendings = FXCollections.observableArrayList();
        private final ObservableList<Reminder> reminders = FXCollections.observableArrayList();
        private final Budget budget = new Budget(0);
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
        public Budget getBudget() {
            return budget;
        }
    }

}
