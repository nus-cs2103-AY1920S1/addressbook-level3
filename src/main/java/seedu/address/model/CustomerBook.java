package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.UniqueCustomerList;

/**
 * Wraps all data at the customer-book level
 * Duplicates are not allowed (by .isSameCustomer comparison)
 */
public class CustomerBook implements ReadOnlyDataBook<Customer> {

    private final UniqueCustomerList customers;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        customers = new UniqueCustomerList();
    }

    public CustomerBook() {
    }

    /**
     * Creates an CustomerBook using the Customers in the {@code toBeCopied}
     */
    public CustomerBook(ReadOnlyDataBook<Customer> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the customer list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setCustomers(List<Customer> customers) {
        this.customers.setCustomers(customers);
    }

    /**
     * Resets the existing data of this {@code CustomerBook} with {@code newData}.
     */
    public void resetData(ReadOnlyDataBook<Customer> newData) {
        requireNonNull(newData);

        setCustomers(newData.getList());
    }

    //// customer-level operations

    /**
     * Returns true if a customer with the same identity as {@code customer} exists in the customer book.
     */
    public boolean hasCustomer(Customer customer) {
        requireNonNull(customer);
        return customers.contains(customer);
    }

    /**
     * Adds a customer to the customer book.
     * The customer must not already exist in the customer book.
     */
    public void addCustomer(Customer c) {
        customers.add(c);
    }

    /**
     * Replaces the given customer {@code target} in the list with {@code editedCustomer}.
     * {@code target} must exist in the customer book.
     * The customer identity of {@code editedCustomer} must not be the same as another existing customer.
     */
    public void setCustomer(Customer target, Customer editedCustomer) {
        requireNonNull(editedCustomer);

        customers.setCustomer(target, editedCustomer);
    }

    /**
     * Removes {@code key} from this {@code CustomerBook}.
     * {@code key} must exist in the customer book.
     */
    public void removeCustomer(Customer key) {
        customers.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return customers.asUnmodifiableObservableList().size() + " customers";
        // TODO: refine later
    }

    @Override
    public ObservableList<Customer> getList() {
        return customers.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomerBook // instanceof handles nulls
                && customers.equals(((CustomerBook) other).customers));
    }

    @Override
    public int hashCode() {
        return customers.hashCode();
    }
}
