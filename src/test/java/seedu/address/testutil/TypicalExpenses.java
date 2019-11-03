package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_TRANSPORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.MooLah;
import seedu.address.model.expense.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final Expense ANNIVERSARY = new ExpenseBuilder()
            .withDescription("Alices Birthday")
            .withPrice("20")
            .withCategory("Entertainment")
            .withTimestamp("01-12-2019 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000001")
            .build();
    public static final Expense BUSAN_TRIP = new ExpenseBuilder()
            .withDescription("Busan Trip")
            .withPrice("1300")
            .withCategory("Transport")
            .withTimestamp("02-12 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000002")
            .build();
    public static final Expense CHICKEN_RICE = new ExpenseBuilder()
            .withDescription("Chicken Rice extra Chicken")
            .withPrice("3.50")
            .withCategory("Food")
            .withTimestamp("03-12-2019 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000003")
            .build();
    public static final Expense DRINKS = new ExpenseBuilder()
            .withDescription("Whiskey and Coke")
            .withPrice("50")
            .withCategory("Food")
            .withTimestamp("04-12 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000004")
            .build();
    public static final Expense ENTERTAINMENT = new ExpenseBuilder()
            .withDescription("Marvel Movie Marathon")
            .withPrice("75")
            .withCategory("Entertainment")
            .withTimestamp("05-12 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000005")
            .build();
    public static final Expense FASHION = new ExpenseBuilder()
            .withDescription("Clothes for the New Year")
            .withPrice("88.88")
            .withCategory("Shopping")
            .withTimestamp("06-12 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000006")
            .build();
    public static final Expense GROCERIES = new ExpenseBuilder()
            .withDescription("Groceries for September meal preps")
            .withPrice("125.35")
            .withCategory("Utilities")
            .withTimestamp("07-12 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000007")
            .build();

    // Manually added
    public static final Expense HALLOWEEN = new ExpenseBuilder()
            .withDescription("HalloweenHorrorNight")
            .withPrice("60")
            .withCategory("Entertainment")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000008").build();
    public static final Expense INVESTMENT = new ExpenseBuilder()
            .withDescription("Property investment")
            .withPrice("1200000")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000009").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense CHICKEN = new ExpenseBuilder()
            .withDescription(VALID_DESCRIPTION_CHICKEN)
            .withPrice(VALID_PRICE_CHICKEN)
            .withCategory(VALID_CATEGORY_TRANSPORT)
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-00000000000a").build();
    public static final Expense TRANSPORT = new ExpenseBuilder()
            .withDescription(VALID_DESCRIPTION_TRANSPORT)
            .withPrice(VALID_PRICE_TRANSPORT)
            .withCategory(VALID_CATEGORY_FOOD)
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-00000000000b").build();

    public static final String primaryBudgetName = "Default Budget";

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExpenses() {} // prevents instantiation

    /**
     * Returns an {@code MooLah} with all the typical expenses.
     */
    public static MooLah getTypicalMooLah() {
        MooLah ab = new MooLah();
        for (Expense expense : getTypicalExpenses()) {
            ab.addExpense(expense);
        }
        ab.setPrimaryBudget(primaryBudgetName);
        return ab;
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(
                ANNIVERSARY, BUSAN_TRIP, CHICKEN_RICE, DRINKS, ENTERTAINMENT, FASHION, GROCERIES));
    }
}
