package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;

/**
 * A utility class containing a list of {@code Budget} objects to be used in tests.
 */
public class TypicalBudgets {

    public static final Budget KOREA = new BudgetBuilder().withName("Korea Trip").withAmount("3000")
            .withStartDate("5/1/2019").withCurrency("KRW").withEndDate("9/1/2019").build();
    public static final Budget JAPAN = new BudgetBuilder().withName("Japan Travel").withAmount("5000")
            .withStartDate("14/6/2019").withCurrency("JPY").withEndDate("23/6/2019").build();
    public static final Budget FRANCE = new BudgetBuilder().withName("France Fun").withAmount("8000")
            .withStartDate("23/4/2019").withCurrency("EUR").withEndDate("11/5/2019").build();

    /**
     * Returns an {@code BudgetList} with all the typical budgets.
     */
    public static BudgetList getTypicalBudgetList() {
        BudgetList ab = new BudgetList();
        for (Budget budget : getTypicalBudgets()) {
            ab.addBudget(budget);
        }
        return ab;
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(KOREA, JAPAN, FRANCE));
    }
}
