package seedu.address.model.finance.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.finance.FinanceLog;
import seedu.address.model.finance.ReadOnlyFinanceLog;
import seedu.address.model.finance.attributes.Amount;
import seedu.address.model.finance.attributes.Category;
import seedu.address.model.finance.attributes.Description;
import seedu.address.model.finance.attributes.Person;
import seedu.address.model.finance.attributes.Place;
import seedu.address.model.finance.attributes.TransactionDate;
import seedu.address.model.finance.attributes.TransactionMethod;
import seedu.address.model.finance.logentry.BorrowLogEntry;
import seedu.address.model.finance.logentry.IncomeLogEntry;
import seedu.address.model.finance.logentry.LendLogEntry;
import seedu.address.model.finance.logentry.LogEntry;
import seedu.address.model.finance.logentry.SpendLogEntry;

/**
 * Contains utility methods for populating {@code FinanceLog} with sample data.
 */
public class SampleDataUtil {
    public static LogEntry[] getSampleLogEntries() {
        return new LogEntry[] {
            new SpendLogEntry(
                    new Amount("87.20"),
                    new TransactionDate("09-08-2019"),
                    new Description("Ikea furniture"),
                    new TransactionMethod("Bank transfer"),
                    getCategorySet("house", "Ikea"),
                    new Place("Ikea")
            ),
            new SpendLogEntry(
                    new Amount("3.95"),
                    new TransactionDate("21-08-2019"),
                    new Description("Ciao Y38 Honey Copic"),
                    new TransactionMethod("NETS"),
                    getCategorySet("art"),
                    new Place("ArtFriend")
            ),
            new SpendLogEntry(
                    new Amount("4"),
                    new TransactionDate("22-08-2019"),
                    new Description("Rover's Island Hopping Trip"),
                    new TransactionMethod("PayNow"),
                    getCategorySet("nature"),
                    new Place("Rovers")
            ),
            new SpendLogEntry(
                    new Amount("4"),
                    new TransactionDate("06-09-2019"),
                    new Description("Honey glazed chicken"),
                    new TransactionMethod("Cash"),
                    getCategorySet("taiwanese", "Food"),
                    new Place("Frontier")
            ),
            new IncomeLogEntry(
                    new Amount("50"),
                    new TransactionDate("14-09-2019"),
                    new Description("Weekly Allowance"),
                    new TransactionMethod("Cash"),
                    getCategorySet("Allowance"),
                    new Person("Parent")
            ),
            new BorrowLogEntry(
                    new Amount("0.40"),
                    new TransactionDate("15-09-2019"),
                    new Description("Haw flakes"),
                    new TransactionMethod("Cash"),
                    getCategorySet("Valudollar"),
                    new Person("Sister")
            ),
            new LendLogEntry(
                    new Amount("10"),
                    new TransactionDate("20-09-2019"),
                    new Description("Groceries"),
                    new TransactionMethod("Cash"),
                    getCategorySet(),
                    new Person("Parent")
            ),
            new SpendLogEntry(
                new Amount("2.80"),
                new TransactionDate("11-10-2019"),
                new Description("Yong Tau Foo"),
                new TransactionMethod("Cash"),
                getCategorySet("Food", "School"),
                new Place("Frontier")
            ),
            new IncomeLogEntry(
                new Amount("50"),
                new TransactionDate("13-10-2019"),
                new Description("Weekly Allowance"),
                new TransactionMethod("Cash"),
                getCategorySet("Allowance"),
                new Person("Parent")
            ),
            new BorrowLogEntry(
                new Amount("1"),
                new TransactionDate("16-10-2019"),
                new Description("Dily's egg tart"),
                new TransactionMethod("Cash"),
                getCategorySet("Food"),
                new Person("Friend")
            ),
            new LendLogEntry(
                    new Amount("10"),
                    new TransactionDate("17-10-2019"),
                    new Description("Baking stuff"),
                    new TransactionMethod("PayNow"),
                    getCategorySet(),
                    new Person("Parent")
            ),
            new SpendLogEntry(
                    new Amount("2"),
                    new TransactionDate("17-10-2019"),
                    new Description("Ham & Egg sandwich"),
                    new TransactionMethod("Cash"),
                    getCategorySet(),
                    new Place("Cool Spot")
            ),
            new SpendLogEntry(
                    new Amount("0.50"),
                    new TransactionDate("21-10-2019"),
                    new Description("Soymilk"),
                    new TransactionMethod("Cash"),
                    getCategorySet(),
                    new Place("Dilys")
            ),
            new SpendLogEntry(
                    new Amount("104.26"),
                    new TransactionDate("26-10-2019"),
                    new Description("Apricot Princess CD Album"),
                    new TransactionMethod("PayPal"),
                    getCategorySet("Music"),
                    new Place("CDJapan")
            ),
            new IncomeLogEntry(
                    new Amount("300"),
                    new TransactionDate("26-10-2019"),
                    new Description("GST Voucher"),
                    new TransactionMethod("Bank credit"),
                    getCategorySet("rebate"),
                    new Person("Government")
            ),
            new BorrowLogEntry(
                    new Amount("1.80"),
                    new TransactionDate("27-10-2019"),
                    new Description("Arizona Lemon Tea Drink"),
                    new TransactionMethod("NETS"),
                    getCategorySet("tea"),
                    new Person("Sister")
            ),
            new SpendLogEntry(
                    new Amount("1"),
                    new TransactionDate("28-10-2019"),
                    new Description("HL Chocolate Milk"),
                    new TransactionMethod("Cash"),
                    getCategorySet("drink"),
                    new Place("Frontier")
            ),
            new SpendLogEntry(
                    new Amount("2.80"),
                    new TransactionDate("29-10-2019"),
                    new Description("Yong Tau Foo"),
                    new TransactionMethod("Cash"),
                    getCategorySet("Food", "School"),
                    new Place("Frontier")
            )
        };
    }

    public static ReadOnlyFinanceLog getSampleFinanceLog() {
        FinanceLog sampleFl = new FinanceLog();
        for (LogEntry sampleLogEntry : getSampleLogEntries()) {
            sampleFl.addLogEntry(sampleLogEntry);
        }
        return sampleFl;
    }

    /**
     * Returns a set of categories containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
