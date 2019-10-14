package seedu.jarvis.model;

import static java.util.Objects.requireNonNull;
import static seedu.jarvis.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.model.address.AddressBook;
import seedu.jarvis.model.address.ReadOnlyAddressBook;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.history.HistoryManager;
import seedu.jarvis.model.userprefs.ReadOnlyUserPrefs;
import seedu.jarvis.model.userprefs.UserPrefs;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final HistoryManager historyManager;
    private final AddressBook addressBook;
    //private final FinanceTracker financeTracker;
    private final UserPrefs userPrefs;
    private final CcaTracker ccaTracker;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(CcaTracker ccaTracker, HistoryManager historyManager, ReadOnlyAddressBook addressBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(historyManager, addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.historyManager = new HistoryManager(historyManager);
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.ccaTracker = new CcaTracker(ccaTracker);
    }

    public ModelManager() {
        this(new CcaTracker(), new HistoryManager(), new AddressBook(), new UserPrefs());
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

    //=========== HistoryManager =============================================================================

    /**
     * Gets the {@code HistoryManager}.
     *
     * @return {@code HistoryManager} object.
     */
    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    /**
     * Replaces {@code HistoryManager} data with the data in {@code HistoryManager} given as argument.
     *
     * @param historyManager {@code HistoryManager} data to be used.
     */
    @Override
    public void setHistoryManager(HistoryManager historyManager) {
        this.historyManager.resetData(historyManager);
    }

    /**
     * Gets the number of available executed commands that can be undone.
     *
     * @return The number of available executed commands that can be undone.
     */
    @Override
    public int getAvailableNumberOfExecutedCommands() {
        return historyManager.getNumberOfAvailableExecutedCommands();
    }

    /**
     * Gets the number of available inversely executed commands that can be redone.
     *
     * @return The number of available inversely executed commands that can be redone.
     */
    @Override
    public int getAvailableNumberOfInverselyExecutedCommands() {
        return historyManager.getNumberOfAvailableInverselyExecutedCommands();
    }

    /**
     * Checks whether it is possible to roll back any commands.
     *
     * @return Whether it is possible to roll back any commands.
     */
    @Override
    public boolean canRollback() {
        return historyManager.canRollback();
    }

    /**
     * Checks whether it is possible to commit any commands.
     *
     * @return Whether it is possible to commit any commands.
     */
    @Override
    public boolean canCommit() {
        return historyManager.canCommit();
    }

    /**
     * Adds the latest executed command. {@code Command} to be added must be invertible.
     *
     * @param command {@code Command} to be added.
     */
    @Override
    public void rememberExecutedCommand(Command command) {
        historyManager.rememberExecutedCommand(command);
        historyManager.forgetAllInverselyExecutedCommands();
    }

    /**
     * Rolls back the changes made by the latest executed command by inversely executing the command.
     *
     * @return Whether the inverse execution of the latest executed command was successful.
     */
    @Override
    public boolean rollback() {
        return historyManager.rollback(this);
    }

    /**
     * Commits the changes made by the latest inversely executed command by executing the command.
     *
     * @return Whether the execution of the latest executed command was successful.
     */
    @Override
    public boolean commit() {
        return historyManager.commit(this);
    }

    //=========== AddressBook ================================================================================

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
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Adds {@code Person} at a given index.
     *
     * @param zeroBasedIndex Zero-based index to add {@code Person} to.
     * @param person {@code Person} to be added.
     */
    @Override
    public void addPerson(int zeroBasedIndex, Person person) {
        addressBook.addPerson(zeroBasedIndex, person);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressBook.getFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        addressBook.updateFilteredPersonList(predicate);
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
        return historyManager.equals(other.historyManager)
                && addressBook.equals(other.addressBook)
                && addressBook.getFilteredPersonList().equals(other.addressBook.getFilteredPersonList())
                && userPrefs.equals(other.userPrefs);
    }

    //=========== Cca Tracker ================================================================================

    @Override
    public void contains(Cca cca) {

    }

    @Override
    public void addCca(Cca cca) {
        requireNonNull(cca);
        ccaTracker.addCca(cca);
    }

    @Override
    public void removeCca(Cca cca) {

    }

    @Override
    public void updateCca(Cca toBeUpdatedCca, Cca updatedCca) {

    }

    @Override
    public boolean hasCca(Cca cca) {
        requireNonNull(cca);

        return ccaTracker.contains(cca);
    }

    @Override
    public CcaTracker getCcaTracker() {
        return ccaTracker;
    }
}
