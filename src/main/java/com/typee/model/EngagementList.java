package com.typee.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.UniqueEngagementList;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isConflictingWith comparison)
 */
public class EngagementList implements ReadOnlyEngagementList {

    private final UniqueEngagementList engagements;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        engagements = new UniqueEngagementList();
    }

    public EngagementList() {}

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
        this.engagements.setEngagements(engagements);
    }

    /**
     * Resets the existing data of this {@code EngagementList} with {@code newData}.
     */
    public void resetData(ReadOnlyEngagementList newData) {
        requireNonNull(newData);

        setEngagements(newData.getEngagementList());
    }

    //// person-level operations

    /**
     * Returns true if an engagement with the same identity as {@code engagement} exists in the address book.
     */
    public boolean hasEngagement(Engagement engagement) {
        requireNonNull(engagement);
        return engagements.contains(engagement);
    }

    /**
     * Adds an engagement to the address book.
     * The engagement must not already exist in the address book.
     */
    public void addEngagement(Engagement engagement) {
        engagements.add(engagement);
    }

    /**
     * Replaces the given engagement {@code target} in the list with {@code editedEngagement}.
     * {@code target} must exist in the address book.
     * The engagement identity of {@code editedEngagement} must not be the same
     * as another existing engagement in the address book.
     */
    public void setPerson(Engagement target, Engagement editedEngagement) {
        requireNonNull(editedEngagement);

        engagements.setEngagement(target, editedEngagement);
    }

    /**
     * Removes {@code key} from this {@code EngagementList}.
     * {@code key} must exist in the address book.
     */
    public void removeEngagement(Engagement key) {
        engagements.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return engagements.asUnmodifiableObservableList().size() + " engagements";
        // TODO: refine later
    }

    @Override
    public ObservableList<Engagement> getEngagementList() {
        return engagements.asUnmodifiableObservableList();
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
