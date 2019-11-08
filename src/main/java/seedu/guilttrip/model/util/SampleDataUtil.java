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
            new Expense(new Category("Food", CategoryType.EXPENSE), new Description("mala at deck"),
                    new Date("2019 09 09"), new Amount("5.40"), getTagSet("mala")),
            new Expense(new Category("Food", CategoryType.EXPENSE), new Description("chicken rice"),
                    new Date("2019 09 09"), new Amount("3.50"), getTagSet("mala")),
            new Income(new Category("Work", CategoryType.INCOME), new Description("october salary"),
                    new Date("2019 10 11"), new Amount("3000"), getTagSet("salary"))
        };
    }

    public static Category[] getSampleCategories() {
        return new Category[] {
            new Category("Food", CategoryType.EXPENSE),
            new Category("Travel", CategoryType.EXPENSE),
            new Category("Family", CategoryType.EXPENSE),
            new Category("Bills", CategoryType.EXPENSE),
            new Category("Entertainment", CategoryType.EXPENSE),
            new Category("Transport", CategoryType.EXPENSE),
            new Category("Shopping", CategoryType.EXPENSE),
            new Category("HealthCare", CategoryType.EXPENSE),
            new Category("Groceries", CategoryType.EXPENSE),
            new Category("Rent", CategoryType.EXPENSE),
            new Category("Business", CategoryType.INCOME),
            new Category("Salary", CategoryType.INCOME),
            new Category("Gifts", CategoryType.INCOME),
            new Category("Loans", CategoryType.INCOME)
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
