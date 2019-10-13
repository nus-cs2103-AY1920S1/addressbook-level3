package seedu.flashcard.model;

import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import static seedu.flashcard.model.DefaultFlashCards.KANETSU_CARD;
import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Some default flashcard lists to be used for testing.
 */
public class DefaultFlashCardList {

    public static List<ShortAnswerFlashcard> temp = Arrays.asList(KANETSU_CARD, MOUNT_BLANC_CARD);

    public static final FlashcardList LIST1_EMPTY = new FlashcardList();
    public static final FlashcardList LIST2 = new FlashcardList((ArrayList<? extends Flashcard>) temp);
}
