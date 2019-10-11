package tagline.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.model.contact.Contact;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Contact> PREDICATE_SHOW_ALL_CONTACTS = unused -> true;

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

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the address
     * book.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * Returns an unmodifiable view of the filtered contact list
     */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);
}
