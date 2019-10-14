package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.address.model.person.loan.Loan;
import seedu.address.model.person.loan.LoanList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;

    // Data fields
    private final Set<Tag> tags = new HashSet<>(); // TODO Get rid of Person's tags attribute.
    private final LoanList loans = new LoanList();

    /**
     * Creates a person with the loans in the {@code toBeCopied}.
     * @param name The name of the person.
     * @param toBeCopied The person's loans.
     */
    public Person(Name name, LoanList toBeCopied) {
        requireAllNonNull(name, toBeCopied);
        this.name = name;
        resetLoans(toBeCopied);
    }

    // TODO Get rid of obsolete constructor (person no longer has tags).
    public Person(Name name, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    // TODO Get rid of Person's getTags() method.
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Retrieves a loan from this person's list equivalent to the given loan.
     * @param toGet The equivalent loan (identical attributes to the target loan).
     * @return The retrieved loan.
     */
    public Loan getLoan(Loan toGet) {
        requireNonNull(toGet);
        return loans.get(toGet);
    }

    public ObservableList<Loan> getLoans() {
        return loans.asUnmodifiableObservableList();
    }

    /**
     * Checks if the person's list of loans is empty.
     * @return True if the list is empty, false otherwise.
     */
    public boolean hasLoansRemaining() {
        return loans.isEmpty();
    }

    /**
     * Resets the existing loans of this person with the loans of {@code replacementList}.
     * @param replacementList The list of loans to replace the existing loans with.
     */
    public void resetLoans(LoanList replacementList) {
        requireNonNull(replacementList);
        this.loans.replaceList(replacementList.asUnmodifiableObservableList());
    }

    /**
     * Checks if a given loan is under this person.
     * @param loan The loan to be checked.
     * @return True if the given loan is in this person's loan list, false otherwise.
     */
    public boolean hasLoan(Loan loan) {
        requireNonNull(loan);
        return loans.contains(loan);
    }

    /**
     * Adds a loan to this person's loan list.
     * @param loan The loan to be added.
     */
    public void addLoan(Loan loan) {
        requireNonNull(loan);
        loans.add(loan);
    }

    /**
     * Replaces a target loan in this peronn's loan list with {@code editedLoan}.
     * @param target The loan to be replaced.
     * @param editedLoan The loan to replace the target loan with.
     */
    public void setLoan(Loan target, Loan editedLoan) {
        requireAllNonNull(target, editedLoan);
        loans.setLoan(target, editedLoan);
    }

    /**
     * Deletes a given loan from this person's list.
     * @param loan The loan to be deleted.
     */
    public void deleteLoan(Loan loan) {
        loans.delete(loan);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getLoans().equals(getLoans());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, loans);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}
