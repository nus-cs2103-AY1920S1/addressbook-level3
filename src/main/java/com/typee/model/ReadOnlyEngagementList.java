package com.typee.model;

import com.typee.model.engagement.Engagement;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyEngagementList {

    /**
     * Returns an unmodifiable view of the list of engagements.
     * This list will not contain any duplicate engagements.
     */
    ObservableList<Engagement> getEngagementList();

}
