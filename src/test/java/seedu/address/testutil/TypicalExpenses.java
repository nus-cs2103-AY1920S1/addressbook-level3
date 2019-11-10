package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENCY_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENCY_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RUM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_VODKA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALCOHOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRINKS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ExpenseList;
import seedu.address.model.exchangedata.ExchangeData;
import seedu.address.model.expense.Expense;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final Expense TRANSPORT = new ExpenseBuilder().withName("Bus")
        .withCurrency("SGD").withAmount("3.20").withDate("13/10/2019")
        .withTag("school").build();
    public static final Expense FOOD = new ExpenseBuilder().withName("Lunch")
        .withCurrency("SGD").withAmount("4.50")
        .withDate("14/12/2019").withTag("school").build();
    public static final Expense SHOPPING = new ExpenseBuilder().withName("Adidas shoe")
        .withCurrency("SGD").withAmount("120.00")
        .withDate("24/12/2019").withTag("").build();
    public static final Expense GROCERIES = new ExpenseBuilder().withName("Groceries")
        .withCurrency("SGD").withAmount("17.80")
        .withDate("1/2/2019").withTag("groceries").build();
    public static final Expense VALENTINES = new ExpenseBuilder().withName("Chocolates")
        .withCurrency("SGD").withAmount("12.30")
        .withDate("13/2/2019").withTag("").build();
    public static final Expense CHRISTMAS = new ExpenseBuilder().withName("Socks")
        .withCurrency("SGD").withAmount("5.00")
        .withDate("24/12/2019").withTag("").build();
    public static final Expense NEWYEAR = new ExpenseBuilder().withName("Cheese")
        .withCurrency("SGD").withAmount("10.00")
        .withDate("31/12/2019").withTag("").build();

    // Manually added
    public static final Expense FURNITURE = new ExpenseBuilder().withName("Sofa")
        .withCurrency("SGD").withAmount("700.00")
        .withDate("6/9/2019").build();
    public static final Expense SNACKS = new ExpenseBuilder().withName("Chips")
        .withCurrency("SGD").withAmount("1.20")
        .withDate("2/3/2019").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense VODKA = new ExpenseBuilder().withName(VALID_NAME_VODKA)
        .withCurrency(VALID_CURRENCY_VODKA).withAmount(VALID_AMOUNT_VODKA)
        .withDate(VALID_DATE_VODKA).withTag(VALID_TAG_ALCOHOL).build();
    public static final Expense RUM = new ExpenseBuilder().withName(VALID_NAME_RUM)
        .withCurrency(VALID_CURRENCY_RUM).withAmount(VALID_AMOUNT_RUM)
        .withDate(VALID_DATE_RUM).withTag(VALID_TAG_DRINKS)
        .build();

    public static final String KEYWORD_MATCHING_SOFA = "Sofa"; // A keyword that matches SOFA

    private TypicalExpenses() {
    } // prevents instantiation

    /**
     * Returns an {@code ExpenseList} with all the typical expenses.
     */
    public static ExpenseList getTypicalExpenseList() {
        ExpenseList ab = new ExpenseList();
        for (Expense expense : getTypicalExpenses()) {
            ab.addExpense(expense);
        }
        return ab;
    }

    public static ExchangeData getTypicalExchangeData() {
        return SampleDataUtil.getSampleExchangeData();
    }


    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(TRANSPORT, FOOD, SHOPPING, GROCERIES, VALENTINES, CHRISTMAS, NEWYEAR));
    }
}
