package com.typee.model.engagement;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import com.typee.commons.util.CollectionUtil;
import com.typee.model.person.Person;
import com.typee.model.person.exceptions.DuplicatePersonException;
import com.typee.model.person.exceptions.PersonNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniqueEngagementList.
 * However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueEngagementList implements Iterable<Engagement> {

    private final ObservableList<Engagement> internalList = FXCollections.observableArrayList();
    private final ObservableList<Engagement> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Engagement toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isConflictingWith);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Engagement toAdd) {
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
    public void setEngagement(Engagement target, Engagement editedEngagement) {
        CollectionUtil.requireAllNonNull(target, editedEngagement);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isConflictingWith(editedEngagement) && contains(editedEngagement)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedEngagement);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Engagement toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setEngagements(UniqueEngagementList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setEngagements(List<Engagement> engagements) {
        CollectionUtil.requireAllNonNull(engagements);
        if (!engagementsAreUnique(engagements)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(engagements);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Engagement> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Engagement> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEngagementList // instanceof handles nulls
                        && internalList.equals(((UniqueEngagementList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean engagementsAreUnique(List<Engagement> engagements) {
        for (int i = 0; i < engagements.size() - 1; i++) {
            for (int j = i + 1; j < engagements.size(); j++) {
                if (engagements.get(i).isConflictingWith(engagements.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
