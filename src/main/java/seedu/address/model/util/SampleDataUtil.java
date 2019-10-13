package seedu.address.model.util;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.BankAccount;
import seedu.address.model.ReadOnlyBankAccount;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.OutTransaction;
import seedu.address.model.transaction.Transaction;

/**
 * Contains utility methods for populating {@code BankAccount} with sample data.
 */
public class SampleDataUtil {
    public static Transaction[] getSampleTransactions() {
        return new Transaction[] {
                new InTransaction(new Amount(100), new Date(System.currentTimeMillis())),
                new OutTransaction(new Amount(44.44), new Date(System.currentTimeMillis())),
                new OutTransaction(new Amount(23.3), new Date(System.currentTimeMillis())),
                new InTransaction(new Amount(34.01), new Date(System.currentTimeMillis())),
                new OutTransaction(new Amount(9.99), new Date(System.currentTimeMillis())),
        };
    }

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    /**
     * Returns a sample BankAccount containing sample transactions.
     */
    public static ReadOnlyBankAccount getSampleBankAccount() {
        BankAccount sampleBankAccount = new BankAccount();
        for (Transaction sampleTxn : getSampleTransactions()) {
            sampleBankAccount.addTransaction(sampleTxn);
        }
        return sampleBankAccount;
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
