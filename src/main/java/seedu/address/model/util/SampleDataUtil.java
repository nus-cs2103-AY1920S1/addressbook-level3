package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FinSec;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Id;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.income.Income;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code FinSec} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Name("John Doe"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), getTagSet("friends"), new HashSet<>())
        };
    }

    public static AutocorrectSuggestion[] getSampleSuggestions() {
        return new AutocorrectSuggestion[] {
            new AutocorrectSuggestion("add_claim n/John Doe")
        };
    }

    public static Income[] getSampleIncomes() {
        return new Income[] {
            new Income(new Description("Shirt sales"), new Amount("100"), new Date("11-11-2019"),
                    new Name("Joshua Seet"), new Phone("98279222"), getTagSet("FOP"))
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
        for (Income sampleIncome: getSampleIncomes()) {
            sampleFs.addIncome(sampleIncome);
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

    /**
     * Returns a claim set containing the list of strings given.
     */
    public static Set<Id> getClaimSet(String... strings) {
        return Arrays.stream(strings)
                .map(Id::new)
                .collect(Collectors.toSet());
    }

}
