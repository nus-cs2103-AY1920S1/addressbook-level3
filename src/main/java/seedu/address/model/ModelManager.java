package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final ActivityBook activityBook;
    private final UserPrefs userPrefs;
    private final InternalState internalState;

    // Lists of person or activity entries to display
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Activity> filteredActivities;

    // Describes the nature of the content currently being displayed
    private Context context;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs, internalState and activityBook.
     */
    public ModelManager(
            ReadOnlyAddressBook addressBook,
            ReadOnlyUserPrefs userPrefs,
            InternalState internalState,
            ActivityBook activityBook) {
        super();
        requireAllNonNull(addressBook, userPrefs, activityBook);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.activityBook = new ActivityBook(activityBook);
        this.internalState = new InternalState(internalState);

        filteredPersons = new FilteredList<Person>(this.addressBook.getPersonList());
        filteredActivities = new FilteredList<Activity>(this.activityBook.getActivityList());
        context = new Context();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new InternalState(), new ActivityBook());
    }

    // =========== Internal model state ============================================================

    @Override
    public void setInternalState(InternalState internalState) {
        requireNonNull(internalState);
        this.internalState.updateInternalState(internalState);
    }

    @Override
    public InternalState getInternalState() {
        // Update before returning internal state
        internalState.updateInternalState();
        return internalState;
    }

    // =========== Context =========================================================================

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public Context getContext() {
        return context;
    }

    // =========== UserPrefs =======================================================================

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

    @Override
    public void setActivityBookFilePath(Path activityBookFilePath) {
        requireNonNull(activityBookFilePath);
        userPrefs.setActivityBookFilePath(activityBookFilePath);
    }

    // =========== AddressBook =====================================================================

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
    public ArrayList<Person> findPersonAny(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        return addressBook.findPerson(predicate);
    }

    @Override
    public ArrayList<Person> findPersonAll(NameContainsAllKeywordsPredicate predicate) {
        requireNonNull(predicate);
        return addressBook.findPerson(predicate);
    }

    @Override
    public Optional<Person> findPersonByName(String searchTerm) {
        requireNonNull(searchTerm);
        return addressBook.findPersonByName(searchTerm);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    // =========== ActivityBook ====================================================================

    @Override
    public Path getActivityBookFilePath() {
        return userPrefs.getActivityBookFilePath();
    }

    @Override
    public void setActivityBook(ActivityBook activityBook) {
        this.activityBook.resetData(activityBook);
    }

    @Override
    public ActivityBook getActivityBook() {
        return activityBook;
    }

    @Override
    public void deleteActivity(Activity target) {
        activityBook.removeActivity(target);
    }

    @Override
    public void addActivity(Activity activity) {
        activityBook.addActivity(activity);
    }

    @Override
    public void setActivity(Activity target, Activity editedActivity) {
        requireAllNonNull(target, editedActivity);

        activityBook.setActivity(target, editedActivity);
    }

    // =========== Filtered Person List Accessors ==================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<? super Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // =========== Filtered Activity List Accessors ================================================

    /**
     * Returns an unmodifiable view of the list of {@code Activity} backed by the internal list of
     * {@code versionedActivityBook}
     */
    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return filteredActivities;
    }

    @Override
    public void updateFilteredActivityList(Predicate<? super Activity> predicate) {
        requireNonNull(predicate);
        filteredActivities.setPredicate(predicate);
    }

    // =========== Association lookup accessors for GUI ============================================

    @Override
    public List<Person> getAssociatedPersons(Activity activity) {
        requireNonNull(activity);

        return this.addressBook.getPersonList().stream()
            .filter((person) -> activity.hasPerson(person.getPrimaryKey()))
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<Activity> getAssociatedActivities(Person person) {
        requireNonNull(person);

        return this.activityBook.getActivityList().stream()
            .filter((activity) -> activity.hasPerson(person.getPrimaryKey()))
            .collect(Collectors.toUnmodifiableList());
    }

    // =========== Overridden Java methods =========================================================

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
                && activityBook.equals(other.activityBook)
                && userPrefs.equals(other.userPrefs)
                && internalState.equals(other.internalState)
                && context.equals(other.context)
                && filteredPersons.equals(other.filteredPersons)
                && filteredActivities.equals(other.filteredActivities);
    }
}
