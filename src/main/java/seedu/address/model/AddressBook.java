package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.binitem.UniqueBinItemList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.UniquePolicyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniquePolicyList policies;
    private final UniqueBinItemList binItems;

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
        binItems = new UniqueBinItemList();
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
     * Replaces the contents of the binItems list with {@code items}.
     * {@code items} must not contain duplicate BinItems.
     */
    public void setBinItems(List<BinItem> items) {
        this.binItems.setBinItems(items);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setPolicies(newData.getPolicyList());
        setBinItems(newData.getBinItemList());
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

    /**
     * Returns true if a policy with the same policy name as {@code policyName} exists in the address book.
     */
    public boolean hasPolicyWithName(PolicyName policyName) {
        requireNonNull(policyName);
        return policies.containsByName(policyName);
    }

    public Policy getPolicy(Policy policy) {
        requireNonNull(policy);
        return policies.getPolicy(policy);
    }

    public Policy getPolicyWithName(PolicyName policyName) {
        return policies.getPolicyWithName(policyName);
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

    //// BinItem-level operations

    /**
     * Returns true if a BinItem with the same identity as {@code binItem} exists in the bin.
     */
    public boolean hasBinItem(BinItem binItem) {
        requireNonNull(binItem);
        return binItems.contains(binItem);
    }

    /**
     * Adds a BinItem to the bin.
     * The BinItem must not already exist in the bin.
     */
    public void addBinItem(BinItem binItem) {
        binItems.add(binItem);
    }

    /**
     * Replaces the BinItem {@code target} in the list with {@code editedBinItem}.
     * {@code target} must exist in the list.
     * The BinItem identity of {@code editedBinItem} must not be the same as another existing BinItem in the list.
     */
    public void setBinItem(BinItem target, BinItem editedBinItem) {
        requireNonNull(editedBinItem);
        binItems.setBinItem(target, editedBinItem);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeBinItem(BinItem key) {
        binItems.remove(key);
    }

    /**
     * Permanently destroys all BinItems that have passed their expiryDate.
     */
    public void binCleanUp() {
        ArrayList<BinItem> cleanList = new ArrayList<>();
        for (BinItem b : binItems) {
            if (!b.isExpired()) {
                cleanList.add(b);
            }
        }
        binItems.setBinItems(cleanList);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons"
                + policies.asUnmodifiableObservableList().size() + " policies"
                + binItems.asUnmodifiableObservableList().size() + " items";
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Policy> getPolicyList() {
        return policies.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<BinItem> getBinItemList() {
        return binItems.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && policies.equals(((AddressBook) other).policies)
                && binItems.equals(((AddressBook) other).binItems));
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, policies, binItems);
    }
}
