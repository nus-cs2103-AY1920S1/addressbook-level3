package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.model.BankAccount;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.stubs.ModelStub;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;
import seedu.address.testutil.BudgetBuilder;

public class SetCommandTest {

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetCommand(null));
    }

    @Test
    public void executeBudgetAcceptedByModeladdSuccessful() throws Exception {
        ModelStubAcceptingBudgetAdded modelStub = new ModelStubAcceptingBudgetAdded();
        Budget validBudget = new BudgetBuilder().build();

        SetCommand setCommand = new SetCommand(validBudget);
        CommandResult commandResult = setCommand.execute(modelStub);

        assertEquals(String.format(SetCommand.MESSAGE_SUCCESS, validBudget), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBudget), modelStub.budgetsAdded);
    }

    @Test
    public void equals() {
        Budget firstBudget = new BudgetBuilder()
            .withCategories("Food")
            .withAmount("100")
            .withDate("10102019")
            .build();
        Budget secondBudget = new BudgetBuilder()
            .withCategories("Drinks")
            .withAmount("80")
            .withDate("10102019")
            .build();
        SetCommand setFirstCommand = new SetCommand(firstBudget);
        SetCommand setSecondCommand = new SetCommand(secondBudget);

        // same object -> returns true
        assertTrue(setFirstCommand.equals(setFirstCommand));

        // same values -> returns false
        SetCommand setFirstCommandCopy = new SetCommand(firstBudget);
        assertFalse(setFirstCommand.equals(setFirstCommandCopy));

        // different types -> returns false
        assertFalse(setFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
    }

    /**
     * A Model stub that contains a single transaction.
     */
    private class ModelStubWithBudget extends ModelStub {
        private final Budget budget;

        ModelStubWithBudget(Budget budget) {
            requireNonNull(budget);
            this.budget = budget;
        }

        public boolean has(Budget budget) {
            requireNonNull(budget);
            return this.budget.isSameBudget(budget);
        }

        @Override
        public void updateProjectionsAfterAdd(Budget budget) {

        }

        @Override
        public void updateProjectionsAfterDelete(Budget budget) {

        }
    }

    /**
     * A Model stub that always accept the transaction being added.
     */
    private class ModelStubAcceptingBudgetAdded extends ModelStub {
        final ArrayList<Budget> budgetsAdded = new ArrayList<>();

        public boolean has(Budget budget) {
            requireNonNull(budget);
            return budgetsAdded.stream().anyMatch(budget::isSameBudget);
        }

        @Override
        public void add(LedgerOperation operation) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void add(Budget budget) {
            requireNonNull(budget);
            budgetsAdded.add(budget);
        }

        @Override
        public void updateProjectionsAfterAdd(Budget budget) {

        }

        @Override
        public void updateProjectionsAfterDelete(Budget budget) {

        }

        @Override
        public void commitUserState() {
        }

        @Override
        public ReadOnlyBankAccount getBankAccount() {
            return new BankAccount();
        }
    }

}


