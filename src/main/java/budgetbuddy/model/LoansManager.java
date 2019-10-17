package budgetbuddy.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.exceptions.DuplicateLoanException;
import budgetbuddy.model.loan.exceptions.LoanNotFoundException;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.person.UniquePersonList;
import budgetbuddy.model.person.exceptions.PersonNotFoundException;
import javafx.collections.ObservableList;

/**
 * Manages the loans of each person in a list of persons.
 */
public class LoansManager {

    private final UniquePersonList persons;

    /**
     * Creates a new (empty) list of persons.
     */
    public LoansManager() {
        this.persons = new UniquePersonList();
    }

    /**
     * Creates and fills a new list of persons.
     * @param persons A list of persons with which to fill the new list.
     */
    public LoansManager(List<Person> persons) {
        requireNonNull(persons);
        this.persons = new UniquePersonList(persons);
    }

    /**
     * Retrieves the list of persons.
     */
    // TODO Probably need to change this to work with the UI.
    public ObservableList<Person> getPersonsList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Adds a given loan to its specified person in the list.
     * Duplicate loans are not allowed in the list.
     * @param toAdd The loan to add.
     * @throws DuplicateLoanException If the loan already exists in the list.
     */
    public void addLoan(Loan toAdd) throws DuplicateLoanException {
        Person newPerson = toAdd.getPerson();
        if (persons.contains(newPerson)) {
            Person targetPerson = persons.get(newPerson);
            if (targetPerson.hasLoan(toAdd)) {
                throw new DuplicateLoanException();
            }
            targetPerson.addLoan(toAdd);
        } else {
            persons.add(newPerson);
        }
    }

    /**
     * Edits a person's loan to match a given loan.
     * @param editedLoan The loan to base the target loan's updated attributes on.
     */
    public void editLoan(Person targetPerson, Loan targetLoan, Loan editedLoan)
            throws PersonNotFoundException, LoanNotFoundException {
        persons.get(targetPerson).setLoan(targetLoan, editedLoan);
    }

    /**
     * Updates the status of a person's loan to that of the given loan.
     * @param updatedLoan A loan identical to the target loan except for its updated status.
     */
    public void updateLoanStatus(Person targetPerson, Loan targetLoan, Loan updatedLoan) {
        editLoan(targetPerson, targetLoan, updatedLoan);
    }

    /**
     * Deletes a person's loan, targeted using a given loan.
     * @param toDelete The loan used to identify the target loan for deletion.
     */
    public void deleteLoan(Loan toDelete) {
        if (persons.contains(toDelete.getPerson())) {
            Person targetPerson = persons.get(toDelete.getPerson());
            targetPerson.deleteLoan(toDelete);
            if (!targetPerson.hasLoansRemaining()) {
                persons.remove(targetPerson);
            }
        } else {
            throw new PersonNotFoundException();
        }
    }

    /**
     * Edits a person to match the given person.
     * @param editedPerson The person to base the target person's updated attributes on.
     */
    public void editPerson(Person editedPerson) {
        if (persons.contains(editedPerson)) {
            Person targetPerson = persons.get(editedPerson);
            persons.setPerson(targetPerson, editedPerson);
        } else {
            throw new PersonNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoansManager)) {
            return false;
        }

        LoansManager otherLoansManager = (LoansManager) other;
        return persons.equals(otherLoansManager.getPersonsList());
    }
}
