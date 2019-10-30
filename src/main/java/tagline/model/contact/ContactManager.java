package tagline.model.contact;

import static java.util.Objects.requireNonNull;
import static tagline.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory contact model of the address book data.
 */
public class ContactManager implements ContactModel {
    private final AddressBook addressBook;
    private final FilteredList<Contact> filteredContacts;

    /**
     * Initializes a ContactManager with the given addressBook.
     */
    public ContactManager(ReadOnlyAddressBook addressBook) {
        this.addressBook = new AddressBook(addressBook);
        filteredContacts = new FilteredList<>(this.addressBook.getContactList());
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
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return addressBook.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        addressBook.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        contact.setContactId(generateUniqueId());
        addressBook.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);
        addressBook.setContact(target, editedContact);
    }

    @Override
    public Optional<Contact> findContact(ContactId id) {
        return addressBook.findContact(id);
    }

    /**
     * Generates a random unique contact Id.
     *
     * @return a random unique contact Id.
     */
    private ContactId generateUniqueId() {
        // If the number of contacts has started to fill up the current digit.
        while (addressBook.size() >= Math.pow(10, ContactId.getDigit()) / 2) {
            ContactId.incrementDigit();
        }

        int limit = (int) Math.pow(10, ContactId.getDigit());
        int randomId = ThreadLocalRandom.current().nextInt(limit);
        while (findContact(new ContactId(randomId)).isPresent()) {
            randomId = ThreadLocalRandom.current().nextInt(limit);
        }

        return new ContactId(randomId);
    }

    //=========== Filtered Contact List Accessors =============================================================

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
    }

    @Override
    public ObservableList<Contact> getFilteredContactListWithPredicate(Predicate<Contact> predicate) {
        requireNonNull(predicate);

        FilteredList<Contact> filteredContactsCopy = new FilteredList<>(addressBook.getContactList());
        filteredContactsCopy.setPredicate(predicate);
        return filteredContactsCopy;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ContactManager)) {
            return false;
        }

        // state check
        ContactManager other = (ContactManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredContacts.equals(other.filteredContacts);
    }
}
