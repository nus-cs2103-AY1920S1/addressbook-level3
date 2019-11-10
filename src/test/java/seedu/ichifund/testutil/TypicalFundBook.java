package seedu.ichifund.testutil;

import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_AMOUNT_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_CATEGORY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DAY_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_MONTH_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_TRANSACTION_TYPE_BUS;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_YEAR_ALLOWANCE;
import static seedu.ichifund.logic.commands.CommandTestUtil.VALID_YEAR_BUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ichifund.model.FundBook;
import seedu.ichifund.model.budget.Budget;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.repeater.Repeater;
import seedu.ichifund.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code FundBook} related objects to be used in tests.
 */
public class TypicalFundBook {

    // Manually added
    public static final Transaction TRANSACTION_CAT_FOOD = new TransactionBuilder()
            .withDescription("Cat Food").withAmount("3.00")
            .withDate(new DateBuilder().withDay("25").withMonth("2").withYear("2005").build())
            .withCategory("Pet")
            .withTransactionType("exp").build();
    public static final Transaction TRANSACTION_DARK_CHOCOLATE = new TransactionBuilder()
            .withDescription("Dark Chocolate").withAmount("2.50")
            .withDate(new DateBuilder().withDay("17").withMonth("8").withYear("2006").build())
            .withCategory("Food")
            .withTransactionType("exp").build();

    public static final Date DATE_ALLOWANCE = new DateBuilder().withDay(VALID_DAY_ALLOWANCE)
            .withMonth(VALID_MONTH_ALLOWANCE).withYear(VALID_YEAR_ALLOWANCE).build();
    public static final Date DATE_BUS = new DateBuilder().withDay(VALID_DAY_BUS)
            .withMonth(VALID_MONTH_BUS).withYear(VALID_YEAR_BUS).build();

    public static final Transaction TRANSACTION_ALLOWANCE = new TransactionBuilder()
            .withDescription(VALID_DESCRIPTION_ALLOWANCE).withAmount(VALID_AMOUNT_ALLOWANCE)
            .withDate(DATE_ALLOWANCE).withCategory(VALID_CATEGORY_ALLOWANCE)
            .withTransactionType(VALID_TRANSACTION_TYPE_ALLOWANCE).build();
    public static final Transaction TRANSACTION_BUS = new TransactionBuilder()
            .withDescription(VALID_DESCRIPTION_BUS).withAmount(VALID_AMOUNT_BUS)
            .withDate(DATE_BUS).withCategory(VALID_CATEGORY_BUS)
            .withTransactionType(VALID_TRANSACTION_TYPE_BUS).build();

    public static final String DEFAULT_UNIQUE_ID = "";
    public static final String DEFAULT_DESCRIPTION = "Phone bills";
    public static final String DEFAULT_AMOUNT = "42.06";
    public static final String DEFAULT_CATEGORY = "utilities";
    public static final String DEFAULT_TRANSACTION_TYPE = "exp";
    public static final String DEFAULT_MONTH_START_OFFSET = "5";
    public static final String DEFAULT_MONTH_END_OFFSET = "";
    public static final String DEFAULT_START_MONTH = "1";
    public static final String DEFAULT_START_YEAR = "2019";
    public static final String DEFAULT_END_MONTH = "12";
    public static final String DEFAULT_END_YEAR = "2019";


    public static final Repeater REPEATER_PHONE_BILLS = new RepeaterBuilder()
        .withUniqueId("").withDescription("Phone bills").withAmount("42.06").withCategory("utilities")
        .withTransactionType("exp").withMonthStartOffset("5").withMonthEndOffset("-1")
        .withStartDate(new DateBuilder().withDay("1").withMonth("1").withYear("2019").build())
        .withEndDate(new DateBuilder().withDay("31").withMonth("12").withYear("2019").build())
        .build();
    public static final Repeater REPEATER_SALARY = new RepeaterBuilder()
        .withUniqueId("").withDescription("Salary").withAmount("8129.64").withCategory("pay")
        .withTransactionType("in").withMonthStartOffset("-1").withMonthEndOffset("1")
        .withStartDate(new DateBuilder().withDay("1").withMonth("1").withYear("2019").build())
        .withEndDate(new DateBuilder().withDay("31").withMonth("12").withYear("2019").build())
        .build();

    public static final Budget BUDGET_OVERALL = new BudgetBuilder().withDescription("Overall budget")
            .withAmount("1337").withMonthAndYear("12", "2012").build();
    public static final Budget BUDGET_FOOD = new BudgetBuilder().withDescription("Budget for food")
            .withAmount("420.69").withMonthAndYear("12", "2012")
            .withCategory("food").build();
    public static final Budget BUDGET_ANIME = new BudgetBuilder().withDescription("Budget for anime")
            .withAmount("300.00").withMonthAndYear("12", "2012")
            .withCategory("entertainment").build();

    private TypicalFundBook() {} // prevents instantiation

    /**
     * Returns an {@code FundBok} with all the typical persons.
     */
    public static FundBook getTypicalFundBook() {
        FundBook fb = new FundBook();
        for (Transaction transaction : getTypicalTransactions()) {
            fb.addTransaction(transaction);
        }
        for (Budget budget : getTypicalBudgets()) {
            fb.addBudget(budget);
        }
        return fb;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(TRANSACTION_ALLOWANCE, TRANSACTION_BUS));
    }

    public static List<Repeater> getTypicalRepeaters() {
        return new ArrayList<>(Arrays.asList(REPEATER_PHONE_BILLS, REPEATER_SALARY));
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(BUDGET_OVERALL, BUDGET_FOOD, BUDGET_ANIME));
    }
}
