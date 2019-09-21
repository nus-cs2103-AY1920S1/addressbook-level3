package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.expense.*;
import seedu.address.model.expense.Price;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_DESCRIPTION = "Alice Pauline";
    public static final String DEFAULT_PRICE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Description description;
    private Price price;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public PersonBuilder() {
        description = new Description(DEFAULT_DESCRIPTION);
        price = new Price(DEFAULT_PRICE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Expense expenseToCopy) {
        description = expenseToCopy.getDescription();
        price = expenseToCopy.getPrice();
        email = expenseToCopy.getEmail();
        address = expenseToCopy.getAddress();
        tags = new HashSet<>(expenseToCopy.getTags());
    }

    /**
     * Sets the {@code Description} of the {@code Person} that we are building.
     */
    public PersonBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code Person} that we are building.
     */
    public PersonBuilder withPrice(String price) {
        this.price = new Price(price);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Expense build() {
        return new Expense(description, price, email, address, tags);
    }

}
