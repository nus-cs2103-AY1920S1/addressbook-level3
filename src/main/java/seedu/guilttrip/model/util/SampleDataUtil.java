package seedu.guilttrip.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Budget;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.entry.Period;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.tag.Tag;

/**
 * Contains utility methods for populating {@code GuiltTrip} with sample data.
 */
public class SampleDataUtil {
    private static Expense[] getSampleExpenses() {
        return new Expense[]{
            new Expense(new Category("Food", "Expense"), new Description("mala at deck"),
                    new Date("2019 11 09"), new Amount("5.40"), getTagSet("mala")),
            new Expense(new Category("Food", "Expense"), new Description("chicken rice"),
                    new Date("2019 11 10"), new Amount("3.50"), getTagSet("food")),
            new Expense(new Category("Travel", "Expense"), new Description("hong kong trip"),
                    new Date("2019 11 07"), new Amount("2000"), getTagSet("joinProtests")),
            new Expense(new Category("Entertainment", "Expense"),
                    new Description("joker movie"), new Date("2019 10 31"), new Amount("13.50"),
                    getTagSet("withFriends")),
            new Expense(new Category("Food", "Expense"), new Description("potato chips"),
                    new Date("2019 11 05"), new Amount("15"), getTagSet("forBingeEating"))
        };
    }

    private static Income[] getSampleIncomes() {
        return new Income[]{
            new Income(new Category("Salary", "Income"), new Description("october salary"),
                    new Date("2019 10 28"), new Amount("3000"), getTagSet("salary")),
            new Income(new Category("Gifts", "Income"), new Description("gst voucher"),
                    new Date("2019 11 07"), new Amount("300"), getTagSet("gahmenMoney")),
            new Income(new Category("Business", "Income"), new Description("side business"),
                    new Date("2019 11 05"), new Amount("100"), getTagSet("salary"))
        };
    }

    private static Wish[] getSampleWishes() {
        return new Wish[]{
            new Wish(new Category("Shopping", "Expense"), new Description("airpods prooo"),
                    new Date("2019 11 07"), new Amount("450"), getTagSet("gudStuff")),
            new Wish(new Category("Shopping", "Expense"), new Description("macbook prooo"),
                    new Date("2019 11 08"), new Amount("2000"), getTagSet("cantGetEnufOfApple")),
            new Wish(new Category("Shopping", "Expense"),
                    new Description("moleskine notebook"), new Date("2019 11 11"), new Amount("50"),
                    getTagSet("giftForFriend")),
        };
    }

    private static Budget[] getSampleBudgets() {
        return new Budget[]{
            new Budget(new Category("Food", "Expense"), new Description("nov food budget"),
                    new Date("2019 11 01"), new Period("1m"), new Amount("400"),
                    getTagSet("canSpendMoreThisMonth")),
            new Budget(new Category("Transport", "Expense"), new Description("grab budget"),
                    new Date("2019 11 11"), new Period("7d"), new Amount("50"),
                    getTagSet("spentTooMuchLastWeek")),
        };
    }

    public static Category[] getSampleCategories() {
        return new Category[]{
            new Category("Food", "Expense"),
            new Category("Travel", "Expense"),
            new Category("Family", "Expense"),
            new Category("Bills", "Expense"),
            new Category("Entertainment", "Expense"),
            new Category("Transport", "Expense"),
            new Category("Shopping", "Expense"),
            new Category("HealthCare", "Expense"),
            new Category("Groceries", "Expense"),
            new Category("Rent", "Expense"),
            new Category("Business", "Income"),
            new Category("Salary", "Income"),
            new Category("Gifts", "Income"),
            new Category("Loans", "Income")
        };
    }

    /**
     * Add sample entries and categories into GuiltTrip. If GuiltTrip is empty, adds only a default set of Categories.
     */
    public static ReadOnlyGuiltTrip getSampleGuiltTrip() {
        GuiltTrip sampleGt = new GuiltTrip(true);
        for (Expense sampleExpense : getSampleExpenses()) {
            sampleGt.addExpense(sampleExpense);
        }
        for (Income sampleIncome : getSampleIncomes()) {
            sampleGt.addIncome(sampleIncome);
        }
        for (Wish sampleWish : getSampleWishes()) {
            sampleGt.addWish(sampleWish);
        }
        for (Budget sampleBudget : getSampleBudgets()) {
            sampleGt.addBudget(sampleBudget);
        }

        return sampleGt;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
