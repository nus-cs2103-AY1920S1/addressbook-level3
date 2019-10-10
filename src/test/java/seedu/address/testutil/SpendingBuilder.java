package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.spending.Address;
import seedu.address.model.spending.Email;
import seedu.address.model.spending.Name;
import seedu.address.model.spending.Phone;
import seedu.address.model.spending.Spending;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class SpendingBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public SpendingBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public SpendingBuilder(Spending spendingToCopy) {
        name = spendingToCopy.getName();
        phone = spendingToCopy.getPhone();
        email = spendingToCopy.getEmail();
        address = spendingToCopy.getAddress();
        tags = new HashSet<>(spendingToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Spending} that we are building.
     */
    public SpendingBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Spending} that we are building.
     */
    public SpendingBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public Spending build() {
        return new Spending(name, phone, email, address, tags);
    }

}
