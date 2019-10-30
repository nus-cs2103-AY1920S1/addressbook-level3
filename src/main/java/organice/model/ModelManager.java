package organice.model;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import organice.commons.core.GuiSettings;
import organice.commons.core.LogsCenter;
import organice.logic.commands.MatchCommand;
import organice.logic.commands.exceptions.CommandException;
import organice.model.comparator.ExpiryDateComparator;
import organice.model.comparator.PriorityComparator;
import organice.model.comparator.SuccessRateComparator;
import organice.model.person.Donor;
import organice.model.person.MatchedDonor;
import organice.model.person.MatchedPatient;
import organice.model.person.Nric;
import organice.model.person.Patient;
import organice.model.person.Person;
import organice.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private ObservableList<Person> listOfMatches = FXCollections.observableArrayList();
    private SortedList<MatchedDonor> sortedMatchedDonors;
    private SortedList<MatchedPatient> sortedMatchedPatients;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasPerson(Nric personNric) {
        requireNonNull(personNric);
        return addressBook.hasPerson(personNric);
    }

    @Override
    public boolean hasDoctor(Nric doctor) {
        requireNonNull(doctor);
        return addressBook.hasDoctor(doctor);
    }

    @Override
    public boolean hasPatient(Nric patient) {
        requireNonNull(patient);
        return addressBook.hasPatient(patient);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public Patient getPatient(Nric patientNric) throws PersonNotFoundException {
        requireNonNull(patientNric);

        return addressBook.getPatient(patientNric);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Match List Accessors =============================================================

    /**
     * Returns a list of {@code Patient} in ORGANice.
     */
    private ArrayList<Patient> getListOfPatients() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ArrayList<Patient> listOfPatients = new ArrayList<>();

        for (Person person : filteredPersons) {
            if (!(person instanceof Patient)) {
                continue;
            }
            listOfPatients.add(((Patient) person));
        }

        return listOfPatients;
    }

    /**
     * Returns a list of {@code Donor} in ORGANice.
     */
    private ArrayList<Donor> getListOfDonors() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ArrayList<Donor> listOfDonors = new ArrayList<>();

        for (Person person : filteredPersons) {
            if (!(person instanceof Donor)) {
                continue;
            }

            listOfDonors.add((Donor) person);
        }

        return listOfDonors;
    }


    /**
     * Add a MatchedDonor to the list to be displayed.
     * @param matchedDonorToAdd {@code MatchedDonor} to add to the list
     */
    public void addMatchedDonor(MatchedDonor matchedDonorToAdd) {
        requireNonNull(matchedDonorToAdd);
        listOfMatches.add(matchedDonorToAdd);
    }

    /**
     * Adds a MatchedPatient to the list displayed.
     * @param matchedPatientToAdd {@code MatchedPatient} to add to the list
     */
    public void addMatchedPatient(MatchedPatient matchedPatientToAdd) {
        requireNonNull(matchedPatientToAdd);
        listOfMatches.add(matchedPatientToAdd);
    }

    /**
     * Remove all MatchedDonors instances. Meant to be called before adding new MatchedDonor instances.
     * The purpose of calling this method before is to ensure that each MatchedDonor is a unique item in the list.
     */
    public void removeMatches() {
        listOfMatches = FXCollections.observableArrayList();
    }

    /**
     * Matches all Patients in ORGANice with all existing donors.
     */
    public void matchAllPatients() {
        List<Patient> listOfPatients = getListOfPatients();
        List<Donor> listOfDonors = getListOfDonors();

        //display matches
        for (Patient patient : listOfPatients) {
            Integer numberOfMatches = 0;
            for (Donor donor : listOfDonors) {
                boolean isMatch = MatchCommand.match(donor, patient);

                if (isMatch) {
                    numberOfMatches += 1;
                }
            }

            MatchedPatient matchedPatient = new MatchedPatient(patient);
            matchedPatient.setNumberOfMatches(numberOfMatches);
            addMatchedPatient(matchedPatient);
        }
    }

    /**
     * Match Donors to the specified {@code Patient}.
     */
    public void matchDonors(Patient patient) {
        //filter out donors.
        List<Donor> listOfDonors = getListOfDonors();

        //if match, create a MatchedDonor and add to the list.
        for (Donor donor : listOfDonors) {
            boolean isMatch = MatchCommand.match(donor, patient);

            if (isMatch) {
                MatchedDonor matchedDonor = new MatchedDonor(donor);
                addMatchedDonor(matchedDonor);
            }
        }
    }

    /**
     * Returns a copy of the match list.
     */
    public ObservableList<Person> getMatchList() throws AssertionError {
        ObservableList<Person> listOfMatchesCopy = FXCollections.observableArrayList();
        for (Person person : listOfMatches) {
            if (person instanceof MatchedDonor) {
                listOfMatchesCopy.add((MatchedDonor) person);
            } else if (person instanceof MatchedPatient) {
                listOfMatchesCopy.add((MatchedPatient) person);
            } else {
                assert true : "A Person not an instance of MatchedDonor or MatchedPatient is in the match list";
            }
        }
        return listOfMatchesCopy;
    }

    /**
     * Returns the number of {@code MatchedDonors} that matches a specific {@code Patient}.
     */
    public int numberOfMatches() {
        return listOfMatches.size();
    }

    //=========== Sorted Person List Accessors =============================================================

    /**
     * Retrieves the sort list.
     */
    public SortedList<Person> getSortList() {
        Person firstperson = listOfMatches.get(0);
        if (firstperson instanceof MatchedPatient) {
            return (SortedList<Person>) (SortedList<?>) sortedMatchedPatients;
        } else {
            return (SortedList<Person>) (SortedList<?>) sortedMatchedDonors;
        }
    }

    /**
     * Sorts list by priority level.
     */
    @Override
    public void sortByPriority() throws CommandException {
        try {
            sortedMatchedPatients = new SortedList<>((ObservableList<MatchedPatient>) (ObservableList<?>)
                    listOfMatches);
            sortedMatchedPatients.setComparator(new PriorityComparator());
        } catch (ClassCastException | IllegalArgumentException ex) {
            throw new CommandException("Sorting by Priority only works after 'match ic/all'.");
        }
    }

    /**
     * Sorts list by rate of success.
     */
    @Override
    public void sortBySuccessRate() throws CommandException {
        try {
            sortedMatchedDonors = new SortedList<>((ObservableList<? extends MatchedDonor>) (ObservableList<?>)
                listOfMatches);
            sortedMatchedDonors.setComparator(new SuccessRateComparator());
        } catch (ClassCastException | IllegalArgumentException ex) {
            throw new CommandException("Sorting by success rate "
                    + "only works after executing 'match ic/[patient NRIC]'.");
        }
    }

    /**
     * Sorts list by organ expiry date.
     */
    @Override
    public void sortByOrganExpiryDate() throws CommandException {
        try {
            sortedMatchedDonors = new SortedList<>((ObservableList<? extends MatchedDonor>) (ObservableList<?>)
                listOfMatches);
            sortedMatchedDonors.setComparator(new ExpiryDateComparator());
        } catch (ClassCastException | IllegalArgumentException ex) {
            throw new CommandException("Sorting by organ expiry date "
                    + "only works after executing 'match ic/[patient NRIC]'.");
        }
    }
}
