package seedu.ezwatchlist.model.show;

import static java.util.Objects.requireNonNull;
import static seedu.ezwatchlist.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.ezwatchlist.model.show.exceptions.DuplicateShowException;
import seedu.ezwatchlist.model.show.exceptions.ShowNotFoundException;
import seedu.ezwatchlist.commons.util.CollectionUtil;

/**
 * A list of shows that enforces uniqueness between its elements and does not allow nulls.
 * A show is considered unique by comparing using {@code show#isSameShow(Show)}. As such, adding and updating of
 * shows uses Show#isSameShow(show) for equality so as to ensure that the show being added or updated is
 * unique in terms of identity in the UniqueShowList. However, the removal of a show uses Show#equals(Object) so
 * as to ensure that the show with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Show#isSameShow(Show)
 */
public class UniqueShowList implements Iterable<Show> {

    private final ObservableList<Show> internalList = FXCollections.observableArrayList();
    private final ObservableList<Show> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent show as the given argument.
     */
    public boolean contains(Show toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameShow);
    }

    /**
     * Adds a show to the list.
     * The show must not already exist in the list.
     */
    public void add(Show toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateShowException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the show {@code target} in the list with {@code editedShow}.
     * {@code target} must exist in the list.
     * The show identity of {@code editedShow} must not be the same as another existing show in the list.
     */
    public void setShow(Show target, Show editedShow) {
        CollectionUtil.requireAllNonNull(target, editedShow);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ShowNotFoundException();
        }

        if (!target.isSameShow(editedShow) && contains(editedShow)) {
            throw new DuplicateShowException();
        }

        internalList.set(index, editedShow);
    }

    /**
     * Removes the equivalent show from the list.
     * The show must exist in the list.
     */
    public void remove(Show toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ShowNotFoundException();
        }
    }

    public void setShows(UniqueShowList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code shows}.
     * {@code shows} must not contain duplicate shows.
     */
    public void setShows(List<Show> shows) {
        CollectionUtil.requireAllNonNull(shows);
        if (!showsAreUnique(shows)) {
            throw new DuplicateShowException();
        }

        internalList.setAll(shows);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Show> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Show> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueShowList // instanceof handles nulls
                && internalList.equals(((UniqueShowList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code shows} contains only unique shows.
     */
    private boolean showsAreUnique(List<Show> shows) {
        for (int i = 0; i < shows.size() - 1; i++) {
            for (int j = i + 1; j < shows.size(); j++) {
                if (shows.get(i).isSameShow(shows.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
