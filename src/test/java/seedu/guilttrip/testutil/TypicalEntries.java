package seedu.guilttrip.testutil;

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
            .withAmt(5.50).withTags("food").build();
    public static final Expense CLOTHING_EXPENSE = new ExpenseBuilder().withDesc("cotton on jeans on sale")
            .withTime("2019-09-09").withAmt(14.90).withTags("want", "clothes").build();
    public static final Expense TRAVEL_EXPENSE = new ExpenseBuilder().withDesc("Travel to Mars")
            .withCategory("Travel").withTime("2019-08-11").withAmt(10000.00).withTags("Gettingalife", "Rest").build();

    public static final Category CATEGORY_FOOD = new CategoryBuilder().withCatType("Expense").withCatName("food")
            .build();
    public static final Category CATEGORY_BUSINESS = new CategoryBuilder().withCatType("Income").withCatName("business")
            .build();
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEntries() {
    } // prevents instantiation

    /**
     * Returns an {@code GuiltTrip} with all the typical persons.
     */
    public static GuiltTrip getTypicalGuiltTrip() {
        GuiltTrip ab = new GuiltTrip(false);
        for (Entry entry : getTypicalEntries()) {
            ab.addEntry(entry);
        }
        for (Category category : getTypicalCategories()) {
            ab.addCategory(category);
        }
        return ab;
    }

    public static List<Entry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(FOOD_EXPENSE, CLOTHING_EXPENSE));
    }

    public static List<Category> getTypicalCategories() {
        return new ArrayList<>(Arrays.asList(CATEGORY_FOOD, CATEGORY_BUSINESS));
    }
}
