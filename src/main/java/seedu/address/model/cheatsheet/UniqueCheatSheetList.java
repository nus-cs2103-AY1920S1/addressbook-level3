package seedu.address.model.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents a list of CheatSheets that are unique.
 * See also @UniquePersonList
 */

public class UniqueCheatSheetList implements Iterable<CheatSheet> {

    private final ObservableList<CheatSheet> internalList = FXCollections.observableArrayList();
    private final ObservableList<CheatSheet> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(CheatSheet toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCheatSheet);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(CheatSheet toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet) {
        requireAllNonNull(target, editedCheatSheet);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameCheatSheet(editedCheatSheet) && contains(editedCheatSheet)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedCheatSheet);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(CheatSheet toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setCheatSheets(UniqueCheatSheetList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setCheatSheets(List<CheatSheet> cheatSheets) {
        requireAllNonNull(cheatSheets);
        if (!cheatSheetsAreUnique(cheatSheets)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(cheatSheets);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CheatSheet> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CheatSheet> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCheatSheetList // instanceof handles nulls
                && internalList.equals(((UniqueCheatSheetList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean cheatSheetsAreUnique(List<CheatSheet> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSameCheatSheet(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
