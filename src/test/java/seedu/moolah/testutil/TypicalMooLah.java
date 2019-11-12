package seedu.moolah.testutil;

import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_DESCRIPTION_TAXI;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_CHICKEN;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EXPENSE_PRICE_TAXI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.moolah.model.MooLah;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.event.Event;
import seedu.moolah.model.expense.Expense;

/**
 * A utility class containing a list of {@code Expense} objects to be used in tests.
 */
public class TypicalMooLah {

    public static final String SCHOOL_BUDGET_STRING_ONE = "|| Description: School related expenses Amount: 300.00 "
            + "Period: month Start date: 15 Oct 2019, 12:00:00 AM End date: 14 Nov 2019, 11:59:59 PM ||";

    public static final String SCHOOL_BUDGET_STRING_TWO = "|| Description: School related expenses Amount: 300.00 "
            + "Period: month Start date: Oct 15, 2019, 12:00:00 AM End date: Nov 14, 2019, 11:59:59 PM ||";

    // ==============================Budgets=================================
    public static final Budget SCHOOL = new BudgetBuilder()
            .withDescription("School related expenses")
            .withAmount("300")
            .withStartDate("15-10-2019 noon")
            .withPeriod("month")
            .withEndDate("14-11-2019 noon")
            .withIsPrimary(true)
            .build();

    public static final Budget OUTSIDE_SCHOOL = new BudgetBuilder()
            .withDescription("Outside school expenses")
            .withAmount("200")
            .withStartDate("05-09-2019 noon")
            .withPeriod("month")
            .withEndDate("04-10-2019 noon")
            .withIsPrimary(false)
            .build();
    // ==============================Expenses=================================
    public static final Expense CHICKEN_RICE = new ExpenseBuilder()
            .withDescription("Chicken Rice extra Chicken")
            .withPrice("3.50")
            .withCategory("Food")
            .withTimestamp("23-10-2019 noon")
            .withBudgetName("School related expenses")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000003")
            .build();
    public static final Expense DRINKS = new ExpenseBuilder()
            .withDescription("Whiskey and Coke")
            .withPrice("50")
            .withCategory("Food")
            .withTimestamp("24-10 noon")
            .withBudgetName("School related expenses")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000004")
            .build();

    //==== For Statistics testing ===
    public static final Budget DAY_BUDGET = new BudgetBuilder()
            .withDescription("Day Budget")
            .withAmount("100")
            .withStartDate("05-09-2016 evening")
            .withPeriod("day")
            .withEndDate("05-09-2016 noon")
            .withIsPrimary(true)
            .build();

    public static final Budget WEEK_BUDGET = new BudgetBuilder()
            .withDescription("Default Budget")
            .withAmount("200")
            .withStartDate("05-09-2016 noon")
            .withPeriod("week")
            .withEndDate("11-09-2016 evening")
            .withIsPrimary(true)
            .build();

    public static final Budget MONTH_BUDGET = new BudgetBuilder()
            .withDescription("Default Budget")
            .withAmount("300")
            .withStartDate("05-09-2016 noon")
            .withPeriod("month")
            .withEndDate("04-10-2016 evening")
            .withIsPrimary(true)
            .build();

    public static final Budget YEAR_BUDGET = new BudgetBuilder()
            .withDescription("Default Budget")
            .withAmount("400")
            .withStartDate("05-09-2016 noon")
            .withPeriod("year")
            .withEndDate("04-09-2017 evening")
            .withIsPrimary(true)
            .build();

    public static final Expense DUCK_RICE = new ExpenseBuilder()
            .withDescription("Duck Rice")
            .withPrice("5")
            .withCategory("Food")
            .withTimestamp("05-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000023")
            .build();

    public static final Expense MEE_POK = new ExpenseBuilder()
            .withDescription("Mee Pok")
            .withPrice("10")
            .withCategory("Food")
            .withTimestamp("13-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000024")
            .build();


    public static final Expense GRAB_RIDE = new ExpenseBuilder()
            .withDescription("Grab Ride")
            .withPrice("20")
            .withCategory("Transport")
            .withTimestamp("14-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000025")
            .build();

    public static final Expense MALAYSIA_TRIP = new ExpenseBuilder()
            .withDescription("Malaysia Trip")
            .withPrice("30")
            .withCategory("Travel")
            .withTimestamp("15-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000026")
            .build();

    public static final Expense SOCCER_JERSEY = new ExpenseBuilder()
            .withDescription("Soccer Jersey")
            .withPrice("60")
            .withCategory("Shopping")
            .withTimestamp("16-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000027")
            .build();



    public static final Expense GIANT_GROCERIES = new ExpenseBuilder()
            .withDescription("Weekend Groceries")
            .withPrice("50")
            .withCategory("Utilities")
            .withTimestamp("17-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000028")
            .build();

    public static final Expense NTUC_GROCERIES = new ExpenseBuilder()
            .withDescription("Weekend Groceries")
            .withPrice("70")
            .withCategory("Utilities")
            .withTimestamp("20-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000100")
            .build();

    public static final Expense MEDICATION = new ExpenseBuilder()
            .withDescription("Medication")
            .withPrice("240")
            .withCategory("Healthcare")
            .withTimestamp("18-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000029")
            .build();

    public static final Expense ROLLER_COASTER = new ExpenseBuilder()
            .withDescription("Roller Coaster")
            .withPrice("480")
            .withCategory("Entertainment")
            .withTimestamp("19-09-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000030")
            .build();

    public static final Expense TUITION = new ExpenseBuilder()
            .withDescription("Tuition")
            .withPrice("960")
            .withCategory("Education")
            .withTimestamp("05-10-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000031")
            .build();

    public static final Expense DEBT_TO_DEBBY = new ExpenseBuilder()
            .withDescription("Debt to Debby")
            .withPrice("1920")
            .withCategory("Others")
            .withTimestamp("06-10-2016 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000031")
            .build();


    //==== For Statscompare ===

    public static final Expense MEE_SIAM = new ExpenseBuilder()
            .withDescription("Mee Siam")
            .withPrice("5")
            .withCategory("Food")
            .withTimestamp("13-09-2015 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000101")
            .build();


    public static final Expense MEE_REBUS = new ExpenseBuilder()
            .withDescription("Mee Rebus")
            .withPrice("5")
            .withCategory("Food")
            .withTimestamp("13-09-2015 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000102")
            .build();

    public static final Expense BUS_RIDE = new ExpenseBuilder()
            .withDescription("Bus Ride")
            .withPrice("20")
            .withCategory("Transport")
            .withTimestamp("14-09-2015 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000103")
            .build();

    public static final Expense THAILAND_TRIP = new ExpenseBuilder()
            .withDescription("Thailand Trip")
            .withPrice("500")
            .withCategory("Travel")
            .withTimestamp("15-09-2015 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000107")
            .build();

    public static final Expense BASKETBALL_JERSEY = new ExpenseBuilder()
            .withDescription("Basketball Jersey")
            .withPrice("60")
            .withCategory("Shopping")
            .withTimestamp("16-09-2015 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000108")
            .build();



    public static final Expense COLD_STORAGE = new ExpenseBuilder()
            .withDescription("Weekend Groceries")
            .withPrice("20")
            .withCategory("Utilities")
            .withTimestamp("17-09-2015 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000028")
            .build();




    //==== For MooLah testing ===

    public static final Expense ANNIVERSARY = new ExpenseBuilder()
            .withDescription("Alices Birthday")
            .withPrice("20")
            .withCategory("Entertainment")
            .withTimestamp("21-10-2019 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000001")
            .build();

    public static final Expense BUSAN_TRIP = new ExpenseBuilder()
            .withDescription("Busan Trip")
            .withPrice("1300")
            .withCategory("Transport")
            .withTimestamp("22-10 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000002")
            .build();

    public static final Expense ENTERTAINMENT = new ExpenseBuilder()
            .withDescription("Marvel Movie Marathon")
            .withPrice("75")
            .withCategory("Entertainment")
            .withTimestamp("25-10 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000005")
            .build();
    public static final Expense FASHION = new ExpenseBuilder()
            .withDescription("Clothes for the New Year")
            .withPrice("88.88")
            .withCategory("Shopping")
            .withTimestamp("26-10 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000006")
            .build();
    public static final Expense GROCERIES = new ExpenseBuilder()
            .withDescription("Groceries for September meal preps")
            .withPrice("125.35")
            .withCategory("Utilities")
            .withTimestamp("27-10 noon")
            .withBudgetName("Default Budget")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000007")
            .build();

    // Manually added
    public static final Expense HALLOWEEN = new ExpenseBuilder()
            .withDescription("HalloweenHorrorNight")
            .withPrice("60")
            .withCategory("Entertainment")
            .withTimestamp("08-10 noon")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000008").build();
    public static final Expense INVESTMENT = new ExpenseBuilder()
            .withDescription("Property investment")
            .withPrice("1200000")
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-000000000009").build();

    // Manually added - Expense's details found in {@code CommandTestUtil}
    public static final Expense CHICKEN = new ExpenseBuilder()
            .withDescription(VALID_EXPENSE_DESCRIPTION_CHICKEN)
            .withPrice(VALID_EXPENSE_PRICE_CHICKEN)
            .withCategory(VALID_EXPENSE_CATEGORY_TAXI)
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-00000000000a").build();
    public static final Expense TRANSPORT = new ExpenseBuilder()
            .withDescription(VALID_EXPENSE_DESCRIPTION_TAXI)
            .withPrice(VALID_EXPENSE_PRICE_TAXI)
            .withCategory(VALID_EXPENSE_CATEGORY_CHICKEN)
            .withUniqueIdentifier("Expense@00000000-0000-0000-0000-00000000000b").build();

    // ==============================Events=================================
    public static final Event BRIAN_BDAY = new EventBuilder()
            .withDescription("Brian bday")
            .withPrice("30.5")
            .withCategory("Shopping")
            .withTimestamp("01-01-2020 noon")
            .withBudgetName("Default Budget")
            .build();
    public static final Event BANGKOK_TRIP = new EventBuilder()
            .withDescription("Bangkok trip")
            .withPrice("500")
            .withCategory("Transport")
            .withTimestamp("02-01-2020 noon")
            .withBudgetName("Default Budget")
            .build();
    public static final Event GIRLFRIEND_ANNIVERSARY = new EventBuilder()
            .withDescription("Girlfriend anniversary")
            .withPrice("50")
            .withCategory("Shopping")
            .withTimestamp("03-01-2020 noon")
            .withBudgetName("Default Budget")
            .build();
    public static final Event DRINKS_WITH_FRIENDS = new EventBuilder()
            .withDescription("Drinks with friends")
            .withPrice("80")
            .withCategory("Food")
            .withTimestamp("04-01-2020 noon")
            .withBudgetName("Default Budget")
            .build();
    public static final Event USS_TRIP = new EventBuilder()
            .withDescription("USS trip")
            .withPrice("50")
            .withCategory("Entertainment")
            .withTimestamp("07-01-2020 noon")
            .withBudgetName("Default Budget")
            .build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event BUFFET = new EventBuilder()
            .withDescription(VALID_EVENT_DESCRIPTION_BUFFET)
            .withPrice(VALID_EVENT_PRICE_BUFFET)
            .withTimestamp(VALID_EVENT_TIMESTAMP_BUFFET)
            .withCategory(VALID_EVENT_CATEGORY_BUFFET)
            .build();
    public static final Event BIRTHDAY = new EventBuilder()
            .withDescription(VALID_EVENT_DESCRIPTION_BIRTHDAY)
            .withPrice(VALID_EVENT_PRICE_BIRTHDAY)
            .withTimestamp(VALID_EVENT_TIMESTAMP_BIRTHDAY)
            .withCategory(VALID_EVENT_CATEGORY_BIRTHDAY)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMooLah() {} // prevents instantiation

    /**
     * Returns an {@code MooLah} with all the typical expenses, events and budgets..
     */
    public static MooLah getTypicalMooLah() {
        MooLah ab = new MooLah();
        for (Budget budget : getTypicalBudgets()) {
            ab.addBudget(budget);
        }
        for (Expense expense : getTypicalExpenses()) {
            ab.addExpense(expense);
            for (Budget b : getTypicalBudgets()) {
                if (b.getDescription().equals(expense.getBudgetName())) {
                    b.addExpense(expense);
                }
            }
        }
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static MooLah getTypicalExpensesOnlyMooLah() {
        MooLah ab = new MooLah();
        for (Expense expense : getTypicalExpenses()) {
            ab.addExpense(expense);
        }
        return ab;
    }

    public static MooLah getTypicalEventsOnlyMooLah() {
        MooLah ab = new MooLah();
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static MooLah getTypicalMooLahForStatistics() {
        MooLah ab = new MooLah();
        ab.addBudget(TypicalMooLah.getPopulatedDayBudget());
        return ab;
    }




    public static List<Expense> getTypicalExpenses() {
        return new ArrayList<>(Arrays.asList(
                ANNIVERSARY, BUSAN_TRIP, CHICKEN_RICE, DRINKS, ENTERTAINMENT, FASHION, GROCERIES));
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(
                BRIAN_BDAY, BANGKOK_TRIP, GIRLFRIEND_ANNIVERSARY, DRINKS_WITH_FRIENDS, USS_TRIP));
    }

    public static List<Budget> getTypicalBudgets() {
        return new ArrayList<>(Arrays.asList(OUTSIDE_SCHOOL, SCHOOL));
    }

    /**
     * To reverse effect of ClearBudgetsCommandTest
     */
    public static void reset() {
        CHICKEN_RICE.setBudget(SCHOOL);
        DRINKS.setBudget(SCHOOL);
        SCHOOL.addExpense(CHICKEN_RICE);
        SCHOOL.addExpense(DRINKS);
    }

    //==== For Statistics testing ===

    public static List<Expense> getExpenseForStatistics() {
        return new ArrayList<>(List.of(DUCK_RICE, MEE_POK, GRAB_RIDE, MALAYSIA_TRIP, SOCCER_JERSEY,
                //GIANT_GROCERIES, MEDICATION, ROLLER_COASTER, TUITION, DEBT_TO_DEBBY, NTUC_GROCERIES));
                GIANT_GROCERIES, MEDICATION, ROLLER_COASTER, TUITION, DEBT_TO_DEBBY, NTUC_GROCERIES,
                MEE_SIAM, MEE_REBUS, BUS_RIDE, THAILAND_TRIP, BASKETBALL_JERSEY, COLD_STORAGE));
    }

    public static Budget getPopulatedDayBudget() {
        Budget budget = TypicalMooLah.DAY_BUDGET;
        for (Expense expense : getExpenseForStatistics()) {
            budget.addExpense(expense);
        }
        return budget;
    }

    public static Budget getPopulatedWeekBudget() {
        Budget budget = TypicalMooLah.WEEK_BUDGET;
        for (Expense expense : getExpenseForStatistics()) {
            budget.addExpense(expense);
        }
        return budget;
    }

    public static Budget getPopulatedMonthBudget() {
        Budget budget = TypicalMooLah.MONTH_BUDGET;
        for (Expense expense : getExpenseForStatistics()) {
            budget.addExpense(expense);
        }
        return budget;
    }

    public static Budget getPopulatedYearBudget() {
        Budget budget = TypicalMooLah.YEAR_BUDGET;
        for (Expense expense : getExpenseForStatistics()) {
            budget.addExpense(expense);
        }
        return budget;
    }

    //==== For User testing ===


}
