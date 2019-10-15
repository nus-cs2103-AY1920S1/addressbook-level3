package com.typee.model;

import javafx.collections.ObservableList;

import com.typee.model.engagement.Engagement;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Engagement> getEngagementList();

}
