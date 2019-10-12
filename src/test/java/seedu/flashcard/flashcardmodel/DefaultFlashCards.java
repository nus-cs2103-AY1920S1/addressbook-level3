package seedu.flashcard.flashcardmodel;

import seedu.flashcard.flashcardmodel.flashcard.Answer;
import seedu.flashcard.flashcardmodel.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.flashcardmodel.flashcard.ShortAnswerQuestion;

/**
 * Some built in flashcards to be used in testing periods
 */
public class DefaultFlashCards {

    public static final ShortAnswerFlashcard MOUNT_BLANC_CARD = new ShortAnswerFlashcard(
            new ShortAnswerQuestion("What happened in Mount Blanc Tunnel in 1999?"),
            new Answer("The Mount Blanc Tunnel fire"));
    public static final ShortAnswerFlashcard KANETSU_CARD = new ShortAnswerFlashcard(
            new ShortAnswerQuestion("How long is Kan-etsu Tunnel?"),
            new Answer("11055m + 10926m"));
    public static final ShortAnswerFlashcard ZHONGANANSHAN_CARD = new ShortAnswerFlashcard(
            new ShortAnswerQuestion("How long is Zhongnanshan tunnel?"),
            new Answer("18062m"));
}
