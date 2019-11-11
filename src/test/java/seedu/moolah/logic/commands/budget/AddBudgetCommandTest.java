package seedu.moolah.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.OUTSIDE_SCHOOL;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL;

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
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ReadOnlyModelHistory;
import seedu.moolah.model.statistics.Statistics;

public class AddBudgetCommandTest {

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBudgetCommand(null));
    }

    @Test
    public void run_budgetAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBudgetAdded modelStub = new ModelStubAcceptingBudgetAdded();
        Budget validBudget = SCHOOL;

        List<Budget> expectedBudgetsAdded = Collections.singletonList(validBudget);

        CommandResult commandResult = new AddBudgetCommand(validBudget).run(modelStub);

        assertEquals(String.format(AddBudgetCommand.MESSAGE_SUCCESS, validBudget), commandResult.getFeedbackToUser());
        assertEquals(expectedBudgetsAdded, modelStub.budgetsAdded);
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
        assertEquals(addSchoolBudgetCommand, addSchoolBudgetCommand);

        // same values -> returns true
        AddBudgetCommand addSchoolBudgetCommandCopy = new AddBudgetCommand(SCHOOL);
        assertEquals(addSchoolBudgetCommand, addSchoolBudgetCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addSchoolBudgetCommand);

        // null -> returns false
        assertNotEquals(null, addSchoolBudgetCommand);

        // different budget -> returns false
        assertNotEquals(addSchoolBudgetCommand, addOutsideSchoolBudgetCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
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
        public void deleteBudget(Budget budget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void notifyAboutTranspiredEvents(List<Event> events) {
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

        public ModelStubAcceptingBudgetAdded() {
            budgetsAdded = new ArrayList<>();
        }

        public ModelStubAcceptingBudgetAdded(ModelStubAcceptingBudgetAdded model) {
            budgetsAdded = new ArrayList<>(model.budgetsAdded);
        }

        @Override
        public Model copy() {
            return new ModelStubAcceptingBudgetAdded(this);
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
        public void commit(String changeMessage, Model prevModel) {
            // Should not do anything for isolated testing.
        }

    }

}

