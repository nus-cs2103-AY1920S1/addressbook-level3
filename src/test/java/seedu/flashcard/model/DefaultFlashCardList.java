package seedu.flashcard.model;

import static seedu.flashcard.model.DefaultFlashCards.MOUNT_BLANC_CARD;

import java.util.ArrayList;

import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;

/**
 * Some default flashcard lists to be used for testing.
 */
public class DefaultFlashCardList {

    public static final FlashcardList LIST1 = new FlashcardList(); // empty list for testing
    public static final FlashcardList LIST2 = new FlashcardList(init(MOUNT_BLANC_CARD));

    /**
     * Initialize the default flashcard list to conduct tests
     * @param card short-answer flashcard to be added, in a vararg way
     * @return the arraylist of short-answer flashcards
     */
    public static ArrayList<ShortAnswerFlashcard> init(ShortAnswerFlashcard... card) {
        ArrayList<ShortAnswerFlashcard> list = new ArrayList<>();
        for (ShortAnswerFlashcard c: card) {
            list.add(c);
        }
        return list;
    }
}
