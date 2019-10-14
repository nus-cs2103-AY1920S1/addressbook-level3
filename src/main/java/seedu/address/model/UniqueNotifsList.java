package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.notif.Notif;
import seedu.address.model.notif.exceptions.DuplicateNotifException;
import seedu.address.model.notif.exceptions.NotifNotFoundException;

/**
 * Lists of notifs that enforces uniqueness between its elements and does not allow nulls.
 * A notif is considered unique by comparing using {@code Notif#isSameNotif(Notif)}. As such, adding and updating
 * of notifs uses Notif#isSameNotif(Notif) for equality so as to ensure that the notif being added or updated is
 * unique in terms of identity in the UniqueNotifList. However, the removal of a notif uses Notif#equals(Object) so
 * as to ensure that the notif with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueNotifsList {
    private final ObservableList<Notif> internalListNotifs = FXCollections.observableArrayList();
    private final ObservableList<Notif> internalUnmodifiableListNotifs =
            FXCollections.unmodifiableObservableList(internalListNotifs);

    /**
     * Returns true if the respective list contains an equivalent notif as the given argument.
     */
    public boolean contains(Notif toCheck) {
        requireNonNull(toCheck);
        ObservableList list;
        list = internalListNotifs;

        return list.stream().anyMatch(toCheck::isSameNotif);
    }

    /**
     * Adds an notif to the respective list.
     * The notif must not already exist in the list.
     */
    public void add(Notif toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateNotifException();
        }
        internalListNotifs.add(toAdd);

    }


    /**
     * Removes the equivalent notif from the list.
     * The notif must exist in the list.
     */
    public void remove(Notif toRemove) {
        requireNonNull(toRemove);
        boolean isRemoved;
        isRemoved = internalListNotifs.remove(toRemove);

        if (!isRemoved) {
            throw new NotifNotFoundException();
        }
    }

    /**
     * Replaces the entity {@code target} in the list with {@code editedEntity}.
     * {@code target} must exist in the list.
     * The entity identity of {@code editedEntity} must not be the same as another existing entity in the list.
     */
    public void setNotif(Notif target, Notif editedNotif) {
        requireAllNonNull(target, editedNotif);

        if (!target.isSameNotif(editedNotif) && contains(editedNotif)) {
            throw new DuplicateNotifException();
        }

        int index = internalListNotifs.indexOf(target);

        if (index == -1) {
            throw new NotifNotFoundException();
        }

        internalListNotifs.set(index, editedNotif);
    }


    public void setNotifs(UniqueNotifsList replacement) {
        requireNonNull(replacement);
        internalListNotifs.setAll(replacement.internalListNotifs);

    }


    /**
     * Replaces the contents of this list with {@code notifs}.
     * {@code notifs} must not contain duplicate notifs.
     */
    public void setNotifs(List<Notif> notifs) {
        requireAllNonNull(notifs);
        if (!notifsAreUnique(notifs)) {
            throw new DuplicateNotifException();
        }

        internalListNotifs.setAll(notifs);
    }


    /**
     * Returns the backing Notif list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Notif> asUnmodifiableObservableListNotif() {
        return internalUnmodifiableListNotifs;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueNotifsList // instanceof handles nulls
                && (internalListNotifs.equals(((UniqueNotifsList) other).internalListNotifs)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalListNotifs);
    }

    /**
     * Returns true if {@code notifs} contains only unique notifs.
     */
    private boolean notifsAreUnique(List<Notif> notifs) {
        for (int i = 0; i < notifs.size() - 1; i++) {
            for (int j = i + 1; j < notifs.size(); j++) {
                if (notifs.get(i).isSameNotif(notifs.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
