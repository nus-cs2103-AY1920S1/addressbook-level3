package seedu.address.model;

import static java.util.Objects.requireNonNull;
import java.util.List;
import javafx.collections.ObservableList;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.UniqueClaimsList;
import seedu.address.model.income.Income;
import seedu.address.model.income.UniqueIncomeList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueClaimsList claims;
    private final UniqueIncomeList incomes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        claims = new UniqueClaimsList();
        incomes = new UniqueIncomeList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
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
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
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

    /**
     * Returns true if a claim with the same identity as {@code claim} exists in the address book.
     */
    public boolean hasClaim(Claim claim) {
        requireNonNull(claim);
        return claims.contains(claim);
    }

    /**
     * Adds a claim to the address book.
     * The claim must not already exist in the address book.
     */
    public void addClaim(Claim c) {
        claims.add(c);
    }

    /**
     * Replaces the given claim {@code target} in the list with {@code editedClaim}.
     * {@code target} must exist in the address book.
     * The claim identity of {@code editedClaim} must not be the same as another existing claim in the address book.
     */
    public void setClaim(Claim target, Claim editedClaim) {
        requireNonNull(editedClaim);

        claims.setClaim(target, editedClaim);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClaim(Claim key) {
        claims.remove(key);
    }

    /**
     * Returns true if an income with the same identity as {@code income} exists in the address book.
     */
    public boolean hasIncome(Income income) {
        requireNonNull(income);
        return incomes.contains(income);
    }

    /**
     * Adds an income to the address book.
     * The income must not already be existing in the address book.
     */
    public void addIncome(Income income) {
        incomes.add(income);
    }

    /**
     * Replaces the income {@code target} in the list with {@code editedIncome}.
     * {@code target} must exist in the address book.
     * The income identity of {@code editedClaim} must not be the same as another existing income in the address book.
     */
    public void setIncome(Income target, Income editedIncome) {
        requireNonNull(editedIncome);

        incomes.setIncome(target, editedIncome);
    }

    /**
     * Removes {@code income} from this {@code AddressBook}.
     * {@code income} must already exist in the address book.
     */
    public void removeIncome(Income income) {
        incomes.remove(income);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
