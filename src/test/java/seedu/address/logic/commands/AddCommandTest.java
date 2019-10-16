package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Alias;
import seedu.address.commons.core.AliasMappings;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyModelHistory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;
import seedu.address.testutil.ExpenseBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullExpense_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void run_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();
        Expense validExpense = new ExpenseBuilder().build();

        List<Expense> expectedExpensesAdded = Arrays.asList(validExpense);
        Stack<ModelStub> expectedPastModels = new Stack<>();
        expectedPastModels.push(new ModelStubAcceptingExpenseAdded(modelStub));

        CommandResult commandResult = new AddCommand(validExpense).run(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validExpense), commandResult.getFeedbackToUser());
        assertEquals(expectedExpensesAdded, modelStub.expensesAdded);
        assertEquals(expectedPastModels, modelStub.pastModels);
    }

    @Test
    public void run_duplicateExpense_throwsCommandException() {
        Expense validExpense = new ExpenseBuilder().build();
        AddCommand addCommand = new AddCommand(validExpense);
        ModelStub modelStub = new ModelStubWithExpense(validExpense);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_EXPENSE, () -> addCommand.run(modelStub));
    }

    @Test
    public void equals() {
        Expense alice = new ExpenseBuilder().withDescription("Alice").build();
        Expense bob = new ExpenseBuilder().withDescription("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different expense -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
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
        public void resetData(Model model) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModelHistory getModelHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModelHistory(ReadOnlyModelHistory history) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRollback() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Model> rollbackModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canMigrate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Model> migrateModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToPastHistory(Model model) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToFutureHistory(Model model) {
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
        public void setAliasMappings(AliasMappings aliasMappings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AliasMappings getAliasMappings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUserAlias(Alias alias) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExpense(Expense target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExpense(Expense target, Expense editedExpense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPrimary(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Expense> getFilteredExpenseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Predicate<? super Expense> getFilteredExpensePredicate() {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void updateFilteredExpenseList(Predicate<? super Expense> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Predicate<? super Event> getFilteredEventPredicate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<? super Event> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String calculateStatistics(String command, Timestamp date1, Timestamp date2, Period period) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStatistic() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StringBuilder getStatistic() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single expense.
     */
    private class ModelStubWithExpense extends ModelStub {
        private final Expense expense;

        ModelStubWithExpense(Expense expense) {
            requireNonNull(expense);
            this.expense = expense;
        }

        @Override
        public boolean hasExpense(Expense expense) {
            requireNonNull(expense);
            return this.expense.isSameExpense(expense);
        }
    }

    /**
     * A Model stub that always accept the expense being added.
     */
    private class ModelStubAcceptingExpenseAdded extends ModelStub {
        final ArrayList<Expense> expensesAdded;
        final Stack<ModelStub> pastModels;

        public ModelStubAcceptingExpenseAdded() {
            expensesAdded = new ArrayList<>();
            pastModels = new Stack<>();
        }

        public ModelStubAcceptingExpenseAdded(ModelStubAcceptingExpenseAdded model) {
            expensesAdded = new ArrayList<>(model.expensesAdded);
            pastModels = (Stack<ModelStub>) model.pastModels.clone();
        }

        @Override
        public boolean hasExpense(Expense expense) {
            requireNonNull(expense);
            return expensesAdded.stream().anyMatch(expense::isSameExpense);
        }

        @Override
        public void addExpense(Expense expense) {
            requireNonNull(expense);
            expensesAdded.add(expense);
        }

        @Override
        public void addToHistory() {
            pastModels.push(new ModelStubAcceptingExpenseAdded(this));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof ModelStubAcceptingExpenseAdded)) {
                return false;
            }

            ModelStubAcceptingExpenseAdded other = (ModelStubAcceptingExpenseAdded) obj;
            return expensesAdded.equals(other.expensesAdded)
                    && pastModels.equals(other.pastModels);
        }
    }

}
