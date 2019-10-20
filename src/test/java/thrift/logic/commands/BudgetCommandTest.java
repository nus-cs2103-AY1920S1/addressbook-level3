package thrift.logic.commands;

import static thrift.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import thrift.model.transaction.Budget;

public class BudgetCommandTest {

    @Test
    public void constructor_nullBudget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new BudgetCommand(new Budget(null, null)));
    }
}
