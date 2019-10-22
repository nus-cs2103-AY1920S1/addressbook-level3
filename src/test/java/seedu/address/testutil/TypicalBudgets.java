package seedu.address.testutil;

import seedu.address.model.budget.Budget;
import seedu.address.model.budget.Percentage;

public class TypicalBudgets {

    private TypicalBudgets() {}

    public static final String SCHOOL_BUDGET_STRING = "|| Description: School related expenses " +
            "Amount: 300 Period: month Start date: 01-10-2019 End date: 01-11-2019 ||";

    public static final Budget SCHOOL = new BudgetBuilder()
            .withDescription("School related expenses")
            .withAmount("300")
            .withStartDate("01-10-2019")
            .withPeriod("month")
            .withEndDate("01-11-2019")
            .withIsPrimary(true)
            .withProportionUsed(new Percentage(35))
            .build();

    public static final Budget OUTSIDE_SCHOOL = new BudgetBuilder()
            .withDescription("Outside school expenses")
            .withAmount("200")
            .withStartDate("01-10-2019")
            .withPeriod("month")
            .withEndDate("01-11-2019")
            .withIsPrimary(false)
            .withProportionUsed(new Percentage(50))
            .build();
}
