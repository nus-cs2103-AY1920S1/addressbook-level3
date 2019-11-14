package organice.model.person;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import organice.model.person.exceptions.DuplicatePersonException;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    private final ObservableList<Person> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Nric toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(person -> person.getNric().equals(toCheck));
    }

    /**
     * Returns true if the list contains an equivalent doctor in charge as the given argument.
     */
    public boolean containsDoctor(Nric doctor) {
        requireNonNull(doctor);
        return internalList.stream().anyMatch(n -> n.getType().isDoctor() && n.getNric().equals(doctor));
    }

    /**
     * Returns true if the list contains an equivalent patient as the given argument.
     */
    public boolean containsPatient(Nric patient) {
        requireNonNull(patient);
        return internalList.stream().anyMatch(n -> n.getType().isPatient() && n.getNric().equals(patient));
    }

    /**
     * Returns true if the list contains an equivalent donor as the given argument.
     */
    public boolean containsDonor(Nric donor) {
        requireNonNull(donor);
        return internalList.stream().anyMatch(n -> n.getType().isDonor() && n.getNric().equals(donor));
    }

    /**
     * Returns true if the list contains a patient whose doctor in charge is the same as the given argument
     */
    public boolean isAttachedToPatient(DoctorInCharge doctorIc) {
        requireNonNull(doctorIc);
        return internalList.stream().anyMatch(
            n -> n.getType().isPatient() && ((Patient) n).getDoctorInCharge().equals(doctorIc)
        );
    }

    /**
     * Returns the {@code Person} with the specified {@code Nric}.
     */
    public Person getPerson(Nric personNric) throws PersonNotFoundException {
        requireNonNull(personNric);
        for (int i = 0; i < internalList.size(); i++) {
            Person currentPerson = internalList.get(i);

            if (currentPerson.getNric().equals(personNric)) {
                return currentPerson;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Returns the {@code Patient} with the specified {@code Nric}.
     */
    public Patient getPatient(Nric patientNric) throws PersonNotFoundException {
        requireNonNull(patientNric);
        for (int i = 0; i < internalList.size(); i++) {
            Person currentPerson = internalList.get(i);

            if (currentPerson instanceof Patient && currentPerson.getNric().equals(patientNric)) {
                return (Patient) currentPerson;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Returns the {@code Donor} with the specified {@code Nric}.
     */
    public Donor getDonor(Nric donorNric) throws PersonNotFoundException {
        requireNonNull(donorNric);
        for (int i = 0; i < internalList.size(); i++) {
            Person currentPerson = internalList.get(i);

            if (currentPerson instanceof Donor && currentPerson.getNric().equals(donorNric)) {
                return (Donor) currentPerson;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Person> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<Person> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSamePerson(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
