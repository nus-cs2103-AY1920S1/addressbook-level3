package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHICKEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_TRANSPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CLAIMABLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DISCOUNTED;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.expense.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final Expense ANNIVERSARY = new ExpenseBuilder()
            .withDescription("Alices Birthday")
            .withPrice("20")
            .withTags("AnniversaryAndHoliday")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000001").build();
    public static final Expense BUSAN_TRIP = new ExpenseBuilder()
            .withDescription("Busan Trip")
            .withPrice("1300")
            .withTags("Travel", "Korea", "Food", "Transport")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000002").build();
    public static final Expense CHICKEN_RICE = new ExpenseBuilder()
            .withDescription("Chicken Rice extra Chicken")
            .withPrice("3.50")
            .withTags("nusDeck", "Favourite")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000003").build();
    public static final Expense DRINKS = new ExpenseBuilder()
            .withDescription("Whiskey and Coke")
            .withPrice("50")
            .withTags("Party", "AnniversaryAndHoliday")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000004").build();
    public static final Expense ENTERTAINMENT = new ExpenseBuilder()
            .withDescription("Marvel Movie Marathon")
            .withPrice("75")
            .withTags("Entertainment")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000005").build();
    public static final Expense FASHION = new ExpenseBuilder()
            .withDescription("Clothes for the New Year")
            .withPrice("88.88")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000006").build();
    public static final Expense GROCERIES = new ExpenseBuilder()
            .withDescription("Groceries for September meal preps")
            .withPrice("125.35")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000007").build();

    // Manually added
    public static final Expense HALLOWEEN = new ExpenseBuilder()
            .withDescription("HalloweenHorrorNight")
            .withPrice("60")
            .withTags("AnniversaryAndHoliday")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000008").build();
    public static final Expense INVESTMENT = new ExpenseBuilder()
            .withDescription("Property investment")
            .withPrice("1200000")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000009").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense CHICKEN = new ExpenseBuilder()
            .withDescription(VALID_DESCRIPTION_CHICKEN)
            .withPrice(VALID_PRICE_CHICKEN)
            .withTags(VALID_TAG_DISCOUNTED)
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-00000000000a").build();
    public static final Expense TRANSPORT = new ExpenseBuilder()
            .withDescription(VALID_DESCRIPTION_TRANSPORT)
            .withPrice(VALID_PRICE_TRANSPORT)
            .withTags(VALID_TAG_CLAIMABLE, VALID_TAG_DISCOUNTED)
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-00000000000b").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExpenses() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical expenses.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Expense expense : getTypicalExpenses()) {
            ab.addExpense(expense);
        }
        return ab;
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(
                ANNIVERSARY, BUSAN_TRIP, CHICKEN_RICE, DRINKS, ENTERTAINMENT, FASHION, GROCERIES));
    }
}
