package seedu.moneygowhere.logic.commands.reminder;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.moneygowhere.commons.core.GuiSettings;
import seedu.moneygowhere.logic.commands.CommandResult;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.ReadOnlyUserPrefs;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.ReminderBuilder;

class AddReminderCommandTest {

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddReminderCommand(null));
    }

    @Test
    public void execute_spendingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = new AddReminderCommand(validReminder).execute(modelStub);

        assertEquals(String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void equals() {
        Reminder schoolFee = new ReminderBuilder().withRemark("Pay School fee").build();
        Reminder phoneBill = new ReminderBuilder().withRemark("Pay Phone bill").build();
        AddReminderCommand addSchoolFeeReminderCommand = new AddReminderCommand(schoolFee);
        AddReminderCommand addPhoneBillReminderCommand = new AddReminderCommand(phoneBill);

        // same object -> returns true
        assertTrue(addSchoolFeeReminderCommand.equals(addSchoolFeeReminderCommand));

        // same values -> returns true
        AddReminderCommand addSchoolFeeReminderCommandCopy = new AddReminderCommand(schoolFee);
        assertTrue(addSchoolFeeReminderCommand.equals(addSchoolFeeReminderCommandCopy));

        // different types -> returns false
        assertFalse(addSchoolFeeReminderCommand.equals(1));

        // null -> returns false
        assertFalse(addSchoolFeeReminderCommand == null);

        // different Spending -> returns false
        assertFalse(addSchoolFeeReminderCommand.equals(addPhoneBillReminderCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getSpendingBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSpendingBookFilePath(Path spendingBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addSpending(Spending spending) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSpendingBook(ReadOnlySpendingBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySpendingBook getSpendingBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSpending(Spending spending) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteSpending(Spending target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSpending(Spending target, Spending editedSpending) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Spending> getFilteredSpendingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredSpendingList(Predicate<Spending> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedSpendingList(Comparator<Spending> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Budget getBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteReminder(Reminder reminder) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Reminder> getReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Currency getCurrencyInUse() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Currency> getCurrencies() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrencyInUse(Currency currency) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Spending> getStatsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateStatsPredicate(Predicate<Spending> statsPredicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Spending.
     */
    private class ModelStubWithReminder extends ModelStub {
        private final Reminder reminder;

        ModelStubWithReminder(Reminder reminder) {
            requireNonNull(reminder);
            this.reminder = reminder;
        }

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return this.reminder.isSameReminder(reminder);
        }
    }

    /**
     * A Model stub that always accept the Spending being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        private final List<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public boolean hasReminder(Reminder reminder) {
            requireNonNull(reminder);
            return remindersAdded.stream().anyMatch(reminder::isSameReminder);
        }

        @Override
        public void addReminder(Reminder reminder) {
            requireNonNull(reminder);
            remindersAdded.add(reminder);
        }

        @Override
        public ReadOnlySpendingBook getSpendingBook() {
            return new SpendingBook();
        }
    }
}
