package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.UniqueContactList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public class ContactManager implements ReadOnlyContact {
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

    public ContactManager() {}

    /**
     * Creates an ContactManager using the Persons in the {@code toBeCopied}
     */
    public ContactManager(ReadOnlyContact toBeCopied) {
        this();
        resetDataContact(toBeCopied);
    }

    //// For CONTACT list overwrite operations

    /**
     * Resets the existing data of this {@code ContactManager} with {@code newData}.
     */
    public void resetDataContact(ReadOnlyContact newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
    }

    /**
     * Replaces the contents of the contacts list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    //// contacts-level operations

    /**
     * Returns true if a contacts with the same identity as {@code contacts} exists in the contact list.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Returns true if a contact with the same phone as {@code phone} exists in the contact list.
     */
    public boolean hasPhone(Phone phone) {
        requireNonNull(phone);
        return contacts.containsPhone(phone);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addContact(Contact c) {
        contacts.add(c);
    }

    /**
     * Adds a contacts to the address book.
     * The contacts must not already exist in the address book.
     */
    public void addContactAtIndex(Index index, Contact c) {
        contacts.addAtIndex(index, c);
    }

    /**
     * Replaces the given contacts {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contacts identity of {@code editedContact} must not be the same as another existing contacts in the
     * address book.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);

        contacts.setContact(target, editedContact);
    }

    /**
     * Returns an Optional Contact by searching the contact list for a contact with the same phone as {@code phone}.
     */
    public Optional<Contact> getContactWithPhone(Phone toGet) {
        requireNonNull(toGet);
        return contacts.getWithPhone(toGet);
    }

    /**
     * Removes {@code key} from this {@code ContactManager}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        contacts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return contacts.asUnmodifiableObservableList().size() + " contacts,";
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContactManager // instanceof handles nulls
                && contacts.equals(((ContactManager) other).contacts));
    }

    @Override
    public int hashCode() {
        return contacts.hashCode();
    }
}
