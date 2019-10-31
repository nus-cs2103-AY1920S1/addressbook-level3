package seedu.address.model.util;

import static seedu.address.model.budget.Budget.DEFAULT_BUDGET_DESCRIPTION;

import seedu.address.model.MooLah;
import seedu.address.model.ReadOnlyMooLah;

import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetPeriod;
import seedu.address.model.category.Category;
import seedu.address.model.expense.Description;
import seedu.address.model.expense.Event;
import seedu.address.model.expense.Expense;
import seedu.address.model.expense.Price;
import seedu.address.model.expense.Timestamp;
import seedu.address.model.expense.util.UniqueIdentifierGenerator;


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
            new Budget(new Description("Outside School"), new Price("50"),
                    Timestamp.createTimestampIfValid("28-10").get(), BudgetPeriod.WEEK),
            new Budget(new Description("NUS Canteens"), new Price("300"),
                Timestamp.createTimestampIfValid("03-10").get(), BudgetPeriod.MONTH)
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
                    getCategory("food"), Timestamp.createTimestampIfValid("23-10").get(),
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

            new Expense(new Description("Movie"), new Price("10.50"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("29-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Bird Park"), new Price("13.00"),
                    getCategory("entertainment"), Timestamp.createTimestampIfValid("18-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Clothes"), new Price("18.90"),
                    getCategory("shopping"), Timestamp.createTimestampIfValid("15-10").get(),
                    OUTSIDE_SCHOOL, UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),

            new Expense(new Description("Genki Sushi"), new Price("23.50"),
                    getCategory("food"), Timestamp.createTimestampIfValid("31-10").get(),
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
