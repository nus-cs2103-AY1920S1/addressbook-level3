package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.UniquePolicyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePolicyList policies;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        policies = new UniquePolicyList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Policies in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the policies list with {@code policies}.
     * {@code policies} must not contain duplicate policies.
     */
    public void setPolicies(List<Policy> policies) {
        this.policies.setPolicies(policies);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setPolicies(newData.getPolicyList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    public Person getPerson(Person person) {
        requireNonNull(person);
        return persons.getPerson(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// policy-level operations

    /**
     * Returns true if a policy with the same identity as {@code policy} exists in the address book.
     */
    public boolean hasPolicy(Policy policy) {
        requireNonNull(policy);
        return policies.contains(policy);
    }

    public Policy getPolicy(Policy policy) {
        requireNonNull(policy);
        return policies.getPolicy(policy);
    }

    /**
     * Adds a policy to the address book.
     * The policy must not already exist in the address book.
     */
    public void addPolicy(Policy p) {
        policies.add(p);
    }

    /**
     * Replaces the given policy {@code target} in the list with {@code editedPolicy}.
     * {@code target} must exist in the address book.
     * The policy identity of {@code editedPolicy} must not be the same as another existing policy in the address book.
     */
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireNonNull(editedPolicy);

        policies.setPolicy(target, editedPolicy);
    }

    /* Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePolicy(Policy key) {
        policies.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: Need to refactor for policies list!!
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Policy> getPolicyList() {
        return policies.asUnmodifiableObservableList();
    }

    // Todo: refactor for policies
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    // Todo: refactor for policies
    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
