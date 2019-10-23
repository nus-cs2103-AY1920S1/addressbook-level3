package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalBudgets.OUTSIDE_SCHOOL;
import static seedu.address.testutil.TypicalBudgets.SCHOOL;

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
import seedu.address.logic.commands.budget.AddBudgetCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyModelHistory;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Timestamp;


public class AddBudgetCommandTest {

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBudgetCommand(null));
    }

    @Test
    public void run_budgetAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBudgetAdded modelStub = new ModelStubAcceptingBudgetAdded();
        Budget validBudget = SCHOOL;

        List<Budget> expectedBudgetsAdded = Arrays.asList(validBudget);
        Stack<ModelStub> expectedPastModels = new Stack<>();
        expectedPastModels.push(new ModelStubAcceptingBudgetAdded(modelStub));

        CommandResult commandResult = new AddBudgetCommand(validBudget).run(modelStub);

        assertEquals(String.format(AddBudgetCommand.MESSAGE_SUCCESS, validBudget), commandResult.getFeedbackToUser());
        assertEquals(expectedBudgetsAdded, modelStub.budgetsAdded);
        assertEquals(expectedPastModels, modelStub.pastModels);
    }

    @Test
    public void run_duplicateBudget_throwsCommandException() {
        Budget validBudget = SCHOOL;
        AddBudgetCommand addBudgetCommand = new AddBudgetCommand(validBudget);
        ModelStub modelStub = new ModelStubWithBudget(validBudget);

        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_DUPLICATE_BUDGET, () ->
                addBudgetCommand.run(modelStub));
    }

    @Test
    public void equals() {
        AddBudgetCommand addSchoolBudgetCommand = new AddBudgetCommand(SCHOOL);
        AddBudgetCommand addOutsideSchoolBudgetCommand = new AddBudgetCommand(OUTSIDE_SCHOOL);

        // same object -> returns true
        assertTrue(addSchoolBudgetCommand.equals(addSchoolBudgetCommand));

        // same values -> returns true
        AddBudgetCommand addSchoolBudgetCommandCopy = new AddBudgetCommand(SCHOOL);
        assertTrue(addSchoolBudgetCommand.equals(addSchoolBudgetCommandCopy));

        // different types -> returns false
        assertFalse(addSchoolBudgetCommand.equals(1));

        // null -> returns false
        assertFalse(addSchoolBudgetCommand.equals(null));

        // different budget -> returns false
        assertFalse(addSchoolBudgetCommand.equals(addOutsideSchoolBudgetCommand));
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
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBudgetWithName(Description targetDescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Budget getPrimaryBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void switchBudgetTo(Description targetDescription) {
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
        final ArrayList<Budget> budgetsAdded;
        final Stack<ModelStub> pastModels;

        public ModelStubAcceptingBudgetAdded() {
            budgetsAdded = new ArrayList<>();
            pastModels = new Stack<>();
        }

        public ModelStubAcceptingBudgetAdded(ModelStubAcceptingBudgetAdded model) {
            budgetsAdded = new ArrayList<>(model.budgetsAdded);
            pastModels = (Stack<ModelStub>) model.pastModels.clone();
        }

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
        public void setPrimary(Budget budget) {
            requireNonNull(budget);
            budget.setPrimary();
        }

        @Override
        public void addToHistory() {
            pastModels.push(new ModelStubAcceptingBudgetAdded(this));
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

            if (!(obj instanceof ModelStubAcceptingBudgetAdded)) {
                return false;
            }

            ModelStubAcceptingBudgetAdded other = (ModelStubAcceptingBudgetAdded) obj;
            return budgetsAdded.equals(other.budgetsAdded)
                    && pastModels.equals(other.pastModels);
        }
    }
}

