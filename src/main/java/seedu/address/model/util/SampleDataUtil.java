package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FinSec;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FinSec} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Name("John Doe"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends"), new HashSet<>())
        };
    }

    public static AutocorrectSuggestion[] getSampleSuggestions() {
        return new AutocorrectSuggestion[] {
            new AutocorrectSuggestion("goto contacts"),
            new AutocorrectSuggestion("goto incomes"),
            new AutocorrectSuggestion("goto claims"),
            new AutocorrectSuggestion("add_contact"),
            new AutocorrectSuggestion("add_income"),
            new AutocorrectSuggestion("add_claim"),
            new AutocorrectSuggestion("exit"),
            new AutocorrectSuggestion("help"),
            new AutocorrectSuggestion("edit_contact"),
            new AutocorrectSuggestion("edit_claim"),
            new AutocorrectSuggestion("edit_income"),
            new AutocorrectSuggestion("delete_contact"),
            new AutocorrectSuggestion("delete_claim"),
            new AutocorrectSuggestion("delete_income"),
            new AutocorrectSuggestion("n/john doe")

        };
    }

    public static ReadOnlyFinSec getSampleFinSec() {
        FinSec sampleFs = new FinSec();
        for (Contact sampleContact : getSampleContacts()) {
            sampleFs.addContact(sampleContact);
        }
        for (AutocorrectSuggestion sampleSuggestion : getSampleSuggestions()) {
            sampleFs.addAutocorrectSuggestion(sampleSuggestion);
        }
        return sampleFs;
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
