package organice.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.UniquePersonList;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

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
     * Returns true if a person with the same nric as {@code personNric} exists in ORGANice.
     */
    public boolean hasPerson(Nric personNric) {
        requireNonNull(personNric);
        return persons.contains(personNric);
    }

    /**
     * Returns true if a doctor with the same nric as {@code doctor} exists in ORGANice.
     */
    public boolean hasDoctor(Nric doctor) {
        requireNonNull(doctor);
        return persons.containsDoctor(doctor);
    }

    /**
     * Returns true if a patient with the specified Nric exists in ORGANice.
     */
    public boolean hasPatient(Nric patient) {
        requireNonNull(patient);
        return persons.containsPatient(patient);
    }

    /**
     * Returns true if a donor with the same NRIC as {@code donor} exists in the address book.
     */
    public boolean hasDonor(Nric donor) {
        requireNonNull(donor);
        return persons.containsDonor(donor);
    }

    /**
     * Returns true if a patient's doctor in charge is the same as {@code doctorIc} exists in the address book.
     */
    public boolean hasDoctorInCharge(DoctorInCharge doctorIc) {
        requireNonNull(doctorIc);
        return persons.isAttachedToPatient(doctorIc);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Retrieves the {@code Person} with the specified {@code Nric}.
     * {@code Nric} must exist in ORGANice.
     */
    public Person getPerson(Nric personNric) throws PersonNotFoundException {
        return persons.getPerson(personNric);
    }

    /**
     * Retrieves the {@code Patient} with the specified {@code Nric}.
     * {@code Nric} must exist in ORGANice.
     */
    public Patient getPatient(Nric patientNric) throws PersonNotFoundException {
        return persons.getPatient(patientNric);
    }

    /**
     * Retrieves the {@code Donor} with the specified {@code Nric}.
     * {@code Nric} must exist in ORGANice.
     */
    public Donor getDonor(Nric donorNric) throws PersonNotFoundException {
        return persons.getDonor(donorNric);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The identity of {@code editedPerson} must not be the same as another existing {@code person}
     * in the ORGANice Transplant Manager.
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
