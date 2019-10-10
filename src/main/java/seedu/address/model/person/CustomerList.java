package seedu.address.model.person;

/**
 * A list of customers that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */

public class CustomerList extends UniquePersonList{

    public void setCustomer(Customer target, Customer editedCustomer){
        setPerson(target, editedCustomer);
    }

    public void setCustomers(CustomerList replacement){
        setPersons(replacement);
    }

    public void addCustomer(Customer toAdd){
        add(toAdd);
    }

    public boolean containCustomer(Customer toCheck){
        return contains(toCheck);
    }

    public void removeCustomer(Customer toRemove){
        remove(toRemove);
    }

}
