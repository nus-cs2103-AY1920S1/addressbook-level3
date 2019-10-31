package com.typee.model.engagement;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import com.typee.commons.util.CollectionUtil;
import com.typee.model.engagement.exceptions.DuplicateEngagementException;
import com.typee.model.engagement.exceptions.EngagementNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of engagements that enforces uniqueness between its elements and does not allow nulls.
 * An engagement is considered unique by comparing using {@code Engagement#isSameEngagement(Engagement)}.
 * As such, adding and updating of engagements uses {@code Engagement#isSameEngagement(Engagement)}
 * for equality so as to ensure that the engagement being added or updated is
 * unique in terms of details in the UniqueEngagementList.
 * However, the removal of an engagement uses Engagement#equals(Object) so
 * as to ensure that the engagement with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Engagement#isSameEngagement(Engagement)
 */
public class UniqueEngagementList<E> implements Iterable<Engagement> {

    private final ObservableList<Engagement> internalList = FXCollections.observableArrayList();
    private final ObservableList<Engagement> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent engagement as the given argument.
     */
    public boolean contains(Engagement toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isConflictingWith);
    }

    /**
     * Adds an engagement to the list.
     * The engagement must not already exist in the list.
     */
    public void add(Engagement toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEngagementException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the engagement {@code target} in the list with {@code editedEngagement}.
     * {@code target} must exist in the list.
     * The details of {@code editedEngagement} must not be the same as another existing engagement in the list.
     */
    public void setEngagement(Engagement target, Engagement editedEngagement) {
        CollectionUtil.requireAllNonNull(target, editedEngagement);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EngagementNotFoundException();
        }

        if (!target.isConflictingWith(editedEngagement) && contains(editedEngagement)) {
            throw new DuplicateEngagementException();
        }
        internalList.set(index, editedEngagement);
    }

    /**
     * Removes the equivalent engagement from the list.
     * The engagement must exist in the list.
     */
    public void remove(Engagement toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EngagementNotFoundException();
        }
    }

    public void setEngagements(UniqueEngagementList<E> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code engagements}.
     * {@code engagements} must not contain duplicate engagements.
     */
    public void setEngagements(List<Engagement> engagements) {
        CollectionUtil.requireAllNonNull(engagements);
        if (!engagementsAreUnique(engagements)) {
            throw new DuplicateEngagementException();
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
                        && internalList.equals(((UniqueEngagementList<E>) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code engagements} contains only unique engagements.
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
