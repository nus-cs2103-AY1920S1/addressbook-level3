package seedu.address.testutil;

import seedu.address.model.CustomerBook;
import seedu.address.model.customer.Customer;

/**
 * A utility class to help with building CustomerBook objects.
 * Example usage: <br>
 *     {@code CustomerBook ab = new CustomerBookBuilder().withCustomer("John", "Doe").build();}
 */
public class CustomerBookBuilder {

    private CustomerBook customerBook;

    public CustomerBookBuilder() {
        customerBook = new CustomerBook();
    }

    public CustomerBookBuilder(CustomerBook customerBook) {
        this.customerBook = customerBook;
    }

    /**
     * Adds a new {@code Customer} to the {@code CustomerBook} that we are building.
     */
    public CustomerBookBuilder withCustomer(Customer customer) {
        customerBook.addCustomer(customer);
        return this;
    }

    public CustomerBook build() {
        return customerBook;
    }
}
