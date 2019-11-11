package organice.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import organice.commons.core.GuiSettings;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
import organice.model.person.Donor;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * A default model stub that have all of the methods failing. This class is created on its own
 * because more than one type of CommandTest class is using it.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDoctor(Nric doctor) {
        return true;
    }

    @Override
    public boolean hasPatient(Nric patientNric) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDonor(Nric donor) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasDoctorInCharge(DoctorInCharge doctorIc) {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public Patient getPatient(Nric patientNric) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Person getPerson(Nric personNric) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<Doctor> getListOfDoctors() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ArrayList<Patient> getPatientsWithDoctorIc(DoctorInCharge doctorIc) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Donor getDonor(Nric donorNric) throws PersonNotFoundException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Nric personNric) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    public void matchDonors(Patient patient) {
        throw new AssertionError("This method should not be called.");
    }

    public void matchAllPatients() {
        throw new AssertionError("This method should not be called.");
    }

    public void removeMatches() {
        throw new AssertionError("This method should not be called.");
    }

    public ObservableList<Person> getDisplayedPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setDisplayedPersonList(List<Person> personList) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFullPersonList() {
        throw new AssertionError("This method should not be called.");
    }


    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int numberOfMatches() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortByOrganExpiryDate() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void sortBySuccessRate() {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public void sortByPriority() {
        throw new AssertionError("This method should not be called");
    }
}
