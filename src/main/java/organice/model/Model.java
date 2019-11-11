package organice.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import organice.commons.core.GuiSettings;

import organice.logic.commands.exceptions.CommandException;

import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true if person is a doctor */
    Predicate<Person> PREDICATE_SHOW_ALL_DOCTORS = person -> person.getType().isDoctor();

    /** {@code Predicate} that always evaluate to true  if person is a donor */
    Predicate<Person> PREDICATE_SHOW_ALL_DONORS = person -> person.getType().isDonor();

    /** {@code Predicate} that always evaluate to true if person is a patient */
    Predicate<Person> PREDICATE_SHOW_ALL_PATIENTS = person -> person.getType().isPatient();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same nric as {@code personNric} exists in the address book.
     */
    boolean hasPerson(Nric personNric);

    /**
     * Returns true if a doctor in charge with the same nric as {@code doctorInCharge} exists in the address book.
     */
    boolean hasDoctor(Nric doctor);

    /**
     * Returns true if a patient with the specified Nric exists in ORGANice.
     */
    boolean hasPatient(Nric patient);

    /**
     * Returns true if a donor in charge with the same NRIC as {@code donor} exists in the address book.
     */
    boolean hasDonor(Nric donor);

    /**
     * Returns true if a patient's doctor in charge is the same as {@code doctorIc} exists in the address book.
     */
    boolean hasDoctorInCharge(DoctorInCharge doctorIc);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in ORGANice.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the
     * address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns the list of persons to be displayed */
    ObservableList<Person> getDisplayedPersonList();

    /** Sets the list of persons to be displayed to the given {@code personList} */
    void setDisplayedPersonList(List<Person> personList);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the full person list */
    ObservableList<Person> getFullPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Retrieves the {@code Person} with the specified {@code Nric}
     * @throws PersonNotFoundException if the {@code Person} with the {@code Nric} cannot be found.
     */
    Person getPerson(Nric personNric) throws PersonNotFoundException;

    /**
     * Retrieves the {@code Patient} with the specified {@code Nric}
     * @throws PersonNotFoundException if the {@code Patient} with the {@code Nric} cannot be found.
     */
    Patient getPatient(Nric patientNric) throws PersonNotFoundException;

    /**
     * Retrieves the {@code Donor} with the specified {@code Nric}
     * @throws PersonNotFoundException if the {@code Donor} with the {@code Nric} cannot be found.
     */
    Donor getDonor(Nric donorNric) throws PersonNotFoundException;

    /**
     * Returns list of doctors in ORGANice
     */
    ArrayList<Doctor> getListOfDoctors();

    /**
     * Returns list of patients with a specific doctor in charge in ORGANice
     */
    ArrayList<Patient> getPatientsWithDoctorIc(DoctorInCharge doctorIc);

    /**
     * Matches all Patients to all Donors in ORGANice.
     */
    void matchAllPatients();

    /**
     * Removes all MatchedDonor instances from ORGANice.
     */
    void removeMatches();

    /**
     * Match Donors to a specified Patient.
     */
    void matchDonors(Patient patient);

    /**
     * Returns the number of {@code MatchedDonors} that matches a specific {@code Patient}.
     */
    int numberOfMatches();

    /**
     * Sorts list by priority level.
     */
    void sortByPriority() throws CommandException;

    /**
     * Sorts list by rate of success.
     */
    void sortByCompatibilityRate() throws CommandException;

    /**
     * Sorts list by organ expiry date.
     */
    void sortByOrganExpiryDate() throws CommandException;
}
