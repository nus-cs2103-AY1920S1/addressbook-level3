package seedu.address.testutil;

import seedu.address.model.budget.Budget;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
public class TypicalBudgets {

    public static final String SCHOOL_BUDGET_STRING_ONE = "|| Description: School related expenses "
            + "Amount: 300 Period: month Start date: 15 Nov 2019, 12:00:00 PM End date: 14 Dec 2019, 12:00:00 PM ||";

    public static final String SCHOOL_BUDGET_STRING_TWO = "|| Description: School related expenses "
            + "Amount: 300 Period: month Start date: Nov 15, 2019, 12:00:00 PM End date: Dec 14, 2019, 12:00:00 PM ||";

    public static final Budget SCHOOL = new BudgetBuilder()
            .withDescription("School related expenses")
            .withAmount("300")
            .withStartDate("15-11-2019 noon")
            .withPeriod("month")
            .withEndDate("14-12-2019 noon")
            .withIsPrimary(true)
            .build();

    public static final Budget OUTSIDE_SCHOOL = new BudgetBuilder()
            .withDescription("Outside school expenses")
            .withAmount("200")
            .withStartDate("05-09-2019 noon")
            .withPeriod("month")
            .withEndDate("04-10-2019 noon")
            .withIsPrimary(false)
            .build();

    private TypicalBudgets() {}
}
