package com.typee.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.typee.commons.core.GuiSettings;
import com.typee.commons.core.LogsCenter;
import com.typee.commons.util.CollectionUtil;
import com.typee.logic.commands.exceptions.NullRedoableActionException;
import com.typee.logic.commands.exceptions.NullUndoableActionException;
import com.typee.model.person.Person;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final HistoryManager statedAppointmentList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.statedAppointmentList = new HistoryManager(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.statedAppointmentList.getPersonList());
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
        this.statedAppointmentList.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return statedAppointmentList;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return statedAppointmentList.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        statedAppointmentList.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        statedAppointmentList.addPerson(person);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        CollectionUtil.requireAllNonNull(target, editedPerson);

        statedAppointmentList.setPerson(target, editedPerson);
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
    public void updateFilteredAppointmentList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Undo ================================================================================

    @Override
    public boolean hasNoUndoableCommand() {
        return !statedAppointmentList.isUndoable();
    }

    @Override
    public void undoAppointmentList() throws NullUndoableActionException {
        statedAppointmentList.undo();
    }

    //=========== Redo ================================================================================

    @Override
    public boolean hasNoRedoableCommand() {
        return !statedAppointmentList.isRedoable();
    }

    @Override
    public void redoAppointmentList() throws NullRedoableActionException {
        statedAppointmentList.redo();
    }

    @Override
    public void saveAppointmentList() {
        statedAppointmentList.saveState();
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
        return statedAppointmentList.equals(other.statedAppointmentList)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
