package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Entry[] getSampleEntries() {
        return new Entry[] {
            new Expense(new Category("Food"), new Description("mala at deck"), new Date("2019 09 09"), new Amount(5.40),
                    getTagSet("mala")),
            new Expense(new Category("Food"), new Description("chicken rice"), new Date("2019 09 09"), new Amount(3.50),
                    getTagSet("mala")),
            new Income(new Category("Work"), new Description("october salary"), new Date("2019 10 11"), new Amount(3000),
                    getTagSet("salary"))
        };
    }

    public static Category[] getSampleCategories() {
        return new Category[] {new Category("Food"), new Category("Travel"), new Category("Family"),
                new Category("Bills"), new Category("Entertainment"), new Category("Transport"),
                new Category("Shopping"), new Category("HealthCare"), new Category("Groceries"),
                new Category("Rent")};
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Entry sampleEntry : getSampleEntries()) {
            sampleAb.addEntry(sampleEntry);
        }
        for (Category sampleCategory : getSampleCategories()) {
            sampleAb.addCategory(sampleCategory);
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
