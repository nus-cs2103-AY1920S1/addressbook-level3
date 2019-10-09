package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.events.Event;
import seedu.address.model.person.Person;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final AppointmentBook appointmentBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs, ReadOnlyAddressBook addressBook,
            ReadOnlyAppointmentBook patientSchedule) {
        super();
        requireAllNonNull(userPrefs, addressBook, patientSchedule);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.appointmentBook = new AppointmentBook(patientSchedule);
        this.userPrefs = new UserPrefs(userPrefs);
        this.filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.filteredEvents = new FilteredList<>(this.appointmentBook.getEventList());
    }

    public ModelManager() {
        this(new UserPrefs(), new AddressBook(), new AppointmentBook());
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

    @Override
    public Path getAppointmentBookFilePath() {
        return userPrefs.getAppointmentBookFilePath();
    }

    @Override
    public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        userPrefs.setAppointmentBookFilePath(appointmentBookFilePath);
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
    public boolean hasPerson(ReferenceId id) {
        requireNonNull(id);
        return addressBook.hasPerson(id);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasExactPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasExactPerson(person);
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
    public Person resolve(ReferenceId id) {
        return addressBook.resolve(id);
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


    //=========== Scheduler ==================================================================================

    @Override
    public void setSchedule(ReadOnlyAppointmentBook schedule) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return appointmentBook;
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return appointmentBook.hasEvent(event);
    }

    @Override
    public boolean hasExactEvent(Event event) {
        requireNonNull(event);
        return appointmentBook.hasExactEvent(event);
    }

    @Override
    public void deleteEvent(Event event) {
        appointmentBook.removeEvent(event);
    }

    @Override
    public void addEvent(Event event) {
        appointmentBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        appointmentBook.setEvent(target, editedEvent);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return filteredEvents;
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }


    //=========== Misc =======================================================================================

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
                && appointmentBook.equals(other.appointmentBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
