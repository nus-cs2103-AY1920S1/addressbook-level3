package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.exceptions.DuplicateStudyBuddyItemException;
import seedu.address.model.exceptions.StudyBuddyItemNotFoundException;

/**
 * A list of StudyBuddyItems that enforces uniqueness between its elements and does not allow nulls.
 * A StudyBuddyItems is considered unique by comparing using {@code StudyBuddyItem#equals(Object)}.
 * As such, adding and updating of StudyBuddyItem uses StudyBuddyItem#equals(Object)
 * for equality so as to ensure that the StudyBuddyItem being added or
 * updated is unique in the UniqueStudyBuddyList. The removal of a flashcard uses
 * StudyBuddyItem#equals(Object) as well so
 * as to ensure that the flashcard with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueStudyBuddyList<T extends StudyBuddyItem> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent flashcard as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a flashcard to the list.
     * The flashcard must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudyBuddyItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the flashcard {@code target} in the list with {@code editedFlashcard}.
     * {@code target} must exist in the list.
     * The flashcard's fields of {@code editedFlashcard} must not be the same as another existing flashcard in the list.
     */
    public void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudyBuddyItemNotFoundException();
        }

        if (!target.equals(editedItem) && contains(editedItem)) {
            throw new DuplicateStudyBuddyItemException();
        }

        internalList.set(index, editedItem);
    }

    /**
     * Removes the equivalent flashcard from the list.
     * The flashcard must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StudyBuddyItemNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     */
    public void setStudyBuddyItems(UniqueStudyBuddyList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code flashcards}.
     * {@code flashcards} must not contain duplicate flashcards.
     */
    public void setStudyBuddyItems(List<T> studyBuddyItems) {
        requireAllNonNull(studyBuddyItems);
        if (!studyBuddyItemsAreUnique(studyBuddyItems)) {
            throw new DuplicateStudyBuddyItemException();
        }

        internalList.setAll(studyBuddyItems);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudyBuddyList // instanceof handles nulls
                && internalList.equals(((UniqueStudyBuddyList ) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code flashcards} contains only unique flashcards.
     */
    private boolean studyBuddyItemsAreUnique(List<T> studyBuddyItems) {
        for (int i = 0; i < studyBuddyItems.size() - 1; i++) {
            for (int j = i + 1; j < studyBuddyItems.size(); j++) {
                if (studyBuddyItems.get(i).equals(studyBuddyItems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
