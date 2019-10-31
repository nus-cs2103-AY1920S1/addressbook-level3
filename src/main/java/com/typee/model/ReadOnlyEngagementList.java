package com.typee.model;

import com.typee.model.engagement.Engagement;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an engagement list
 */
public interface ReadOnlyEngagementList {

    /**
     * Returns an unmodifiable view of the list of engagements.
     * This list will not contain any duplicate engagements.
     */
    ObservableList<Engagement> getEngagementList();

    /**
     * Returns true if the input {@code Engagement} clashes with an existing {@code Engagement}.
     * @param engagement input engagement.
     * @return true if there is a conflict.
     */
    boolean isConflictingEngagement(Engagement engagement);

}
