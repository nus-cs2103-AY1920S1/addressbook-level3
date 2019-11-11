package organice.model;

import static java.util.Objects.requireNonNull;
import static organice.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
import organice.model.comparator.CompatibilityRateComparator;
import organice.model.comparator.ExpiryDateComparator;
import organice.model.comparator.NameComparator;
import organice.model.comparator.NumOfMatchesComparator;
import organice.model.comparator.PriorityComparator;
import organice.model.person.Doctor;
import organice.model.person.DoctorInCharge;
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
    private ObservableList<Person> displayedPersons;
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
        displayedPersons = FXCollections.observableList(this.addressBook.getPersonList());
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

    /**
     * To check if the model have a person with the given Nric.
     * @param personNric
     * @return a boolean true or false if the person exist.
     */
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
    public boolean hasDonor(Nric donor) {
        requireNonNull(donor);
        return addressBook.hasDonor(donor);
    }

    @Override
    public boolean hasDoctorInCharge(DoctorInCharge doctorIc) {
        requireNonNull(doctorIc);
        return addressBook.hasDoctorInCharge(doctorIc);
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
    public Person getPerson(Nric personNric) throws PersonNotFoundException {
        requireNonNull(personNric);
        return addressBook.getPerson(personNric);
    }

    @Override
    public Patient getPatient(Nric patientNric) throws PersonNotFoundException {
        requireNonNull(patientNric);
        return addressBook.getPatient(patientNric);
    }

    @Override
    public Donor getDonor(Nric donorNric) throws PersonNotFoundException {
        requireNonNull(donorNric);
        return addressBook.getDonor(donorNric);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Displayed Person List ======================================================================

    @Override
    public ObservableList<Person> getDisplayedPersonList() {
        return displayedPersons;
    }

    @Override
    public void setDisplayedPersonList(List<Person> personList) {
        displayedPersons = FXCollections.observableList(personList);
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
    public ObservableList<Person> getFullPersonList() {
        Predicate predicate = filteredPersons.getPredicate();
        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<Person> fullPersonList = FXCollections.observableList(
                Arrays.asList(filteredPersons.toArray(Person[]::new)));
        filteredPersons.setPredicate(predicate);
        return fullPersonList;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        setDisplayedPersonList(Arrays.asList(filteredPersons.toArray(Person[]::new)));
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
     * Returns a list of {@code Doctor} in ORGANice.
     */
    @Override
    public ArrayList<Doctor> getListOfDoctors() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ArrayList<Doctor> listOfDoctors = new ArrayList<>();

        for (Person person : filteredPersons) {
            if (!(person instanceof Doctor)) {
                continue;
            }

            listOfDoctors.add((Doctor) person);
        }

        return listOfDoctors;
    }

    /**
     * Returns a list of {@code Patient} with a specified doctor in charge in ORGANice.
     */
    public ArrayList<Patient> getPatientsWithDoctorIc(DoctorInCharge doctorIc) {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ArrayList<Patient> listOfPatients = new ArrayList<>();

        for (Person person : filteredPersons) {
            if (!(person instanceof Patient) || !((Patient) person).getDoctorInCharge().equals(doctorIc)) {
                continue;
            }
            listOfPatients.add(((Patient) person));
        }
        return listOfPatients;
    }


    /**
     * Add a MatchedDonor to the list to be displayed.
     * @param matchedDonorToAdd {@code MatchedDonor} to add to the list
     */
    public void addMatchedDonor(MatchedDonor matchedDonorToAdd) {
        requireNonNull(matchedDonorToAdd);
        listOfMatches.add(matchedDonorToAdd);
        setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));

    }

    /**
     * Adds a MatchedPatient to the list displayed.
     * @param matchedPatientToAdd {@code MatchedPatient} to add to the list
     */
    public void addMatchedPatient(MatchedPatient matchedPatientToAdd) {
        requireNonNull(matchedPatientToAdd);
        listOfMatches.add(matchedPatientToAdd);
        setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
    }

    /**
     * Remove all MatchedDonors instances. Meant to be called before adding new MatchedDonor instances.
     * The purpose of calling this method before is to ensure that each MatchedDonor is a unique item in the list.
     */
    public void removeMatches() {
        listOfMatches = FXCollections.observableArrayList();
        setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
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
        setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
    }

    /**
     * Match Donors to the specified {@code Patient}.
     */
    public void matchDonors(Patient patient) {
        List<Donor> listOfDonors = getListOfDonors();
        //if match, create a MatchedDonor and add to the list.
        for (Donor donor : listOfDonors) {
            boolean isMatch = MatchCommand.match(donor, patient);
            if (isMatch) {
                MatchedDonor matchedDonor = new MatchedDonor(donor);
                addMatchedDonor(matchedDonor);
            }
        }
        setDisplayedPersonList(Arrays.asList(listOfMatches.toArray(Person[]::new)));
    }

    /**
     * Returns the number of {@code MatchedDonors} that matches a specific {@code Patient}.
     */
    public int numberOfMatches() {
        return listOfMatches.size();
    }

    //=========== Sorted Person List Accessors =============================================================

    /**
     * Sorts list of patients by priority level from highest to lowest.
     * Patients of same priority will be sorted by number of matched donors they have (higher to lower number).
     * If these two variables are the same, they will additionally be sorted by name in alphabetical order.
     */
    @Override
    public void sortByPriority() throws CommandException {
        try {
            sortedMatchedPatients = new SortedList<>((ObservableList<MatchedPatient>) (ObservableList<?>)
                    listOfMatches);
            sortedMatchedPatients.setComparator(new NameComparator());
            sortedMatchedPatients.setComparator(new NumOfMatchesComparator());
            sortedMatchedPatients.setComparator(new PriorityComparator());
            setDisplayedPersonList(Arrays.asList(sortedMatchedPatients.toArray(Person[]::new)));
        } catch (ClassCastException | IllegalArgumentException ex) {
            throw new CommandException("Sorting by priority only works after 'match ic/all'.");
        }
    }

    /**
     * Sorts list of donors of a patient by rate of compatibility from highest to lowest.
     */
    @Override
    public void sortByCompatibilityRate() throws CommandException {
        try {
            sortedMatchedDonors = new SortedList<>((ObservableList<? extends MatchedDonor>) (ObservableList<?>)
                    listOfMatches);
            sortedMatchedDonors.setComparator(new CompatibilityRateComparator());
            setDisplayedPersonList(Arrays.asList(sortedMatchedDonors.toArray(Person[]::new)));
        } catch (ClassCastException | IllegalArgumentException ex) {
            throw new CommandException("Sorting by compatibility rate "
                    + "only works after executing 'match ic/[patient NRIC]'.");
        }
    }

    /**
     * Sorts list of donors of a patient by organ expiry date from the earliest to the latest date.
     */
    @Override
    public void sortByOrganExpiryDate() throws CommandException {
        try {
            sortedMatchedDonors = new SortedList<>((ObservableList<? extends MatchedDonor>) (ObservableList<?>)
                    listOfMatches);
            sortedMatchedDonors.setComparator(new ExpiryDateComparator());
            setDisplayedPersonList(Arrays.asList(sortedMatchedDonors.toArray(Person[]::new)));
        } catch (ClassCastException | IllegalArgumentException ex) {
            throw new CommandException("Sorting by organ expiry date "
                    + "only works after executing 'match ic/[patient NRIC]'.");
        }
    }
}
