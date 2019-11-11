package seedu.guilttrip.testutil;

import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_CLOTHING_EXPENSE;
import static seedu.guilttrip.logic.commands.CommandTestUtil.VALID_CATEGORY_FOOD_EXPENSE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.internal.Conditions;

import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TagsCondition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;
import seedu.guilttrip.model.tag.Tag;

/**
 * A utility class containing a list of {@code Entry} objects to be used in tests.
 */
public class TypicalEntries {

    // Expenses
    public static final Expense FOOD_EXPENSE = new ExpenseBuilder().withDesc("pgp mala").withTime("2019-09-09")
            .withCategory(VALID_CATEGORY_FOOD_EXPENSE).withAmt(5.50).withTags("food").build();
    public static final Expense CLOTHING_EXPENSE = new ExpenseBuilder().withDesc("cotton on jeans on sale")
            .withCategory(VALID_CATEGORY_CLOTHING_EXPENSE).withTime("2019-11-09").withAmt(300)
            .withTags("want", "clothes").build();
    public static final Expense TRAVEL_EXPENSE = new ExpenseBuilder().withDesc("Travel to Mars")
            .withCategory("Travel").withTime("2019-08-11").withAmt(10000.00).withTags("Gettingalife", "Rest").build();

    // Incomes
    public static final Income SALARY_INCOME = new IncomeBuilder().withDesc("november salary")
            .withCategory("Salary").withDate("2019 11 28").withAmt(3500).withTags("work").build();
    public static final Income PART_TIME_INCOME = new IncomeBuilder().withDesc("part time work")
            .withCategory("Salary").withDate("2019 09 21").withAmt(350).withTags("parttime").build();
    public static final Income GIFT_INCOME = new IncomeBuilder().withDesc("gift card from mom")
            .withCategory("Gift").withDate("2019 10 20").withAmt(30).withTags("timetogoshopping").build();

    // Wishes
    public static final Wish AIRPODS_WISH = new WishBuilder().withDesc("airpods proo")
            .withCategory("Shopping").withDate("2019 11 02").withAmt(450).withTags("new").build();
    public static final Wish MACBOOK_WISH = new WishBuilder().withDesc("macbook proo")
            .withCategory("Shopping").withDate("2019 11 03").withAmt(2000).withTags("cantGetEnoughOfApple").build();
    public static final Wish PORTABLE_WISH = new WishBuilder().withDesc("xiaomi portable charger from lazada")
            .withCategory("Shopping").withDate("2019 11 11").withAmt(20).withTags("electronics").build();

    // Budgets
    public static final Budget FOOD_BUDGET = new BudgetBuilder().withDesc("november food budget")
            .withCategory("Expense").withDate("2019 11 01").withTotalAmt(200).withTags("food").build();
    public static final Budget PUBLIC_TRANSPORT_BUDGET = new BudgetBuilder().withDesc("nov public transport budget")
            .withCategory("Expense").withDate("2019 11 01").withTotalAmt(50).build();
    public static final Budget BUBBLETEA_BUDGET = new BudgetBuilder().withDesc("nov bubbletea budget")
            .withCategory("Expense").withDate("2019 11 01").withTotalAmt(50).withTags("REDUCEYOURSPENDING").build();



    // Standard categories
    public static final Category CATEGORY_FOOD = new CategoryBuilder().withCatType("Expense").withCatName("food")
            .build();
    public static final Category CATEGORY_SHOPPING = new CategoryBuilder().withCatType("Expense")
            .withCatName("shopping").build();
    public static final Category CATEGORY_TRAVEL = new CategoryBuilder().withCatType("Expense")
            .withCatName("travel").build();
    public static final Category CATEGORY_BUSINESS = new CategoryBuilder().withCatType("Income").withCatName("business")
            .build();

    /*public static final Category CATEGORY_STOCKS = new CategoryBuilder().withCatType("Income").withCatName("stocks")
            .build();*/

    public static final Category CATEGORY_SALARY = new CategoryBuilder().withCatType("Income").withCatName("salary")
            .build();
    public static final Category CATEGORY_GIFT = new CategoryBuilder().withCatType("Income").withCatName("gift")
            .build();

    // For adding new Categories
    public static final Category CATEGORY_STOCKS = new CategoryBuilder().withCatType("Income").withCatName("stocks")
            .build();
    public static final Category CATEGORY_SPACE = new CategoryBuilder().withCatType("Expense").withCatName("space")
            .build();

    private TypicalEntries() {
    } // prevents instantiation

    /**
     * Returns an {@code GuiltTrip} with all the typical entries.
     */
    public static GuiltTrip getTypicalGuiltTrip() {
        GuiltTrip gt = new GuiltTrip(false);
        for (Expense expense : getTypicalExpenses()) {
            gt.addExpense(expense);
        }
        for (Income income : getTypicalIncomes()) {
            gt.addIncome(income);
        }
        for (Budget budget : getTypicalBudgets()) {
            gt.addBudget(budget);
        }
        for (Wish wish : getTypicalWishes()) {
            gt.addWish(wish);
        }
        return gt;
    }

    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(FOOD_EXPENSE, CLOTHING_EXPENSE, TRAVEL_EXPENSE));
    }

    public static List<Income> getTypicalIncomes() {
        return new ArrayList<>(Arrays.asList(SALARY_INCOME, PART_TIME_INCOME, GIFT_INCOME));
    }

    public static List<Wish> getTypicalWishes() {
        return new ArrayList<>(Arrays.asList(AIRPODS_WISH, MACBOOK_WISH, PORTABLE_WISH));
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(FOOD_BUDGET, PUBLIC_TRANSPORT_BUDGET, BUBBLETEA_BUDGET));
    }

    public static List<Category> getTypicalCategories() {
        return new ArrayList<>(Arrays.asList(CATEGORY_FOOD, CATEGORY_SHOPPING, CATEGORY_BUSINESS, CATEGORY_TRAVEL,
                CATEGORY_GIFT, CATEGORY_SALARY));
    }
}
