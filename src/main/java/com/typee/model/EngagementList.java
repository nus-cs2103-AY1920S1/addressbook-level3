package com.typee.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;

import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.EngagementConflictChecker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the engagement-list level
 * Duplicates are not allowed (by .isConflictingWith comparison)
 */
public class EngagementList implements ReadOnlyEngagementList {

    private final ObservableList<Engagement> engagements;

    public EngagementList() {
        engagements = FXCollections.observableArrayList();
    }

    /**
     * Creates an EngagementList using the Engagements in the {@code toBeCopied}
     */
    public EngagementList(ReadOnlyEngagementList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the engagement list with {@code engagements}.
     * {@code engagements} must not contain duplicate engagements.
     */
    public void setEngagements(List<Engagement> engagements) {
        this.engagements.setAll(engagements);
    }

    /**
     * Resets the existing data of this {@code EngagementList} with {@code newData}.
     */
    public void resetData(ReadOnlyEngagementList newData) {
        requireNonNull(newData);

        setEngagements(newData.getEngagementList());
    }

    /**
     * Sorts the ObservableList by custom comparator
     */
    public void sort(Comparator<Engagement> comparator) {
        FXCollections.sort(engagements, comparator);
    }

    //// engagement-level operations

    /**
     * Returns true if an engagement with the same identity as {@code engagement}
     * exists in the engagement list.
     */
    public boolean hasEngagement(Engagement engagement) {
        requireNonNull(engagement);
        return engagements.contains(engagement);
    }

    /**
     * Adds an engagement to the engagement list.
     * The engagement must not already exist in the engagement list.
     */
    public void addEngagement(Engagement engagement) {
        engagements.add(engagement);
    }

    /**
     * Replaces the given engagement {@code target} in the list with {@code editedEngagement}.
     * {@code target} must exist in the engagement list.
     * The engagement identity of {@code editedEngagement} must not be the same
     * as another existing engagement in the engagement list.
     */
    public void setEngagement(Engagement target, Engagement editedEngagement) {
        requireNonNull(editedEngagement);

        engagements.set(engagements.indexOf(target), editedEngagement);
    }

    /**
     * Removes {@code key} from this {@code EngagementList}.
     * {@code key} must exist in the engagement list.
     */
    public void removeEngagement(Engagement key) {
        engagements.remove(key);
    }

    @Override
    public boolean isConflictingEngagement(Engagement engagement) {
        return engagements.stream()
                .anyMatch(existingEngagement -> EngagementConflictChecker
                        .areConflicting(existingEngagement, engagement));
    }

    //// util methods

    @Override
    public String toString() {
        return engagements.size() + " engagements";
        // TODO: refine later
    }

    @Override
    public ObservableList<Engagement> getEngagementList() {
        return FXCollections.unmodifiableObservableList(engagements);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EngagementList // instanceof handles nulls
                && engagements.equals(((EngagementList) other).engagements));
    }

    @Override
    public int hashCode() {
        return engagements.hashCode();
    }
}
