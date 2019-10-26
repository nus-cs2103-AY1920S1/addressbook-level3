package seedu.address.testutil;

import seedu.address.model.budget.Budget;
import seedu.address.model.budget.Percentage;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
public class TypicalBudgets {

    public static final String SCHOOL_BUDGET_STRING_ONE = "|| Description: School related expenses "
            + "Amount: 300 Period: month Start date: 1 Oct 2019, 12:00:00 PM End date: 1 Nov 2019, 12:00:00 PM ||";

    public static final String SCHOOL_BUDGET_STRING_TWO = "|| Description: School related expenses "
            + "Amount: 300 Period: month Start date: Oct 1, 2019, 12:00:00 PM End date: Nov 1, 2019, 12:00:00 PM ||";

    public static final Budget SCHOOL = new BudgetBuilder()
            .withDescription("School related expenses")
            .withAmount("300")
            .withStartDate("01-10-2019 noon")
            .withPeriod("month")
            .withEndDate("01-11-2019 noon")
            .withIsPrimary(true)
            .withProportionUsed(new Percentage(35))
            .build();

    public static final Budget OUTSIDE_SCHOOL = new BudgetBuilder()
            .withDescription("Outside school expenses")
            .withAmount("200")
            .withStartDate("05-09-2019 noon")
            .withPeriod("month")
            .withEndDate("05-10-2019 noon")
            .withIsPrimary(false)
            .withProportionUsed(new Percentage(50))
            .build();

    private TypicalBudgets() {}
}
