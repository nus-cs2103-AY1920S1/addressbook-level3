package seedu.billboard.testutil;

import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_AMOUNT_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_NAME_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DINNER;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_DESCRIPTION_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_TAXES;
import static seedu.billboard.logic.commands.CommandTestUtil.VALID_TAG_DINNER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.billboard.model.Billboard;
import seedu.billboard.model.person.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalExpenses {

    public static final Expense BILLS = new ExpenseBuilder().withName("monthly bills")
            .withDescription("pay phone company")
            .withAmount("350.25")
            .withTags("bills").build();
    public static final Expense FOOD = new ExpenseBuilder().withName("monday breakfast food")
            .withDescription("toast with frens")
            .withAmount("4.20")
            .withTags("monday", "friends").build();
    public static final Expense GROCERIES = new ExpenseBuilder().withName("groceries")
            .withDescription("bought from fairprice")
            .withAmount("23.50")
            .build();
    public static final Expense MOVIE = new ExpenseBuilder().withName("movie tickets")
            .withDescription("tickets to joker")
            .withAmount("10.00").withTags("leisure").build();


    // Manually added
    public static final Expense CLOTHES = new ExpenseBuilder().withName("clothes")
            .withDescription("bought new yeezys")
            .withAmount("1000")
            .build();
    public static final Expense NEW_LAPTOP = new ExpenseBuilder().withName("new laptop")
            .withDescription("new macbook pro")
            .withAmount("2999.99")
            .build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense DINNER = new ExpenseBuilder().withName(VALID_NAME_DINNER)
            .withDescription(VALID_DESCRIPTION_DINNER)
            .withAmount(VALID_AMOUNT_DINNER)
            .withTags(VALID_TAG_TAXES).build();

    public static final Expense TAXES = new ExpenseBuilder().withName(VALID_NAME_TAXES)
            .withDescription(VALID_DESCRIPTION_TAXES)
            .withAmount(VALID_AMOUNT_TAXES)
            .withTags(VALID_TAG_DINNER, VALID_TAG_TAXES)
            .build();


    private TypicalExpenses() {} // prevents instantiation

    /**
     * Returns an {@code Billboard} with all the typical persons.
     */
    public static Billboard getTypicalBillboard() {
        Billboard bb = new Billboard();
        for (Expense expense : getTypicalExpenses()) {
            bb.addExpense(expense);
        }
        return bb;
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(BILLS, FOOD, GROCERIES, MOVIE));
    }
}
