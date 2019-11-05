package seedu.address.testutil;

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

}
