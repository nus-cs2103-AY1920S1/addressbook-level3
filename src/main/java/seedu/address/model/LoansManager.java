package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.loan.Loan;

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
    public ObservableList<Person> viewLoans() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Adds a given loan to its specified person in the list.
     * @param toAdd The loan to add.
     */
    public void addLoan(Loan toAdd) {
        Person newPerson = toAdd.getPerson();
        if (persons.contains(newPerson)) {
            persons.get(newPerson).addLoan(toAdd);
        } else {
            persons.add(newPerson);
        }
    }

    /**
     * Edits a person's loan to match a given loan.
     * @param editedLoan The loan to base the target loan's updated attributes on.
     */
    public void editLoan(Loan editedLoan) {
        if (persons.contains(editedLoan.getPerson())) {
            Person targetPerson = persons.get(editedLoan.getPerson());
            targetPerson.setLoan(targetPerson.getLoan(editedLoan), editedLoan);
        } else {
            // TODO Handle loan not found action (make an exception).
        }
    }

    /**
     * Deletes a person's loan, targeted using a given loan.
     * @param toDelete The loan used to identify the target loan for deletion.
     */
    public void deleteLoan(Loan toDelete) {
        if (persons.contains(toDelete.getPerson())) {
            Person targetPerson = persons.get(toDelete.getPerson());
            targetPerson.deleteLoan(targetPerson.getLoan(toDelete));
            if (!targetPerson.hasLoansRemaining()) {
                persons.remove(targetPerson);
            }
        } else {
            // TODO Handle loan not found action (make an exception).
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
}
