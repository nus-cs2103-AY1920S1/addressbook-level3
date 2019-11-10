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
import seedu.guilttrip.model.entry.Income;

/**
 * A utility class containing a list of {@code Entry} objects to be used in tests.
 */
public class TypicalEntries {

    public static final Expense FOOD_EXPENSE = new ExpenseBuilder().withDesc("pgp mala").withTime("2019-09-09")
            .withCategory(VALID_CATEGORY_FOOD_EXPENSE).withAmt(5.50).withTags("food").build();
    public static final Expense CLOTHING_EXPENSE = new ExpenseBuilder().withDesc("cotton on jeans on sale")
            .withCategory(VALID_CATEGORY_CLOTHING_EXPENSE).withTime("2019-11-09").withAmt(300)
            .withTags("want", "clothes").build();
    public static final Expense TRAVEL_EXPENSE = new ExpenseBuilder().withDesc("Travel to Mars")
            .withCategory("Travel").withTime("2019-08-11").withAmt(10000.00).withTags("Gettingalife", "Rest").build();

    public static final Income SALARY_INCOME = new IncomeBuilder().withDesc("november salary")
            .withCategory("Salary").withDate("2019 11 28").withAmt("3500").withTags("work").build();
    public static final Income PART_TIME_INCOME = new IncomeBuilder().withDesc("part time work")
            .withCategory("Salary").withDate("2019 09 21").withAmt("350").withTags("parttime").build();
    public static final Income GIFT_INCOME = new IncomeBuilder().withDesc("gift card from mom")
            .withCategory("Gift").withDate("2019 10 20").withAmt("30").withTags("timetogoshopping").build();

    public static final Category CATEGORY_FOOD = new CategoryBuilder().withCatType("Expense").withCatName("food")
            .build();
    public static final Category CATEGORY_SHOPPING = new CategoryBuilder().withCatType("Expense")
            .withCatName("shopping").build();
    public static final Category CATEGORY_TRAVEL = new CategoryBuilder().withCatType("Expense")
            .withCatName("travel").build();
    public static final Category CATEGORY_BUSINESS = new CategoryBuilder().withCatType("Income").withCatName("business")
            .build();
<<<<<<< HEAD
<<<<<<< HEAD
    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
=======
    public static final Category CATEGORY_STOCKS = new CategoryBuilder().withCatType("Income").withCatName("stocks")
            .build();
>>>>>>> 6c8808293a4466f0a09dd6f1a452ffdb7b4e87c7
=======
    public static final Category CATEGORY_SALARY = new CategoryBuilder().withCatType("Income").withCatName("salary")
            .build();
    public static final Category CATEGORY_GIFT = new CategoryBuilder().withCatType("Income").withCatName("gift")
            .build();


    //For adding new Categories
    public static final Category CATEGORY_STOCKS = new CategoryBuilder().withCatType("Income").withCatName("stocks")
            .build();
    public static final Category CATEGORY_SPACE = new CategoryBuilder().withCatType("Expense").withCatName("space")
            .build();
>>>>>>> f659a201d941a9d7dedf6472b89947095b946820

    private TypicalEntries() {
    } // prevents instantiation

    /**
     * Returns an {@code GuiltTrip} with all the typical entries.
     */
    public static GuiltTrip getTypicalGuiltTrip() {
        GuiltTrip gt = new GuiltTrip(false);
        for (Category category : getTypicalCategories()) {
            gt.addCategory(category);
        }
        for (Entry entry : getTypicalEntries()) {
            gt.addEntry(entry);
        }
        for (Income income : getTypicalIncomes()) {
            gt.addIncome(income);
        }
        return gt;
    }

    public static List<Entry> getTypicalEntries() {
        return new ArrayList<>(Arrays.asList(CLOTHING_EXPENSE, FOOD_EXPENSE, TRAVEL_EXPENSE));
    }

    public static List<Income> getTypicalIncomes() {
        return new ArrayList<>(Arrays.asList(SALARY_INCOME, PART_TIME_INCOME, GIFT_INCOME));
    }

    public static List<Category> getTypicalCategories() {
        return new ArrayList<>(Arrays.asList(CATEGORY_FOOD, CATEGORY_SHOPPING, CATEGORY_BUSINESS, CATEGORY_TRAVEL,
                CATEGORY_GIFT, CATEGORY_SALARY));
    }
}
