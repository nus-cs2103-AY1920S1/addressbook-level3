package seedu.ichifund.logic.commands.budget;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.ReadOnlyFundBook;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.testutil.BudgetBuilder;
import seedu.ichifund.testutil.ModelStub;

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
