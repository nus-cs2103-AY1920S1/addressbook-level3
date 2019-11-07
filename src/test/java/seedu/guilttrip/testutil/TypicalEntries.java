package seedu.guilttrip.testutil;

import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD_EXPENSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalEntries {

    public static final Expense FOOD_EXPENSE = new ExpenseBuilder().withDesc("pgp mala").withTime("2019-09-09")
            .withCategory(VALID_CATEGORY_FOOD_EXPENSE).withAmt(5.50).withTags("food").build();
    public static final Expense CLOTHING_EXPENSE = new ExpenseBuilder().withDesc("cotton on jeans on sale")
            .withCategory(VALID_CATEGORY_CLOTHING_EXPENSE).withTime("2019-11-09").withAmt(300)
            .withTags("want", "clothes").build();
    public static final Expense TRAVEL_EXPENSE = new ExpenseBuilder().withDesc("Travel to Mars")
            .withCategory("Travel").withTime("2019-08-11").withAmt(10000.00).withTags("Gettingalife", "Rest").build();

    public static final Category CATEGORY_FOOD = new CategoryBuilder().withCatType("Expense").withCatName("food")
            .build();
    public static final Category CATEGORY_SHOPPING = new CategoryBuilder().withCatType("Expense")
            .withCatName("shopping").build();
    public static final Category CATEGORY_TRAVEL = new CategoryBuilder().withCatType("Expense")
            .withCatName("travel").build();
    public static final Category CATEGORY_BUSINESS = new CategoryBuilder().withCatType("Income").withCatName("business")
            .build();
<<<<<<< HEAD
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
=======
    public static final Category CATEGORY_STOCKS = new CategoryBuilder().withCatType("Income").withCatName("stocks")
            .build();
>>>>>>> 6c8808293a4466f0a09dd6f1a452ffdb7b4e87c7

    private TypicalEntries() {
    } // prevents instantiation

    /**
     * Returns an {@code GuiltTrip} with all the typical persons.
     */
    public static GuiltTrip getTypicalGuiltTrip() {
        GuiltTrip ab = new GuiltTrip(false);
        for (Category category : getTypicalCategories()) {
            ab.addCategory(category);
        }
        for (Entry entry : getTypicalEntries()) {
            ab.addEntry(entry);
        }
        return ab;
    }

    public static List<Entry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(FOOD_EXPENSE, CLOTHING_EXPENSE, TRAVEL_EXPENSE));
    }

    public static List<Category> getTypicalCategories() {
        return new ArrayList<>(Arrays.asList(CATEGORY_FOOD, CATEGORY_SHOPPING, CATEGORY_BUSINESS, CATEGORY_TRAVEL));
    }
}
