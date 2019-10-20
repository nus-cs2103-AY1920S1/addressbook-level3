package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.earnings.Earnings;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Earnings> filteredEarnings;
    private final FilteredList<CommandObject> filteredCommands;
    private Stack<String> savedCommand;

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
        filteredEarnings = new FilteredList<>(this.addressBook.getEarningsList());
        filteredCommands = new FilteredList<>(this.addressBook.getCommandsList());
        savedCommand = new Stack<String>();
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
    public boolean hasEarnings(Earnings earnings) {
        requireAllNonNull(earnings);
        return addressBook.hasEarnings(earnings);
    }

    @Override
    public void addEarnings(Earnings earnings) {
        addressBook.addEarnings(earnings);
        updateFilteredEarningsList(PREDICATE_SHOW_ALL_EARNINGS);
    }

    @Override
    public void setEarnings(Earnings target, Earnings editedEarnings) {
        requireAllNonNull(target, editedEarnings);

        addressBook.setEarnings(target, editedEarnings);
    }


    @Override
    public boolean hasCommand(CommandObject command) {
        requireAllNonNull(command);
        return addressBook.hasCommand(command);
    }

    @Override
    public void deleteCommand(CommandObject target) {
        addressBook.removeCommand(target);
    }

    @Override
    public void addCommand(CommandObject command) {
        addressBook.addCommand(command);
        updateFilteredCommandsList(PREDICATE_SHOW_ALL_COMMANDS);
    }

    @Override
    public void setCommands(CommandObject target, CommandObject editedCommands) {
        requireAllNonNull(target, editedCommands);

        addressBook.setCommands(target, editedCommands);
    }

    @Override
    public void saveCommand(String command) {
        this.savedCommand.push(command);
    }

    @Override
    public String getSavedCommand() {
       return this.savedCommand.peek();
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
    public void addTask(Task task) {

    }

    @Override
    public ReadOnlyCalendar getCalendar() {
        return null;
    }

    public ObservableList<Earnings> getFilteredEarningsList() {
        return filteredEarnings;
    }

    public ObservableList<CommandObject> getFilteredCommandsList() {
        return filteredCommands;
    }

    @Override
    public void updateFilteredEarningsList(Predicate<Earnings> predicate) {
        requireNonNull(predicate);
        filteredEarnings.setPredicate(predicate);
    }

    @Override
    public void updateFilteredCommandsList(Predicate<CommandObject> predicate) {
        requireNonNull(predicate);
        filteredCommands.setPredicate(predicate);
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
        return (addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons));
    }

}
