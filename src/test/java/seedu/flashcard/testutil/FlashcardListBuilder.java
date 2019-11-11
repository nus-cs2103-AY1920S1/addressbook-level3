package seedu.flashcard.testutil;

import seedu.flashcard.model.FlashcardList;
import seedu.flashcard.model.flashcard.Flashcard;

/**
 * A utility class to help with building flashcardList objects.
 */
public class FlashcardListBuilder {

    private FlashcardList flashcardList;

    public FlashcardListBuilder() {
        flashcardList = new FlashcardList();
    }

    public FlashcardListBuilder(FlashcardList flashcardList) {
        this.flashcardList = flashcardList;
    }

    /**
     * Adds a new {@code Flashcard} to the {@code FlashcardList} that we are building.
     */
    public FlashcardListBuilder withFlashcard(Flashcard flashcard) {
        flashcardList.addFlashcard(flashcard);
        return this;
    }

    public FlashcardList build() {
        return flashcardList;
    }
}
