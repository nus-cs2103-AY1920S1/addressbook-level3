package seedu.moolah.logic.commands.expense;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_MENU_CATEGORY_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_MENU_DESCRIPTION_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_MENU_PRICE_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_MENU_TIMESTAMP_CHICKEN;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.moolah.commons.core.GuiSettings;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.ReadOnlyUserPrefs;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.menu.MenuItem;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ReadOnlyModelHistory;
import seedu.moolah.model.statistics.Statistics;
import seedu.moolah.testutil.ExpenseBuilder;

public class AddMenuExpenseCommandTest {

    private MenuItem chickenRice = new MenuItem(
            new Description(VALID_EXPENSE_MENU_DESCRIPTION_CHICKEN),
            new Price(VALID_EXPENSE_MENU_PRICE_CHICKEN));
    private Expense chickenRiceExpense = new ExpenseBuilder()
            .withDescription(VALID_EXPENSE_MENU_DESCRIPTION_CHICKEN)
            .withPrice(VALID_EXPENSE_MENU_PRICE_CHICKEN)
            .withCategory(VALID_EXPENSE_MENU_CATEGORY_CHICKEN)
            .withTimestamp(VALID_EXPENSE_MENU_TIMESTAMP_CHICKEN)
            .build();

    @Test
    public void constructorWithTimestamp_nullTimestamp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddMenuExpenseCommand(chickenRice, null));
    }

    @Test
    public void run_duplicateExpense_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithExpense(chickenRiceExpense);

        assertThrows(CommandException.class,
                AddMenuExpenseCommand.MESSAGE_DUPLICATE_EXPENSE, () ->
                        new AddMenuExpenseCommand(chickenRiceExpense).run(modelStub));
    }

    @Test
    public void run_expenseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExpenseAdded modelStub = new ModelStubAcceptingExpenseAdded();

        List<Expense> expectedExpensesAdded = Collections.singletonList(chickenRiceExpense);

        CommandResult commandResult = new AddMenuExpenseCommand(chickenRiceExpense).run(modelStub);

        assertEquals(String.format(AddExpenseCommand.MESSAGE_SUCCESS, chickenRiceExpense),
                commandResult.getFeedbackToUser());
        assertEquals(expectedExpensesAdded, modelStub.expensesAdded);
    }

    @Test
    public void equals() {
        AddMenuExpenseCommand addChickenRice = new AddMenuExpenseCommand(chickenRice);

        // Same object
        assertEquals(addChickenRice, addChickenRice);

        // Different object types
        assertNotEquals(addChickenRice, new Object());

        // Different values
        assertNotEquals(addChickenRice, new AddMenuExpenseCommand(
                new MenuItem(new Description("pasta"), new Price("7.0"))
        ));

        // Same values
        assertEquals(new AddMenuExpenseCommand(chickenRiceExpense),
                new AddMenuExpenseCommand(chickenRiceExpense));
    }

    /**
     * A stub of {@code Model} that has all of its methods throw exceptions.
     */
    private class ModelStub implements Model {

        @Override
        public void setBudget(Budget target, Budget editedBudget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void changePrimaryBudgetWindow(Timestamp pastDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBudgetList(Predicate<? super Budget> budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Predicate<? super Budget> getFilteredBudgetPredicate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearBudgets() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBudgetWithName(Description description) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(Model model) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Model copy() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void applyChanges(ModelChanges changes) {
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
        public Optional<String> rollback() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canMigrate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<String> migrate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commit(String changeMessage, Model prevModel) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToPastChanges(ModelChanges changes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addToFutureChanges(ModelChanges changes) {
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
        public boolean removeAliasWithName(String aliasName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean aliasWithNameExists(String aliasName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getMooLahFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMooLahFilePath(Path mooLahFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void notifyAboutTranspiredEvents(List<Event> events) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExpense(Expense expense) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMooLah(ReadOnlyMooLah newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyMooLah getMooLah() {
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
        public boolean hasBudgetWithName(Description targetDescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Budget getPrimaryBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPrimaryBudget() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void switchBudgetTo(Description targetDescription) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBudget(Budget budget) {
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
        public void setEvent(Event eventToEdit, Event editedEvent) {
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
        public Statistics getStatistics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatistics(Statistics statistics) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleAlreadyTranspiredEvents() {
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
     * A stub of {@code Model} that accepts the addition of expenses.
     */
    private class ModelStubAcceptingExpenseAdded extends ModelStub {
        final ArrayList<Expense> expensesAdded;

        public ModelStubAcceptingExpenseAdded() {
            expensesAdded = new ArrayList<>();
        }

        public ModelStubAcceptingExpenseAdded(ModelStubAcceptingExpenseAdded model) {
            expensesAdded = new ArrayList<>(model.expensesAdded);
        }

        @Override
        public Model copy() {
            return new ModelStubAcceptingExpenseAdded(this);
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
        public void commit(String changeMessage, Model prevModel) {
            // Should not do anything for isolated testing.
        }
    }

}
