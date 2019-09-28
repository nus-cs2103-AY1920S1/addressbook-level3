package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A meme is considered unique by comparing using {@code Meme#isSamePerson(Meme)}. As such, adding and updating of
 * persons uses Meme#isSamePerson(Meme) for equality so as to ensure that the meme being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a meme uses Meme#equals(Object) so
 * as to ensure that the meme with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Meme#isSamePerson(Meme)
 */
public class UniquePersonList implements Iterable<Meme> {

    private final ObservableList<Meme> internalList = FXCollections.observableArrayList();
    private final ObservableList<Meme> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent meme as the given argument.
     */
    public boolean contains(Meme toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a meme to the list.
     * The meme must not already exist in the list.
     */
    public void add(Meme toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the meme {@code target} in the list with {@code editedMeme}.
     * {@code target} must exist in the list.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in the list.
     */
    public void setPerson(Meme target, Meme editedMeme) {
        requireAllNonNull(target, editedMeme);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedMeme) && contains(editedMeme)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedMeme);
    }

    /**
     * Removes the equivalent meme from the list.
     * The meme must exist in the list.
     */
    public void remove(Meme toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code memes}.
     * {@code memes} must not contain duplicate memes.
     */
    public void setPersons(List<Meme> memes) {
        requireAllNonNull(memes);
        if (!personsAreUnique(memes)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(memes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meme> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Meme> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code memes} contains only unique memes.
     */
    private boolean personsAreUnique(List<Meme> memes) {
        for (int i = 0; i < memes.size() - 1; i++) {
            for (int j = i + 1; j < memes.size(); j++) {
                if (memes.get(i).isSamePerson(memes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
