package seedu.address.model;

import javafx.collections.ObservableList;

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
     * Returns an unmodifiable view of the customer list.
     * This list will not contain any duplicate customers.
     *
     * @return Customer list without duplicate customers.
     */
    public ObservableList<Customer> getCustomerList() {
        return super.getPersonList();
    }

    /**
     * Checks if the customer list has a customer with {@code Customer customer}.
     *
     * @param customer customer to be checked
     */
    public boolean hasCustomer(Customer customer) {
        return super.hasPerson(customer);
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

    public void setCustomer(Customer customerToEdit, Customer editedCustomer) {
        super.setPerson(customerToEdit, editedCustomer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerManager otherObject = (CustomerManager) o;
        return getCustomerList().equals(otherObject.getCustomerList());
    }
}
