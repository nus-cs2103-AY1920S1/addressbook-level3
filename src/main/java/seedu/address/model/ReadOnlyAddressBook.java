package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.category.Category;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the flashcard list.
     * This list will not contain any duplicate flashcard.
     */
    ObservableList<FlashCard> getFlashcardList();

    /**
     * Returns an unmodifiable view of the deadline list.
     */
    ObservableList<Deadline> getDeadlineList();

    /**
     * Returns an unmodifiable view of the category list.
     */
    ObservableList<Category> getCategoryList();

}
