package unrealunity.visit.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import unrealunity.visit.commons.core.GuiSettings;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.appointment.Appointment;
import unrealunity.visit.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Adds a new Appointment.
     *
     * @param type The type of appointment. 0 = Reminder, 1 = Follow-Up.
     * @param description The description of the Appointment.
     * @param days How many days the Appointment has remaining.
     */
    void addAppointment(int type, String description, int days) throws CommandException;

    /**
     * Deletes an appointment from VISIT.
     *
     * @param description The description of the appointment to delete.
     * @param days Optional number of days to specifically target the exact appointment to delete.
     */
    void deleteAppointment(String description, int days);

    /**
     * Sorts the list of appointments by days remaining, then name.
     */
    void sortAppointments();

    /**
     * Outputs the Appointments to readable String.
     * Used in the Message of the Day output.
     */
    String outputAppointments();

    /**
     * Reset Appointment Data completely.
     */
    void resetAppointments();

    /**
     * Adds a given alias into the alias table.
     * @param alias Alias name
     * @param aliasTo Alias value
     */
    void addAlias(String alias, String aliasTo);

    /**
     * Removes a given alias from the alias table.
     * @param alias Alias name
     * @return Returns true if alias exists in alias table and removed successfully, returns false otherwise.
     */
    boolean removeAlias(String alias);

    /**
     * Apply a suitable alias to the input command text. A suitable alias is an alias that matches the regex
     * "{alias}($|\\s).*".
     * If multiple matches exists, this method chooses the longest matching alias.
     * @param commandText Command for alias to be applied to.
     * @return Command that alias has been applied to.
     */
    String applyAlias(String commandText);

    /**
     * Gets a list of existing user-defined aliases in either a user-friendly or reusable form.
     * @return list of existing user-defined aliases.
     */
    String getAliases(boolean reusable);
}
