package seedu.guilttrip.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.guilttrip.model.GuiltTrip;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Entry;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;
import seedu.guilttrip.model.tag.Tag;

/**
 * Contains utility methods for populating {@code GuiltTrip} with sample data.
 */
public class SampleDataUtil {
    public static Entry[] getSampleEntries() {
        return new Entry[] {
            new Expense(new Category("Food", "Expense"), new Description("mala at deck"),
                    new Date("2019 09 09"), new Amount("5.40"), getTagSet("mala")),
            new Expense(new Category("Food", "Expense"), new Description("chicken rice"),
                    new Date("2019 09 09"), new Amount("3.50"), getTagSet("mala")),
            new Income(new Category("Work", "Income"), new Description("october salary"),
                    new Date("2019 10 11"), new Amount("3000"), getTagSet("salary"))
        };
    }

    public static Category[] getSampleCategories() {
        return new Category[] {
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
     * Adds a default set of Categories if the addressBook is empty.
     */
    public static ReadOnlyGuiltTrip getSampleAddressBook() {
        GuiltTrip sampleAb = new GuiltTrip(true);
        for (Entry sampleEntry : getSampleEntries()) {
            sampleAb.addEntry(sampleEntry);
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
