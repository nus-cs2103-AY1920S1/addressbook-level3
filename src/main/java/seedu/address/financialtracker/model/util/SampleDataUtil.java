package seedu.address.financialtracker.model.util;

import seedu.address.financialtracker.model.FinancialTracker;
import seedu.address.financialtracker.model.expense.Amount;
import seedu.address.financialtracker.model.expense.Country;
import seedu.address.financialtracker.model.expense.Date;
import seedu.address.financialtracker.model.expense.Description;
import seedu.address.financialtracker.model.expense.Expense;
import seedu.address.financialtracker.model.expense.Time;
import seedu.address.financialtracker.model.expense.Type;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * A class to stores sample data for financial tracker
 */
public class SampleDataUtil {

    public static Expense[] getExpenses() {
        return new Expense[] {
            // --------- Singapore
            new Expense(new Date("27102019"), new Time("0900"), new Amount("2.60"), new Description("breakfast"),
                new Type("FOOD"), new Country("Singapore")),
            new Expense(new Date("27102019"), new Time("1300"), new Amount("4.80"), new Description("lunch"),
                new Type("FOOD"), new Country("Singapore")),
            new Expense(new Date("27102019"), new Time("1830"), new Amount("6.50"), new Description("dinner"),
                new Type("FOOD"), new Country("Singapore")),
            new Expense(new Date("27102019"), new Time("1600"), new Amount("59.90"),
                new Description("New wireless keyboard"), new Type("SHOPPING"), new Country("Singapore")),
            new Expense(new Date("28102019"), new Time("1130"), new Amount("149.90"),
                new Description("portable aircon"), new Type("NECESSITIES"), new Country("Singapore")),
            // --------- Malaysia
            new Expense(new Date("01012019"), new Time("1700"), new Amount("40"),
                new Description("cheap foooodsss"), new Type("FOOD"), new Country("Malaysia")),
            new Expense(new Date("01012019"), new Time("2100"), new Amount("15"), new Description("grab car"),
                new Type("TRANSPORT"), new Country("Malaysia")),
            new Expense(new Date("02012019"), new Time("0900"), new Amount("80"), new Description("air bnb!"),
                new Type("ACCOMMODATION"), new Country("Malaysia")),
            new Expense(new Date("02012019"), new Time("1500"), new Amount("42.80"), new Description("theme park"),
                 new Type("ENTERTAINMENT"), new Country("Malaysia")),
            new Expense(new Date("03012019"), new Time("0900"), new Amount("15"), new Description("snackss"),
                new Type("FOOD"), new Country("Malaysia")),
            // ---------- Japan
            new Expense(new Date("05062019"), new Time("0800"), new Amount("86.60"), new Description("air ticket"),
                new Type("FLIGHT"), new Country("Japan")),
            new Expense(new Date("05062019"), new Time("1600"), new Amount("39.90"),
                new Description("spending on food"), new Type("FOOD"), new Country("Japan")),
            new Expense(new Date("06062019"), new Time("1034"), new Amount("100"), new Description("hostel"),
                new Type("ACCOMMODATION"), new Country("Japan")),
            new Expense(new Date("06062019"), new Time("1720"), new Amount("20"), new Description("steamboat"),
                new Type("FOOD"), new Country("Japan")),
            new Expense(new Date("07072019"), new Time("0900"), new Amount("40"), new Description("souvenirs"),
                new Type("SHOPPING"), new Country("Japan")),
        };
    }

    public static FinancialTracker getSampleData() {
        FinancialTracker sample = new FinancialTracker();
        try {
            for (Expense expense : getExpenses()) {
                sample.addExpense(expense, expense.getCountry());
            }
        } catch (CommandException e) {
            assert false : "Sample date can't be broken";
            sample = new FinancialTracker();
            return sample;
        }
        return sample;
    }
}
