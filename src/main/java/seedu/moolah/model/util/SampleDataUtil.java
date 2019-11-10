package seedu.moolah.model.util;

import static seedu.moolah.model.budget.Budget.DEFAULT_BUDGET_DESCRIPTION;

import seedu.moolah.model.MooLah;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Expense;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.model.expense.util.UniqueIdentifierGenerator;


/**
 * Contains utility methods for populating {@code MooLah} with sample data.
 */
public class SampleDataUtil {

    private static final Description NUS_CANTEENS = new Description("NUS Canteens");
    private static final Description OUTSIDE_SCHOOL = new Description("Outside School");
    private static final Description HOLIDAY = new Description("Holiday");

    public static Budget[] getSampleBudgets() {
        return new Budget[] {
            new Budget(new Description("Holiday"), new Price("3000"),
                    Timestamp.createTimestampIfValid("01-01").get(), BudgetPeriod.YEAR),
            new Budget(new Description("Outside School"), new Price("500"),
                    Timestamp.createTimestampIfValid("01-11").get(), BudgetPeriod.DAY),
            new Budget(new Description("NUS Canteens"), new Price("300"),
                Timestamp.createTimestampIfValid("28-10").get(), BudgetPeriod.MONTH)
        };
    }

    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Description("Chicken Rice"), new Price("4.80"),
                    getCategory("food"), Timestamp.createTimestampIfValid("31-10").get(),
                    NUS_CANTEENS, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Yong Tau Foo"), new Price("5.20"),
                    getCategory("food"), Timestamp.createTimestampIfValid("29-10").get(),
                    NUS_CANTEENS, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Pasta"), new Price("6.80"),
                    getCategory("food"), Timestamp.createTimestampIfValid("01-11").get(),
                    NUS_CANTEENS, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Taiwan Ichiban"), new Price("5.00"),
                    getCategory("food"), Timestamp.createTimestampIfValid("16-10").get(),
                    NUS_CANTEENS, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Gong Cha"), new Price("3.06"),
                    getCategory("food"), Timestamp.createTimestampIfValid("12-10").get(),
                    NUS_CANTEENS, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Mala Hotpot"), new Price("10.30"),
                    getCategory("food"), Timestamp.createTimestampIfValid("04-10").get(),
                    NUS_CANTEENS, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),


            new Expense(new Description("Chicken rice"), new Price("3.50"),
                    getCategory("food"), Timestamp.createTimestampIfValid("28-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Duck rice"), new Price("4.50"),
                    getCategory("food"), Timestamp.createTimestampIfValid("29-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Pork chop"), new Price("8.00"),
                    getCategory("food"), Timestamp.createTimestampIfValid("26-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Lamb chop"), new Price("6.00"),
                    getCategory("food"), Timestamp.createTimestampIfValid("01-11").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Lamb chop"), new Price("6.00"),
                    getCategory("food"), Timestamp.createTimestampIfValid("03-11").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Mee rebus"), new Price("10.50"),
                    getCategory("food"), Timestamp.createTimestampIfValid("05-11").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),




            new Expense(new Description("Comfort Delgro Taxi"), new Price("20"),
                    getCategory("transport"), Timestamp.createTimestampIfValid("29-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Grab Taxi"), new Price("13"),
                    getCategory("transport"), Timestamp.createTimestampIfValid("25-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),


            new Expense(new Description("Bus fare"), new Price("2.50"),
                    getCategory("transport"), Timestamp.createTimestampIfValid("19-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),


            new Expense(new Description("MRT concession"), new Price("45"),
                    getCategory("transport"), Timestamp.createTimestampIfValid("03-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),




            new Expense(new Description("Levis"), new Price("30"),
                    getCategory("shopping"), Timestamp.createTimestampIfValid("30-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Timberland"), new Price("50"),
                    getCategory("shopping"), Timestamp.createTimestampIfValid("24-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Uniqlo"), new Price("30"),
                    getCategory("shopping"), Timestamp.createTimestampIfValid("14-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Pull and Bears"), new Price("30"),
                    getCategory("shopping"), Timestamp.createTimestampIfValid("05-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),





            new Expense(new Description("Giant"), new Price("10.70"),
                    getCategory("utilities"), Timestamp.createTimestampIfValid("31-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Hardware shop at my house"), new Price("25.40"),
                    getCategory("utilities"), Timestamp.createTimestampIfValid("23-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("IKEA"), new Price("15.65"),
                    getCategory("utilities"), Timestamp.createTimestampIfValid("07-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),


            new Expense(new Description("Sheng Siong"), new Price("31"),
                    getCategory("utilities"), Timestamp.createTimestampIfValid("15-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),





            new Expense(new Description("Panadol"), new Price("20"),
                    getCategory("healthcare"), Timestamp.createTimestampIfValid("01-11").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Sleeping pills"), new Price("31"),
                    getCategory("healthcare"), Timestamp.createTimestampIfValid("22-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Lung Cancer operation"), new Price("30000"),
                    getCategory("healthcare"), Timestamp.createTimestampIfValid("28-10-2018").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),





            new Expense(new Description("Infinity War Movie"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("02-11").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Infinity War Movie"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("01-11").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Infinity War Movie"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("31-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Infinity War Movie"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("30-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Infinity War Movie"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("29-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Infinity War Movie"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("28-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Museum Visit"), new Price("10.90"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("21-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Swimming pool visit"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("13-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Bowling"), new Price("10"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("16-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),




            new Expense(new Description("Debt to Debby"), new Price("100"),
                    getCategory("others"), Timestamp.createTimestampIfValid("03-11").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Debt to Debby"), new Price("100"),
                    getCategory("others"), Timestamp.createTimestampIfValid("27-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),













            new Expense(new Description("Air Ticket"), new Price("349.56"),
                    getCategory("transport"), Timestamp.createTimestampIfValid("01-11").get(),
                    HOLIDAY, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Hotel"), new Price("117.91"),
                    getCategory("travel"), Timestamp.createTimestampIfValid("02-11").get(),
                    HOLIDAY, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Concert"), new Price("247.26"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("03-11").get(),
                    HOLIDAY, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Birthday"), new Price("175"),
                    getCategory("others"), Timestamp.createTimestampIfValid("05-06").get(),
                    DEFAULT_BUDGET_DESCRIPTION, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Learning Journey"), new Price("40"),
                    getCategory("education"), Timestamp.createTimestampIfValid("13-07").get(),
                    DEFAULT_BUDGET_DESCRIPTION, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Phone Bill"), new Price("23.50"),
                    getCategory("utilities"), Timestamp.createTimestampIfValid("20-09").get(),
                    DEFAULT_BUDGET_DESCRIPTION, UniqueIdentifierGenerator.generateRandomUniqueIdentifier())
        };
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new Description("Brian bday"), new Price("30"),
                    getCategory("shopping"), Timestamp.createTimestampIfValid("31-12").get(),
                    DEFAULT_BUDGET_DESCRIPTION),
            new Event(new Description("Family buffet"), new Price("200"),
                    getCategory("food"), Timestamp.createTimestampIfValid("3 weeks from now").get(),
                    DEFAULT_BUDGET_DESCRIPTION),
            new Event(new Description("RC Sem 2 deposit"), new Price("300"),
                    getCategory("utilities"), Timestamp.createTimestampIfValid("3 days from now").get(),
                    DEFAULT_BUDGET_DESCRIPTION),
        };
    }

    public static ReadOnlyMooLah getSampleMooLah() {
        MooLah sampleMl = new MooLah();
        for (Budget sampleBudget : getSampleBudgets()) {
            sampleMl.addBudget(sampleBudget);
        }
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleMl.addExpense(sampleExpense);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleMl.addEvent(sampleEvent);
        }
        return sampleMl;
    }

    public static Category getCategory(String category) {
        return new Category(category);
    }

}
