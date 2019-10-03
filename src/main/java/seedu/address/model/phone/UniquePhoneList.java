package seedu.address.model.phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.phone.exceptions.DuplicatePhoneException;
import seedu.address.model.phone.exceptions.PhoneNotFoundException;

/**
 * A list of phones that enforces uniqueness between its elements and does not allow nulls.
 * A phone is considered unique by comparing using {@code Phone#isSamePhone((Phone)}.
 * As such, adding and updating of
 * phones uses Phone#isSamePhone(Phone) for equality so as to ensure that the
 * phone being added or updated is
 * unique in terms of identity in the UniquePhoneList.
 * However, the removal of a phone uses Phone#equals(Object) so
 * as to ensure that the phone with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Phone#isSamePhone(Phone)
 */
public class UniquePhoneList implements Iterable<Phone> {

    private final ObservableList<Phone> internalList = FXCollections.observableArrayList();
    private final ObservableList<Phone> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent phone as the given argument.
     */
    public boolean contains(Phone toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePhone);
    }

    /**
     * Adds a phone to the list.
     * The phone must not already exist in the list.
     */
    public void add(Phone toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePhoneException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the phone {@code target} in the list with {@code editedPhone}.
     * {@code target} must exist in the list.
     * The phone identity of {@code editedPhone} must not be the same as another existing phone in the list.
     */
    public void setPhone(Phone target, Phone editedPhone) {
        requireAllNonNull(target, editedPhone);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PhoneNotFoundException();
        }

        if (!target.isSamePhone(editedPhone) && contains(editedPhone)) {
            throw new DuplicatePhoneException();
        }

        internalList.set(index, editedPhone);
    }

    /**
     * Removes the equivalent phone from the list.
     * The phone must exist in the list.
     */
    public void remove(Phone toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PhoneNotFoundException();
        }
    }

    public void setPhones(UniquePhoneList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code phones}.
     * {@code phones} must not contain duplicate phones.
     */
    public void setPhones(List<Phone> phones) {
        requireAllNonNull(phones);
        if (!phonesAreUnique(phones)) {
            throw new DuplicatePhoneException();
        }

        internalList.setAll(phones);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Phone> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Phone> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePhoneList // instanceof handles nulls
                && internalList.equals(((UniquePhoneList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code phones} contains only unique phones.
     */
    private boolean phonesAreUnique(List<Phone> phones) {
        for (int i = 0; i < phones.size() - 1; i++) {
            for (int j = i + 1; j < phones.size(); j++) {
                if (phones.get(i).isSamePhone(phones.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

