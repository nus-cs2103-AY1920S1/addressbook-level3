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
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UserSettings;
import seedu.address.commons.util.TimeUtil;
import seedu.address.model.binitem.BinItem;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyName;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StatefulAddressBook statefulAddressBook;
    private final UserPrefs userPrefs;
    private final CommandHistory commandHistory;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Policy> filteredPolicies;
    private final FilteredList<BinItem> filteredBinItems;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs "
            + userPrefs);

        this.statefulAddressBook = new StatefulAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.commandHistory = new CommandHistory();
        filteredPersons = new FilteredList<>(this.statefulAddressBook.getPersonList());
        filteredPolicies = new FilteredList<>(this.statefulAddressBook.getPolicyList());
        filteredBinItems = new FilteredList<>(this.statefulAddressBook.getBinItemList());
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
    public UserSettings getUserSettings() {
        return userPrefs.getUserSettings();
    }

    @Override
    public void setUserSettings(UserSettings userSettings) {
        requireNonNull(userSettings);
        userPrefs.setUserSettings(userSettings);
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
        return statefulAddressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        statefulAddressBook.resetData(addressBook);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return statefulAddressBook.hasPerson(person);
    }

    @Override
    public Person getPerson(Person person) {
        requireNonNull(person);
        return statefulAddressBook.getPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        statefulAddressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        statefulAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        statefulAddressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasPolicy(Policy policy) {
        requireNonNull(policy);
        return statefulAddressBook.hasPolicy(policy);
    }

    @Override
    public boolean hasPolicyWithName(PolicyName policyName) {
        return statefulAddressBook.hasPolicyWithName(policyName);
    }

    @Override
    public Policy getPolicy(Policy policy) {
        requireNonNull(policy);
        return statefulAddressBook.getPolicy(policy);
    }

    @Override
    public Policy getPolicyWithName(PolicyName policyName) {
        requireNonNull(policyName);
        return statefulAddressBook.getPolicyWithName(policyName);
    }

    @Override
    public void deletePolicy(Policy target) {
        statefulAddressBook.removePolicy(target);
    }

    @Override
    public void addPolicy(Policy policy) {
        statefulAddressBook.addPolicy(policy);
        updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
    }

    @Override
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireAllNonNull(target, editedPolicy);

        statefulAddressBook.setPolicy(target, editedPolicy);
    }

    @Override
    public boolean hasBinItem(BinItem binItem) {
        requireNonNull(binItem);
        return statefulAddressBook.hasBinItem(binItem);
    }

    @Override
    public void deleteBinItem(BinItem target) {
        statefulAddressBook.removeBinItem(target);
    }

    @Override
    public void addBinItem(BinItem binItem) {
        statefulAddressBook.addBinItem(binItem);
        updateFilteredBinItemList(PREDICATE_SHOW_ALL_BIN_ITEMS);
    }

    @Override
    public void setBinItem(BinItem target, BinItem editedBinItem) {
        requireAllNonNull(target, editedBinItem);

        statefulAddressBook.setBinItem(target, editedBinItem);
    }

    @Override
    public void binCleanUp() {
        statefulAddressBook.binCleanUp();
    }

    //=========== Functions related to undo/redo =============================================================

    @Override
    public boolean canUndoAddressBook() {
        return statefulAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return statefulAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        statefulAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        statefulAddressBook.redo();
    }

    @Override
    public void saveAddressBookState() {
        statefulAddressBook.saveAddressBookState();
    }

    @Override
    public ObservableMap<String, Integer> getPolicyPopularityBreakdown() {
        // Set up map
        ObservableMap<String, Integer> result = FXCollections.observableHashMap();
        statefulAddressBook.getPolicyList().forEach(policy -> result.put(policy.getName().toString(), 0));

        String assertionMessage = "Value in map must be initialised to zero!";
        // Assert values in result map are initialised as 0
        result.values().forEach((value) -> {
            assert value == 0 : assertionMessage;
        });

        // Add popularity
        statefulAddressBook.getPersonList().forEach(person -> {
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

        String assertionMessage = "Value in map must be initialised to zero!";
        // Assert values in result map are initialised as 0
        result.values().forEach((value) -> {
            assert value == 0 : assertionMessage;
        });

        // Add numbers
        statefulAddressBook.getPersonList().forEach(person -> {
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

        String assertionMessage = "Value in map must be initialised to zero!";
        // Assert values in result map are initialised as 0
        result.values().forEach((value) -> {
            assert value == 0 : assertionMessage;
        });

        // Add numbers
        statefulAddressBook.getPersonList().forEach(person -> {
            String gender = person.getGender().toString();
            result.put(gender, result.get(gender) + 1);
        });

        return result;
    }

    //=========== Command History ============================================================================

    @Override
    public ObservableList<Pair<String, String>> getHistoryList() {
        return commandHistory.getHistory();
    }

    @Override
    public void addCommandToHistory(String commandWord, String commandText) {
        requireNonNull(commandWord);
        requireNonNull(commandText);
        commandHistory.addCommand(commandWord, commandText);
    }

    //=========== Filtered Person List Accessors =============================================================

    // TODO: delete

    /**
     * Returns a list of unfiltered person.
     *
     * @returnlist of unfiltered person.
     */
    private ObservableList<Person> getPersonList() {
        return statefulAddressBook.getPersonList();
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

    //=========== Filtered Person List Accessors =============================================================

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

    /**
     * Returns an unmodifiable view of the list of {@code BinItem} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<BinItem> getFilteredBinItemList() {
        return filteredBinItems;
    }

    @Override
    public void updateFilteredBinItemList(Predicate<BinItem> predicate) {
        requireNonNull(predicate);
        filteredBinItems.setPredicate(predicate);
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
        return statefulAddressBook.equals(other.statefulAddressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredPersons.equals(other.filteredPersons)
            && filteredPolicies.equals(other.filteredPolicies);
    }

}
