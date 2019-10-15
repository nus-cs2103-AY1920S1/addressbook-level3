package seedu.deliverymans.model.database;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.deliverymans.model.customer.Customer;
import seedu.deliverymans.model.customer.UniqueCustomerList;

/**
 * Wraps all Customer data at the customer-database level
 * Duplicates are not allowed (by .isSameCustomer comparison)
 */
public class CustomerDatabase implements ReadOnlyCustomerDatabase {

    private final UniqueCustomerList customers;

    {
        customers = new UniqueCustomerList();
    }

    public CustomerDatabase() {}

    /**
     * Creates a CustomerDatabase using the Customers in the {@code toBeCopied}
     */
    public CustomerDatabase(ReadOnlyCustomerDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers.setCustomers(customers);
    }

    /**
     * Resets the existing data of this {@code CustomerDatabase} with {@code newData}.
     */
    public void resetData(ReadOnlyCustomerDatabase newData) {
        requireNonNull(newData);

        setCustomers(newData.getCustomerList());
    }

    //// customer-level operations

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the customer database.
     */
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customers.contains(customer);
    }

    /**
     * Adds a customer to the customer database.
     * The customer must not already exist in the customer database.
     */
    public void addCustomer(Customer c) {
        customers.add(c);
    }

    /**
     * Replaces the given customer {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the customer database.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer in the customer
     * database.
     */
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireNonNull(editedCustomer);
        customers.setCustomer(target, editedCustomer);
    }

    /**
     * Removes {@code key} from this {@code CustomerDatabase}.
     * {@code key} must exist in the customer database.
     */
    public void removeCustomer(Customer key) {
        customers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return customers.asUnmodifiableObservableList().size() + " customers";
    }

    @Override
    public ObservableList<Customer> getCustomerList() {
        return customers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof CustomerDatabase
                && customers.equals(((CustomerDatabase) other).customers));
    }

    @Override
    public int hashCode() {
        return customers.hashCode();
    }
}
