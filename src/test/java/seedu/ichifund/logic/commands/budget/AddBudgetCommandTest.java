package seedu.ichifund.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.ichifund.commons.core.GuiSettings;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.ReadOnlyUserPrefs;
import seedu.ichifund.model.analytics.Data;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.context.TransactionContext;
import seedu.ichifund.model.person.Person;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.repeater.RepeaterUniqueId;
import seedu.ichifund.model.transaction.Transaction;
import seedu.ichifund.testutil.BudgetBuilder;

public class AddBudgetCommandTest {

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBudgetCommand(null));
    }

    @Test
    public void execute_budgetAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBudgetAdded modelStub = new ModelStubAcceptingBudgetAdded();
        Budget validBudget = new BudgetBuilder().build();

        CommandResult commandResult = new AddBudgetCommand(validBudget).execute(modelStub);

        assertEquals(String.format(AddBudgetCommand.MESSAGE_SUCCESS, validBudget), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBudget), modelStub.budgetsAdded);
    }

    @Test
    public void execute_duplicateBudget_throwsCommandException() {
        Budget validBudget = new BudgetBuilder().build();
        AddBudgetCommand addBudgetCommand = new AddBudgetCommand(validBudget);
        ModelStub modelStub = new ModelStubWithBudget(validBudget);

        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_DUPLICATE_BUDGET, ()
            -> addBudgetCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Budget overall = new BudgetBuilder().withDescription("Limit for Spendings").build();
        Budget food = new BudgetBuilder().withDescription("Limit for Food").build();
        AddBudgetCommand addOverallCommand = new AddBudgetCommand(overall);
        AddBudgetCommand addFoodCommand = new AddBudgetCommand(food);

        // same object -> returns true
        assertTrue(addOverallCommand.equals(addOverallCommand));

        // same values -> returns true
        AddBudgetCommand addOverallCommandCopy = new AddBudgetCommand(overall);
        assertTrue(addOverallCommand.equals(addOverallCommandCopy));

        // different types -> returns false
        assertFalse(addOverallCommand.equals(1));

        // null -> returns false
        assertFalse(addOverallCommand.equals(null));

        // different budget -> returns false
        assertFalse(addOverallCommand.equals(addFoodCommand));
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
        public Path getFundBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFundBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public RepeaterUniqueId getCurrentRepeaterUniqueId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentRepeaterUniqueId(RepeaterUniqueId uniqueId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTransaction(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTransaction(Transaction target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransaction(Transaction target, Transaction editedTransaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getFilteredTransactionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public TransactionContext getTransactionContext() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTransactionContext(TransactionContext transactionContext) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateTransactionContext(Transaction transaction) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Transaction> getAssociatedTransactions(RepeaterUniqueId repeaterUniqueId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRepeater(Repeater repeater) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createRepeaterTransactions(Repeater repeater) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRepeater(Repeater repeater) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRepeater(Repeater target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRepeaterTransactions(RepeaterUniqueId repeaterUniqueId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRepeater(Repeater target, Repeater editedRepeater) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Repeater> getFilteredRepeaterList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRepeaterList(Predicate<Repeater> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void addBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBudget(Budget target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setBudget(Budget target, Budget editedBudget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBudgetList(Predicate<Budget> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyProperty<TransactionContext> getTransactionContextProperty() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Data> getDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateDataList(List<Data> datas) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFundBook(ReadOnlyFundBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFundBook getFundBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single budget.
     */
    private class ModelStubWithBudget extends ModelStub {
        private final Budget budget;

        ModelStubWithBudget(Budget budget) {
            requireNonNull(budget);
            this.budget = budget;
        }

        @Override
        public boolean hasBudget(Budget budget) {
            requireNonNull(budget);
            return this.budget.isSameBudget(budget);
        }
    }

    /**
     * A Model stub that always accept the budget being added.
     */
    private class ModelStubAcceptingBudgetAdded extends ModelStub {
        final ArrayList<Budget> budgetsAdded = new ArrayList<>();

        @Override
        public boolean hasBudget(Budget budget) {
            requireNonNull(budget);
            return budgetsAdded.stream().anyMatch(budget::isSameBudget);
        }

        @Override
        public void addBudget(Budget budget) {
            requireNonNull(budget);
            budgetsAdded.add(budget);
        }

        @Override
        public ReadOnlyFundBook getFundBook() {
            return new FundBook();
        }
    }

}
