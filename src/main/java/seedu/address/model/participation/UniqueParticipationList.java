package seedu.address.model.participation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.participation.exceptions.DuplicateParticipationException;
import seedu.address.model.participation.exceptions.ParticipationNotFoundException;

/**
 * A list of participations that enforces uniqueness between its elements and does not allow nulls.
 * A participation is considered unique by comparing using {@code Participation#equals(Participation)}.
 *
 * Supports a minimal set of list operations.
 *
 * @see Participation#equals(Object)
 */
public class UniqueParticipationList implements Iterable<Participation> {
    private final ObservableList<Participation> internalList = FXCollections.observableArrayList();
    private final ObservableList<Participation> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent participation as the given argument.
     */
    public boolean contains(Participation toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a participation to the list.
     * The participation must not already exist in the list.
     */
    public void add(Participation toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateParticipationException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the participation {@code target} in the list with {@code editedParticipation}.
     * {@code target} must exist in the list.
     * The participation identity of {@code editedParticipation} must
     * not be the same as another existing participation in the list.
     */
    public void setParticipation(Participation target, Participation editedParticipation) {
        requireAllNonNull(target, editedParticipation);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ParticipationNotFoundException();
        }

        if (!target.equals(editedParticipation) && contains(editedParticipation)) {
            throw new DuplicateParticipationException();
        }

        internalList.set(index, editedParticipation);
    }

    /**
     * Removes the equivalent participation from the list.
     * The participation must exist in the list.
     */
    public void remove(Participation toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ParticipationNotFoundException();
        }
    }

    public void setParticipations(seedu.address.model.participation.UniqueParticipationList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code participations}.
     * {@code participations} must not contain duplicate participations.
     */
    public void setParticipations(List<Participation> participations) {
        requireAllNonNull(participations);
        if (!participationsAreUnique(participations)) {
            throw new DuplicateParticipationException();
        }

        internalList.setAll(participations);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Participation> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Participation> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueParticipationList // instanceof handles nulls
                && internalList.equals(((UniqueParticipationList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code participations} contains only unique participations.
     */
    private boolean participationsAreUnique(List<Participation> participations) {
        for (int i = 0; i < participations.size() - 1; i++) {
            for (int j = i + 1; j < participations.size(); j++) {
                if (participations.get(i).equals(participations.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
