package seedu.address.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.customer.exceptions.CustomerNotFoundException;
import seedu.address.model.customer.exceptions.DuplicateCustomerException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Customer#isSameCustomer(Customer)}.
 * As such, adding and updating of
 * persons uses Customer#isSameCustomer(Customer) for equality so as to ensure that the person
 * being added or updated is
 * unique in terms of identity in the UniqueCustomerList. However, the removal of a person uses
 * Customer#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see Customer#isSameCustomer(Customer)
 */
public class UniqueCustomerList implements Iterable<Customer> {

    private final ObservableList<Customer> internalList = FXCollections.observableArrayList();
    private final ObservableList<Customer> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Customer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCustomer);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Customer toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCustomerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     * @param target
     * @param editedCustomer
     */
    public void setPerson(Customer target, Customer editedCustomer) {
        requireAllNonNull(target, editedCustomer);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CustomerNotFoundException();
        }

        if (!target.isSameCustomer(editedCustomer) && contains(editedCustomer)) {
            throw new DuplicateCustomerException();
        }

        internalList.set(index, editedCustomer);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     * @param toRemove
     */
    public void remove(Customer toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CustomerNotFoundException();
        }
    }

    public void setPersons(UniqueCustomerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code customers}.
     * {@code customers} must not contain duplicate customers.
     */
    public void setPersons(List<Customer> customers) {
        requireAllNonNull(customers);
        if (!customersAreUnique(customers)) {
            throw new DuplicateCustomerException();
        }

        internalList.setAll(customers);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Customer> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Customer> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCustomerList // instanceof handles nulls
                        && internalList.equals(((UniqueCustomerList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code customers} contains only unique customers.
     */
    private boolean customersAreUnique(List<Customer> customers) {
        for (int i = 0; i < customers.size() - 1; i++) {
            for (int j = i + 1; j < customers.size(); j++) {
                if (customers.get(i).isSameCustomer(customers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
