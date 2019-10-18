package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javax.swing.plaf.nimbus.State;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
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
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Policy> filteredPolicies;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.statefulAddressBook = new StatefulAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.statefulAddressBook.getPersonList());
        filteredPolicies = new FilteredList<>(this.statefulAddressBook.getPolicyList());
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
        statefulAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return statefulAddressBook;
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

    //=========== Functions related to undo/redo =============================================================

    @Override
    public boolean canUndo() {
        return statefulAddressBook.canUndo();
    }

    @Override
    public boolean canRedo() {
        return statefulAddressBook.canRedo();
    }

    @Override
    public void undo() {
        statefulAddressBook.undo();
    }

    @Override
    public void redo() {
        statefulAddressBook.redo();
    }

    @Override
    public void commitPerson() {
        statefulAddressBook.commitPerson();
    }

    @Override
    public void commitPolicy() {
        statefulAddressBook.commitPolicy();
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
        return statefulAddressBook.equals(other.statefulAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredPolicies.equals(other.filteredPolicies);
    }

}
