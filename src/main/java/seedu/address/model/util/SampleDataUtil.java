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

    public static Budget[] getSampleBudgets() {
        return new Budget[] {
            new Budget(new Description("School"), new Price("300"),
                        Timestamp.createTimestampIfValid("01-10").get(), BudgetPeriod.MONTH),
            new Budget(new Description("Outside School"), new Price("200"),
                        Timestamp.createTimestampIfValid("01-10").get(), BudgetPeriod.MONTH),
        };
    }

    public static Expense[] getSampleExpenses() {
        return new Expense[] {
            new Expense(new Description("Chickens"), new Price("30"),
                    getCategory("food"), Timestamp.getCurrentTimestamp(), DEFAULT_BUDGET_DESCRIPTION,
                    UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("David Li"), new Price("15000"),
                    getCategory("others"), Timestamp.getCurrentTimestamp(), DEFAULT_BUDGET_DESCRIPTION,
                    UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Invite only Chicken Event"), new Price("1000"),
                    getCategory("Food"), Timestamp.getCurrentTimestamp(), DEFAULT_BUDGET_DESCRIPTION,
                    UniqueIdentifierGenerator.generateRandomUniqueIdentifier()),
            new Expense(new Description("Chicken Rice extra Chicken"), new Price("15"),
                    getCategory("Food"), Timestamp.getCurrentTimestamp(), DEFAULT_BUDGET_DESCRIPTION,
                    UniqueIdentifierGenerator.generateRandomUniqueIdentifier())
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
