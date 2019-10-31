package organice.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import organice.commons.core.GuiSettings;
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
        throw new AssertionError("This method should not be called.");
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
    public Patient getPatient(Nric patientNric) {
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

    @Override
    public ObservableList<Person> getMatchList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void matchDonors(Patient patient) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void matchAllPatients() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeMatches() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
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

    @Override
    public SortedList<Person> getSortList() {
        throw new AssertionError("This method should not be called");
    }
}
