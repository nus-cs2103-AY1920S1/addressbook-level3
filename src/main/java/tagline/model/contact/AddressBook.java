// @@author yehezkiel01

package tagline.model.contact;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueContactList contacts;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        contacts = new UniqueContactList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Contacts in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.containsContact(contact);
    }

    /**
     * Adds a contact to the address book.
     * The contact must not already exist in the address book.
     */
    public void addContact(Contact p) {
        requireNonNull(p.getContactId());
        assert (findContact(p.getContactId()).isEmpty()) : "Contact id is not unique";
        contacts.addContact(p);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing contact in the address
     * book.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);
        assert (target.getContactId().equals(editedContact.getContactId()))
                : "Contact id is permanent and cannot be edited";
        contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        contacts.removeContact(key);
    }

    /**
     * Finds a contact with ID equal to {@code contactId}.
     */
    public Optional<Contact> findContact(ContactId contactId) {
        return contacts.findContact(contactId);
    }

    //// util methods

    /**
     * Returns the number of contacts in AddressBook.
     *
     * @return the number of contacts.
     */
    public int size() {
        return contacts.size();
    }

    @Override
    public String toString() {
        return contacts.asUnmodifiableObservableList().size() + " contacts";
        // TODO: refine later
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && contacts.equals(((AddressBook) other).contacts));
    }

    @Override
    public int hashCode() {
        return contacts.hashCode();
    }
}
