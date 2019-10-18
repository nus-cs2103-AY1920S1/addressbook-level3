package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonHasOngoingVisitException;
import seedu.address.model.visit.Visit;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private static final Pair<Integer, Integer> NO_ONGOING_PATIENT_AND_VISIT_VAL = new Pair<>(-1, -1);

    private final UniquePersonList persons;
    private Pair<Integer, Integer> pairOfOngoingPatAndVisitIndexes = NO_ONGOING_PATIENT_AND_VISIT_VAL;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
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
        setPairOfOngoingPatAndVisitIndexes(newData.getIndexPairOfOngoingPatientAndVisit());
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
     * Returns index of person.
     */
    public int indexOfPerson(Person person) {
        requireNonNull(person);
        return persons.indexOf(person);
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
     * Removes {@code key} from this {@code AddressBook}. Also updates currentPersonAndVisit if needed.
     * {@code key} must exist in the address book and must not have an ongoing visit.
     */
    public void removePerson(Person key) {
        requireNonNull(key);
        //If no ongoing visit, just remove
        if (pairOfOngoingPatAndVisitIndexes.equals(NO_ONGOING_PATIENT_AND_VISIT_VAL)) {
            persons.remove(key);
        } else {
            Optional<Visit> optionalVisit = getOngoingVisit();
            assert optionalVisit.isPresent();
            Person currentPatient = optionalVisit.get().getPatient();

            if (currentPatient.equals(key)) {
                //Code should have prevented this from reaching this stage
                throw new PersonHasOngoingVisitException();
            } else {
                //Remove and update
                persons.remove(key);
                setPairOfOngoingPatAndVisitIndexes(new Pair<>(indexOfPerson(currentPatient),
                        pairOfOngoingPatAndVisitIndexes.getValue()));
            }
        }
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

    /**
     * Get optional pair of current person and visit if there is an ongoing visit.
     */
    public Optional<Visit> getOngoingVisit() {
        if (pairOfOngoingPatAndVisitIndexes.getKey() == -1 || pairOfOngoingPatAndVisitIndexes.getValue() != -1) {
            return Optional.empty();
        }
        Optional<Person> patient = persons.getByIndex(pairOfOngoingPatAndVisitIndexes.getKey());
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        return patient.get().getVisitByIndex(pairOfOngoingPatAndVisitIndexes.getValue());
    }

    /**
     * Verifies that the patient and visit indexes can be obtained from the visit i.e.
     * Returns true if the visit can be found in the data.
     */
    public boolean visitIsInData(Visit visit) {
        requireNonNull(visit);
        Person patient = visit.getPatient();
        int patientIndex = persons.indexOf(patient);
        if (patientIndex <= -1) {
            return false;
        }
        int visitIndex = patient.indexOfVisit(visit);
        return visitIndex > -1;
    }

    /**
     * Record ongoing visit of person.
     * This will be saved until the visit is finished.
     */
    public void setOngoingVisit(Visit visit) {
        requireNonNull(visit);
        if (!visitIsInData(visit)) {
            throw new IllegalArgumentException();
        }
        setPairOfOngoingPatAndVisitIndexes(new Pair<>(persons.indexOf(visit.getPatient()),
                visit.getPatient().indexOfVisit(visit)));
    }

    /**
     * Unset current person and visit
     */
    public void unsetOngoingVisit() {
        this.pairOfOngoingPatAndVisitIndexes = NO_ONGOING_PATIENT_AND_VISIT_VAL;
    }

    @Override
    public Pair<Integer, Integer> getIndexPairOfOngoingPatientAndVisit() {
        return pairOfOngoingPatAndVisitIndexes;
    }

    private void setPairOfOngoingPatAndVisitIndexes(Pair<Integer, Integer> pairOfOngoingPatAndVisitIndexes) {
        this.pairOfOngoingPatAndVisitIndexes = pairOfOngoingPatAndVisitIndexes;
    }
}
