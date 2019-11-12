package seedu.address.model;

import javafx.collections.ObservableList;

import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of StudyBuddyPro's cheatsheets
 */
public interface ReadOnlyStudyBuddyProCheatSheets {


    /**
     * Returns an unmodifiable view of the cheatsheets list.
     * This list will not contain any duplicate cheatsheets.
     */
    ObservableList<CheatSheet> getCheatSheetList();

    /**
     * Returns an unmodifiable view of the tag list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();
}
