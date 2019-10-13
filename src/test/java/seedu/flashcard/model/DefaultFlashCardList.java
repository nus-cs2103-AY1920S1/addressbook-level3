package seedu.flashcard.model;

import static seedu.flashcard.model.DefaultFlashCards.KANETSU_CARD;
import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;

/**
 * Some default flashcard lists to be used for testing.
 */
public class DefaultFlashCardList {

    public static final List<ShortAnswerFlashcard> TEMP_SHORTANS = Arrays.asList(KANETSU_CARD, MOUNT_BLANC_CARD);

    public static final FlashcardList LIST1 = new FlashcardList(); // empty list for testing
    public static final FlashcardList LIST2 = new FlashcardList((ArrayList<ShortAnswerFlashcard>) TEMP_SHORTANS);
}
