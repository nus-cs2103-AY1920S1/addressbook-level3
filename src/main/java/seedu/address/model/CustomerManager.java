package seedu.address.model;

import seedu.address.model.person.Customer;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Manages the customer list.
 * It contains the minimal set of list operations.
 */
public class CustomerManager extends EntityManager<Customer> {

    public CustomerManager() {
        super();
    }

    /**
     * Retrieve customer using its unique customer id.
     *
     * @param customerId customer unique id.
     * @return Customer with the specified unique id.
     */
    public Customer getCustomer(int customerId) {
        return getPersonList()
                .stream()
                .filter(customer -> customer.getId() == customerId)
                .findFirst()
                .orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Checks if the customer list has a customer with {@code int customerId}.
     *
     * @param customerId customer unique id.
     */
    public boolean hasCustomer(int customerId) {
        return getPersonList()
                .stream()
                .anyMatch(customer -> customer.getId() == customerId);
    }
}
