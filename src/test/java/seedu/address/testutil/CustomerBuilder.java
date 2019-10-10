package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.customer.ContactNumber;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.CustomerName;
import seedu.address.model.customer.Email;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Customer objects.
 */
public class CustomerBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";

    private CustomerName customerName;
    private ContactNumber contactNumber;
    private Email email;
    private Set<Tag> tags;

    public CustomerBuilder() {
        customerName = new CustomerName(DEFAULT_NAME);
        contactNumber = new ContactNumber(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CustomerBuilder with the data of {@code customerToCopy}.
     */
    public CustomerBuilder(Customer customerToCopy) {
        customerName = customerToCopy.getCustomerName();
        contactNumber = customerToCopy.getContactNumber();
        email = customerToCopy.getEmail();
        tags = new HashSet<>(customerToCopy.getTags());
    }

    /**
     * Sets the {@code CustomerName} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withName(String customerName) {
        this.customerName = new CustomerName(customerName);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Customer} that we are building.
     */
    public CustomerBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code ContactNumber} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withContactNumber(String contactNumber) {
        this.contactNumber = new ContactNumber(contactNumber);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Customer} that we are building.
     */
    public CustomerBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Customer build() {
        return new Customer(customerName, contactNumber, email, tags);
    }

}
