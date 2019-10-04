package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.UniquePhoneList;

/**
 * Wraps all data at the phone-book level
 * Duplicates are not allowed (by .isSamePhone comparison)
 */
public class PhoneBook implements ReadOnlyDataBook<Phone> {

    private final UniquePhoneList phones;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        phones = new UniquePhoneList();
    }

    public PhoneBook() {}

    /**
     * Creates an PhoneBook using the Phones in the {@code toBeCopied}
     */
    public PhoneBook(ReadOnlyDataBook<Phone> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the phone list with {@code phones}.
     * {@code phone} must not contain duplicate phones.
     */
    public void setPhones(List<Phone> phones) {
        this.phones.setPhones(phones);
    }

    /**
     * Resets the existing data of this {@code PhoneBook} with {@code newData}.
     */
    public void resetData(ReadOnlyDataBook<Phone> newData) {
        requireNonNull(newData);

        setPhones(newData.getList());
    }

    //// phone-level operations

    /**
     * Returns true if a phone with the same identity as {@code phone} exists in the phone book.
     */
    public boolean hasPhone(Phone phone) {
        requireNonNull(phone);
        return phones.contains(phone);
    }

    /**
     * Adds a phone to SML.
     * The phone must not already exist in the phone book.
     */
    public void addPhone(Phone p) {
        phones.add(p);
    }

    /**
     * Replaces the given phone {@code target} in the list with {@code editedPhone}.
     * {@code target} must exist in the phone book.
     * The phone identity of {@code editedPhone} must not be the same as another existing phone in the phone book.
     */
    public void setPhone(Phone target, Phone editedPhone) {
        requireNonNull(editedPhone);

        phones.setPhone(target, editedPhone);
    }

    /**
     * Removes {@code key} from this {@code PhoneBook}.
     * {@code key} must exist in the phone book.
     */
    public void removePhone(Phone key) {
        phones.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return phones.asUnmodifiableObservableList().size() + " phones";
        // TODO: refine later
    }

    @Override
    public ObservableList<Phone> getList() {
        return phones.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneBook // instanceof handles nulls
                && phones.equals(((PhoneBook) other).phones));
    }

    @Override
    public int hashCode() {
        return phones.hashCode();
    }
}
