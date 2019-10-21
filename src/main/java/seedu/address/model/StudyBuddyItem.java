package seedu.address.model;

import seedu.address.model.tag.Tag;

/**
 * Interface StudyBuddyItem
 * For future consideration
 * Perhaps get cheatSheet, notes, flashcard objects to extend this class
 * Such that we can extract out the FilteredList to have generics : ? extends StudyBuddyItem?
 */

public interface StudyBuddyItem {

    /**
     * Checks if this studyBuddyItem contains the tag
     * @return true if it has the tag, false otherwise
     */
    public boolean containsTag(Tag tag);
}
