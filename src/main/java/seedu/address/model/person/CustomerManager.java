package seedu.address.model.person;

import seedu.address.model.AddressBook;

/**
 * Manages the customer list.
 * It contains the minimal set of list operations.
 */
public class CustomerManager extends AddressBook {

    public CustomerManager() {
        persons = new CustomerList();
    }

    /**
     * Retrieve customer using its unique customer id.
     *
     * @param customerId customer unique id.
     * @return Customer with the specified unique id.
     */
    public Customer getCustomer(int customerId) {
        Person foundCustomer = persons.asUnmodifiableObservableList()
                                            .stream()
                                            .filter(person -> {
                                                Customer customer = (Customer) person;
                                                return customer.getId() == customerId;
                                            })
                                            .findFirst()
                                            .get();
        return (Customer) foundCustomer;
    }

    /**
     * Checks if the customer list has a customer with {@code int customerId}.
     *
     * @param customerId customer unique id.
     */
    public boolean hasCustomer(int customerId) {
        return persons.asUnmodifiableObservableList()
                        .stream()
                        .anyMatch(person -> {
                            Customer customer = (Customer) person;
                            return customer.getId() == customerId;
                        });
    }
}
