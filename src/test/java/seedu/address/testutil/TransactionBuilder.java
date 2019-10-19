package seedu.address.testutil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Transaction objects.
 */
public class TransactionBuilder {

    public static final String DEFAULT_AMOUNT = "1";
    public static final String DEFAULT_DATE = "1";

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Amount amount;
    private Date date;
    private Person peopleInvolved;
    private Set<Tag> tags;

    public TransactionBuilder() {
        amount = new Amount(Double.parseDouble(DEFAULT_AMOUNT));
        // date = new Date(DEFAULT_DATE);
        date = new Date();
        peopleInvolved = new Person(
                new Name(DEFAULT_NAME),
                new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL),
                new Address(DEFAULT_ADDRESS),
                new HashSet<>());
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TransactionBuilder(Transaction transactionToCopy) {
        amount = transactionToCopy.getAmount();
        date = transactionToCopy.getDate();
        peopleInvolved = transactionToCopy.getPeopleInvolved();
        tags = new HashSet<>(transactionToCopy.getTags());
    }

    /**
     * Sets the {@code amount} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withAmount(String amount) {
        this.amount = new Amount(Double.parseDouble(amount));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Transaction} that we are building.
     */
    public TransactionBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public TransactionBuilder withDate(String date) {
        this.date = new Date();
        return this;
    }

    // TODO: EDIT THIS METHOD

    /**
     * Sets the {@code PeopleInvolved} of the {@code Transaction} that we are building.
     */
    public TransactionBuilder withPeopleInvolved(String peopleInvolved) {
        this.peopleInvolved = new Person(
                new Name(DEFAULT_NAME),
                new Phone(DEFAULT_PHONE),
                new Email(DEFAULT_EMAIL),
                new Address(DEFAULT_ADDRESS),
                new HashSet<>());
        return this;
    }

    // TODO: Change constructor
    public Transaction build() {
        return new InTransaction(amount, date);
    }

}
