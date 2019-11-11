package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder extends PersonBuilder {

    public static final int DEFAULT_CUSTOMER_ID = 69;

    private int id;

    public CustomerBuilder() {
        super();
        this.id = DEFAULT_CUSTOMER_ID;
    }

    public CustomerBuilder(Customer customer) {
        super(customer);
        this.id = customer.getId();
    }

    /**
     * Sets the {@code Id} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withId(int id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Customer} that we are building.
     */
    @Override
    public CustomerBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Customer} that we are building.
     */
    @Override
    public CustomerBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Customer} that we are building.
     */
    @Override
    public CustomerBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Customer} that we are building.
     */
    @Override
    public CustomerBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Customer} that we are building.
     */
    @Override
    public CustomerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    @Override
    public Customer build() {
        return new Customer(id, super.name, super.phone,
                super.email, super.address, super.tags);
    }
}
