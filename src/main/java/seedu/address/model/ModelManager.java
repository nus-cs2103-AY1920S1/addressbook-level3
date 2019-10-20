package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.TimeUtil;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Policy> filteredPolicies;

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
        filteredPolicies = new FilteredList<>(this.addressBook.getPolicyList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public Person getPerson(Person person) {
        requireNonNull(person);
        return addressBook.getPerson(person);
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
    public boolean hasPolicy(Policy policy) {
        requireNonNull(policy);
        return addressBook.hasPolicy(policy);
    }

    @Override
    public boolean hasPolicyWithName(PolicyName policyName) {
        return addressBook.hasPolicyWithName(policyName);
    }

    @Override
    public Policy getPolicy(Policy policy) {
        requireNonNull(policy);
        return addressBook.getPolicy(policy);
    }

    @Override
    public Policy getPolicyWithName(PolicyName policyName) {
        requireNonNull(policyName);
        return addressBook.getPolicyWithName(policyName);
    }

    @Override
    public void deletePolicy(Policy target) {
        addressBook.removePolicy(target);
    }

    @Override
    public void addPolicy(Policy policy) {
        addressBook.addPolicy(policy);
        updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
    }

    @Override
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireAllNonNull(target, editedPolicy);

        addressBook.setPolicy(target, editedPolicy);
    }

    @Override
    public ObservableMap<String, Integer> getPolicyPopularityBreakdown() {
        // Set up map
        ObservableMap<String, Integer> result = FXCollections.observableHashMap();
        this.addressBook.getPolicyList().forEach(policy -> result.put(policy.getName().toString(), 0));

        // Add popularity
        this.addressBook.getPersonList().forEach(person -> {
            Set<Policy> policies = person.getPolicies();
            policies.forEach(policy -> {
                String policyName = policy.getName().toString();
                result.put(policyName, result.get(policyName) + 1);
            });
        });

        return result;
    }

    @Override
    public ObservableMap<String, Integer> getAgeGroupBreakdown() {
        // Set up age group
        ObservableMap<String, Integer> result = FXCollections.observableHashMap();
        TimeUtil.getAgeGroup().forEach(ageGroup -> result.put(ageGroup, 0));

        // Add numbers
        this.addressBook.getPersonList().forEach(person -> {
            int yearOfBirth = person.getDateOfBirth().dateOfBirth.getYear();
            String ageGroup = TimeUtil.parseAgeGroup(yearOfBirth);
            result.put(ageGroup, result.get(ageGroup) + 1);
        });

        return result;
    }

    @Override
    public ObservableMap<String, Integer> getGenderBreakdown() {
        // Set up gender
        ObservableMap<String, Integer> result = FXCollections.observableHashMap();
        Gender.getValidGender().forEach(gender -> result.put(gender, 0));

        // Add numbers
        this.addressBook.getPersonList().forEach(person -> {
            String gender = person.getGender().toString();
            result.put(gender, result.get(gender) + 1);
        });

        return result;
    }

    //=========== Filtered Person List Accessors =============================================================

    // TODO: delete

    /**
     * Returns a list of unfiltered person.
     *
     * @returnlist of unfiltered person.
     */
    private ObservableList<Person> getPersonList() {
        return this.addressBook.getPersonList();
    }

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

    /**
     * Returns an unmodifiable view of the list of {@code Policy} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Policy> getFilteredPolicyList() {
        return filteredPolicies;
    }

    @Override
    public void updateFilteredPolicyList(Predicate<Policy> predicate) {
        requireNonNull(predicate);
        filteredPolicies.setPredicate(predicate);
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
            && filteredPersons.equals(other.filteredPersons)
            && filteredPolicies.equals(other.filteredPolicies);
    }

}
