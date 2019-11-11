package seedu.address.testutil;

import seedu.address.model.DataBook;
import seedu.address.model.customer.Customer;

/**
 * A utility class to help with building {@code Customer} {@code DataBook}.
 * Example usage: <br>
 *     {@code DataBook<Customer> ab = new CustomerBookBuilder().withCustomer("John", "Doe").build();}
 */
public class CustomerBookBuilder {

    private DataBook<Customer> customerBook;

    public CustomerBookBuilder() {
        customerBook = new DataBook<>();
    }

    public CustomerBookBuilder(DataBook<Customer> customerBook) {
        this.customerBook = customerBook;
    }

    /**
     * Adds a new {@code Customer} to the {@code DataBook} that we are building.
     */
    public CustomerBookBuilder withCustomer(Customer customer) {
        customerBook.add(customer);
        return this;
    }

    public DataBook<Customer> build() {
        return customerBook;
    }
}
