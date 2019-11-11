package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ViewState;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.UniqueBudgetList;
import seedu.address.testutil.BudgetBuilder;

public class AddBudgetCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddBudgetCommand(null));
    }

    @Test
    public void execute_budgetAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBudgetAdded modelStub = new ModelStubAcceptingBudgetAdded();
        Budget validBudget = new BudgetBuilder().build();

        CommandResult commandResult = new AddBudgetCommand(validBudget).execute(modelStub, commandHistory);

        assertEquals(String.format(AddBudgetCommand.MESSAGE_SUCCESS, validBudget), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBudget), modelStub.budgetsAdded);
    }

    @Test
    public void execute_duplicateBudget_throwsCommandException() {
        Budget validBudget = new BudgetBuilder().build();
        AddBudgetCommand addBudgetCommand = new AddBudgetCommand(validBudget);
        ModelStub modelStub = new ModelStubWithBudget(validBudget);

        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_DUPLICATE_BUDGET, ()
            -> addBudgetCommand.execute(modelStub, commandHistory));
    }

    @Test
    public void execute_budgetClash_throwsCommandException() {
        Budget validBudget = new BudgetBuilder().build();
        ModelStub modelStub = new ModelStubWithBudget(validBudget);

        // same end date but different start date
        Budget otherValidBudget = new BudgetBuilder().withStartDate(BudgetBuilder.VALID_DATE_BEFORE_DEFAULT_START_DATE)
            .build();
        AddBudgetCommand addBudgetCommand1 = new AddBudgetCommand(otherValidBudget);
        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_BUDGET_CLASH, ()
            -> addBudgetCommand1.execute(modelStub, commandHistory));

        // same start date but different end date
        otherValidBudget = new BudgetBuilder().withEndDate(BudgetBuilder.VALID_DATE_AFTER_DEFAULT_END_DATE)
            .build();
        AddBudgetCommand addBudgetCommand2 = new AddBudgetCommand(otherValidBudget);
        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_BUDGET_CLASH, ()
            -> addBudgetCommand2.execute(modelStub, commandHistory));

        // both start date and end date are within the period of another budget
        otherValidBudget = new BudgetBuilder()
            .withStartDate(BudgetBuilder.VALID_DATE_BETWEEN_DEFAULT_START_AND_END_DATES)
            .withEndDate(BudgetBuilder.VALID_DATE_BETWEEN_DEFAULT_START_AND_END_DATES)
            .build();
        AddBudgetCommand addBudgetCommand3 = new AddBudgetCommand(otherValidBudget);
        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_BUDGET_CLASH, ()
            -> addBudgetCommand3.execute(modelStub, commandHistory));

        // start date before period of another budget and end date after period of that same budget, overlapping
        otherValidBudget = new BudgetBuilder()
            .withStartDate(BudgetBuilder.VALID_DATE_BEFORE_DEFAULT_START_DATE)
            .withEndDate(BudgetBuilder.VALID_DATE_AFTER_DEFAULT_END_DATE)
            .build();
        AddBudgetCommand addBudgetCommand4 = new AddBudgetCommand(otherValidBudget);
        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_BUDGET_CLASH, ()
            -> addBudgetCommand4.execute(modelStub, commandHistory));

        // start date fall within period of another budget
        otherValidBudget = new BudgetBuilder()
            .withStartDate(BudgetBuilder.VALID_DATE_BETWEEN_DEFAULT_START_AND_END_DATES)
            .withEndDate(BudgetBuilder.VALID_DATE_AFTER_DEFAULT_END_DATE)
            .build();
        AddBudgetCommand addBudgetCommand5 = new AddBudgetCommand(otherValidBudget);
        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_BUDGET_CLASH, ()
            -> addBudgetCommand5.execute(modelStub, commandHistory));

        // end date fall within period of another budget
        otherValidBudget = new BudgetBuilder()
            .withStartDate(BudgetBuilder.VALID_DATE_BEFORE_DEFAULT_START_DATE)
            .withEndDate(BudgetBuilder.VALID_DATE_BETWEEN_DEFAULT_START_AND_END_DATES)
            .build();
        AddBudgetCommand addBudgetCommand6 = new AddBudgetCommand(otherValidBudget);
        assertThrows(CommandException.class, AddBudgetCommand.MESSAGE_BUDGET_CLASH, ()
            -> addBudgetCommand6.execute(modelStub, commandHistory));
    }

    @Test
    public void equals() {
        Budget shopping = new BudgetBuilder().withName("Shopping").build();
        Budget food = new BudgetBuilder().withName("Food").build();
        AddBudgetCommand addShoppingCommand = new AddBudgetCommand(shopping);
        AddBudgetCommand addFoodCommand = new AddBudgetCommand(food);

        // same object -> returns true
        assertTrue(addShoppingCommand.equals(addShoppingCommand));

        // same values -> returns true
        AddBudgetCommand addShoppingCommandCopy = new AddBudgetCommand(shopping);
        assertTrue(addShoppingCommand.equals(addShoppingCommandCopy));

        // different types -> returns false
        assertFalse(addShoppingCommand.equals(1));

        // null -> returns false
        assertFalse(addShoppingCommand.equals(null));

        // different budget -> returns false
        assertFalse(addShoppingCommand.equals(addFoodCommand));
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
        public boolean hasBudgetPeriodClash(Budget b) {
            return budget.isDateWithinBudgetPeriod(b.getStartDate())
                || budget.isDateWithinBudgetPeriod(b.getEndDate())
                || budget.doesOtherBudgetOverlap(b);
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
        private ViewState viewState = ViewState.BUDGETLIST;

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
        public boolean hasBudgetPeriodClash(Budget budget) {
            for (Budget b : budgetsAdded) {
                if (b.isDateWithinBudgetPeriod(budget.getStartDate())
                    || b.isDateWithinBudgetPeriod(budget.getEndDate())
                    || b.doesOtherBudgetOverlap(budget)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void setViewState(ViewState state) {
            viewState = state;
        }

        @Override
        public ObservableList<Budget> getFilteredBudgetList() {
            return new FilteredList<Budget>(new UniqueBudgetList().asUnmodifiableObservableList());
        }
    }
}
