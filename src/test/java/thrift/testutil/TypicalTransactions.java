package thrift.testutil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import thrift.commons.util.StreamUtils;
import thrift.model.Thrift;
import thrift.model.transaction.Budget;
import thrift.model.transaction.BudgetList;
import thrift.model.transaction.BudgetValue;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final Expense LAKSA = new ExpenseBuilder().withDescription("Laksa").withValue("3.50")
            .withDate("13/03/1937").withTags("Lunch").build();
    public static final Expense PENANG_LAKSA = new ExpenseBuilder().withDescription("Penang Laksa1").withValue("5")
            .withDate("11/10/2010").withRemark("One of the best").withTags("Brunch").build();
    public static final Income BURSARY = new IncomeBuilder().withDescription("Bursary").withValue("500")
            .withDate("13/11/2011").withTags("Award").build();

    public static final Calendar OCT_2019_MONTH = setCalendar("10/2019");
    public static final Budget OCT_BUDGET = new Budget(OCT_2019_MONTH, new BudgetValue("1000"));

    private TypicalTransactions() {} // prevents instantiation

    /**
     * Returns an {@code Thrift} with all the typical transactions.
     */
    public static Thrift getTypicalThrift() {
        Thrift thrift = new Thrift();
        for (Transaction transaction : getTypicalTransaction()) {
            thrift.addTransaction(transaction);
        }
        for (Budget budget : getTypicalBudget()) {
            thrift.setBudget(budget);
        }
        return thrift;
    }

    public static List<Transaction> getTypicalTransaction() {
        return new ArrayList<>(Arrays.asList(LAKSA, BURSARY, PENANG_LAKSA));
    }

    public static List<Budget> getTypicalBudget() {
        try {
            OCT_2019_MONTH.setTime(Budget.BUDGET_DATE_FORMAT.parse("10/2019"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BudgetList budgets = new BudgetList();
        budgets.setBudget(OCT_BUDGET);
        return StreamUtils.asStream(budgets.iterator()).collect(Collectors.toList());
    }

    /**
     * Method to create and return a {@code Calendar} object with the given month String in the format MM/yyyy.
     */
    private static Calendar setCalendar(String month) {
        Calendar t = Calendar.getInstance();
        try {
            t.setTime(Budget.BUDGET_DATE_FORMAT.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return t;
    }
}
