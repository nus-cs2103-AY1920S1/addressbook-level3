package seedu.moneygowhere.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.moneygowhere.model.ReadOnlySpendingBook;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.reminder.Reminder;
import seedu.moneygowhere.model.reminder.ReminderMessage;
import seedu.moneygowhere.model.spending.Cost;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Name;
import seedu.moneygowhere.model.spending.Remark;
import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.model.tag.Tag;

/**
 * Contains utility methods for populating {@code SpendingBook} with sample data.
 */
public class SampleDataUtil {

    public static Spending[] getSampleSpendings() {
        return new Spending[]{
            new Spending(new Name("Chicken rice"), new Date("20/10/2019"), new Remark("Good food"),
                new Cost("4.00"),
                getTagSet("food")),
            new Spending(new Name("Taxi"), new Date("21/10/2019"), new Remark("I was late for school"),
                new Cost("25.50"),
                getTagSet("transport", "regret")),
            new Spending(new Name("Math textbook"), new Date("22/10/2019"), new Remark("Wasted my money!"),
                new Cost("32.00"),
                getTagSet("education", "regret")),
            new Spending(new Name("Gold class movie ticket"), new Date("23/10/2019"), new Remark("GV ticket"),
                new Cost("20"),
                getTagSet("entertainment")),
            new Spending(new Name("Watch"), new Date("24/10/2019"), new Remark("Birthday gift to Jonathan"),
                new Cost("20"),
                getTagSet("entertainment", "aesthetics")),
            new Spending(new Name("Concert ticket"), new Date("25/10/2019"), new Remark("Favourite band"),
                new Cost("180"),
                getTagSet("entertainment")),
            new Spending(new Name("Transport card fare"), new Date("31/10/2019"), new Remark("I would rather walk"),
                new Cost("15"),
                getTagSet("transport")),
            new Spending(new Name("Tissue"), new Date("31/10/2019"), new Remark(""),
                new Cost("0.50"),
                getTagSet()),
            new Spending(new Name("Cap"), new Date("30/10/2019"), new Remark("It says 3 on it"),
                new Cost("5.00"),
                getTagSet()),
            new Spending(new Name("Cap"), new Date("30/10/2019"), new Remark("It says 5 on it"),
                new Cost("5.00"),
                getTagSet()),
            new Spending(new Name("cap"), new Date("30/10/2019"), new Remark("got it cheap"),
                new Cost("2.50"),
                getTagSet()),
            new Spending(new Name("Cappuccino"), new Date("31/10/2019"), new Remark("Too tired"),
                new Cost("5.00"),
                getTagSet("drink", "daily")),
            new Spending(new Name("Laksa"), new Date("31/10/2019"), new Remark(""),
                new Cost("5.00"),
                getTagSet("food", "daily")),
            new Spending(new Name("Bubbletea large"), new Date("20/10/2019"), new Remark(""),
                new Cost("5.00"),
                getTagSet("food")),
            new Spending(new Name("balloon"), new Date("20/10/2019"), new Remark("friend's birthday"),
                new Cost("2.00"),
                getTagSet("gift")),
            new Spending(new Name("NUS Computing Shirt"), new Date("20/10/2019"), new Remark(""),
                new Cost("12.75"),
                getTagSet("clothes")),
            new Spending(new Name("NUS Foolscap Paper"), new Date("20/10/2019"), new Remark(""),
                new Cost("2.50"),
                getTagSet("acad")),
            new Spending(new Name("Phone case"), new Date("30/09/2019"), new Remark(""),
                new Cost("12"),
                getTagSet("accessories")),
            new Spending(new Name("Laptop sticker"), new Date("30/09/2019"), new Remark(""),
                new Cost("3"),
                getTagSet("accessories")),
            new Spending(new Name("Sephora makeup"), new Date("30/09/2019"), new Remark("for my friend"),
                new Cost("75"),
                getTagSet("gift")),
            new Spending(new Name("water"), new Date("29/09/2019"), new Remark("overseas bottled water safer"),
                new Cost("1"),
                getTagSet("daily")),
            new Spending(new Name("Alcohol"), new Date("29/09/2019"), new Remark("can be used for many things"),
                new Cost("10"),
                getTagSet("medical")),
            new Spending(new Name("plaster"), new Date("28/09/2019"), new Remark("fell down"),
                new Cost("10"),
                getTagSet("medical"))
        };
    }

    public static Reminder[] getSampleReminders() {
        return new Reminder[] {
            new Reminder(new Date("yesterday"), new ReminderMessage("Pay school fees")),
            new Reminder(new Date("5 days later"), new ReminderMessage("Check bank balance")),
            new Reminder(new Date("7 days later"), new ReminderMessage("Repay my friend $2")),
            new Reminder(new Date("30 days later"), new ReminderMessage("Pay phone bills")),
            new Reminder(new Date("30 days later"), new ReminderMessage("Pay rent"))
        };
    }

    public static ReadOnlySpendingBook getSampleSpendingBook() {
        SpendingBook sampleAb = new SpendingBook();
        for (Spending sampleSpending : getSampleSpendings()) {
            sampleAb.addSpending(sampleSpending);
        }
        for (Reminder sampleReminder : getSampleReminders()) {
            sampleAb.addReminder(sampleReminder);
        }
        return sampleAb;
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
