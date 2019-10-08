package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

import java.util.List;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class QueueList {

    private final UniquePersonList patients;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        patients = new UniquePersonList();
    }

    public QueueList() {

    }

    public QueueList(QueueList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void resetData(QueueList newData) {
        requireNonNull(newData);

        setPatients(newData.getPatientList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPatient(Person patient) {
        requireNonNull(patient);
        return patients.contains(patient);
    }

    /**
     * Adds a person to the queue
     * The person must not already exist in the queue.
     */
    public void addPatient(Person p) {
        patients.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedpatient) {
        requireNonNull(editedpatient);
        patients.setPerson(target, editedpatient);
    }

    public void setPatients(List<Person> patients) {
        this.patients.setPersons(patients);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePatient(Person key) {
        patients.remove(key);
    }

    public Person getFirstPatient() {
        return patients.getFirst();
    }

    public void removePatient(int index) {
        patients.remove(index);
    }

    public void poll() {
        patients.remove(0);
    }

    public int size() {
        return patients.size();
    }

    //// util methods

    public ObservableList<Person> getPatientList() {
        return patients.asUnmodifiableObservableList();
    }

    @Override
    public String toString() {
        return patients.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public int hashCode() {
        return patients.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QueueList // instanceof handles nulls
                && patients.equals(((QueueList) other).patients));
    }
}
