package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UserState;
import seedu.address.model.transaction.Budget;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
public class TypicalBudgets {

    public static final Budget BUDGET_ONE = new BudgetBuilder()
        .withAmount("100")
        .withDate("31122025")
        .withCategories("food")
        .build();

    public static final Budget BUDGET_TWO = new BudgetBuilder()
        .withAmount("10")
        .withDate("31012025")
        .withCategories("drink")
        .build();

    public static final Budget BUDGET_THREE = new BudgetBuilder()
        .withAmount("1000")
        .withDate("31122025")
        .build();

    private TypicalBudgets() {
    } //prevent instantiation

    /**
     * Returns a {@code UserState} with all the typical budgets.
     */
    public static UserState getTypicalUserState() {
        UserState ba = new UserState();
        for (Budget op : getTypicalBudgets()) {
            ba.add(op);
        }
        return ba;
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(BUDGET_ONE, BUDGET_TWO, BUDGET_THREE));
    }
}
