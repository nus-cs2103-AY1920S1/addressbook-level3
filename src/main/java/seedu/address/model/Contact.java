package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.claim.Claim;
import seedu.address.model.claim.UniqueClaimsList;
import seedu.address.model.income.Income;
import seedu.address.model.income.UniqueIncomesList;
import seedu.address.model.contact.UniqueContactsList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Contact implements ReadOnlyContact,  ReadOnlyClaim, ReadOnlyIncome{

    private final UniqueContactsList persons;
    private final UniqueClaimsList claims;
    private final UniqueIncomesList incomes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueContactsList();
        claims = new UniqueClaimsList();
        incomes = new UniqueIncomesList();
    }

    public Contact() {}

    /**
     * Creates Contact using the Contact in the {@code toBeCopied}
     */
    public Contact(ReadOnlyContact toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<seedu.address.model.contact.Contact> contacts) {
        this.persons.setContacts(contacts);
    }

    /**
     * Resets the existing data of this {@code Contact} with {@code newData}.
     */
    public void resetData(ReadOnlyContact newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    public boolean hasPerson(seedu.address.model.contact.Contact contact) {
        requireNonNull(contact);
        return persons.contains(contact);
    }

    /**
     * Adds a contact to the address book.
     * The contact must not already exist in the address book.
     */
    public void addPerson(seedu.address.model.contact.Contact p) {
        persons.add(p);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the address book.
     */
    public void setPerson(seedu.address.model.contact.Contact target, seedu.address.model.contact.Contact editedContact) {
        requireNonNull(editedContact);

        persons.setPerson(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code Contact}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(seedu.address.model.contact.Contact key) {
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
     * Removes {@code key} from this {@code Contact}.
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
     * Removes {@code income} from this {@code Contact}.
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
    public ObservableList<seedu.address.model.contact.Contact> getContactList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Claim> getClaimList() {
        return claims.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Income> getIncomeList() {
        return incomes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Contact // instanceof handles nulls
                && persons.equals(((Contact) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
