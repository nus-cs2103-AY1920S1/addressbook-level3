package seedu.flashcard.model;

/**
 * Helps calculating the correct rate for each model, tag and the whole model set.
 */
public class Statistics {

    /**
     * Sum up all the correct answers in a tag
     */
    public static int getTagTotalCorrect(Tag tag) {
        int overallCorrectAnswer = 0;
        for (Flashcard card : tag.getFlashcards()) {
            overallCorrectAnswer = overallCorrectAnswer + card.getScore().getCorrectAnswerNumber();
        }
        return overallCorrectAnswer;
    }

    /**
     * Sum up all the wrong answers in a tag
     */
    public static int getTagTotalWrong(Tag tag) {
        int overallWrongAnswers = 0;
        for (Flashcard card : tag.getFlashcards()) {
            overallWrongAnswers = overallWrongAnswers + card.getScore().getWrongAnswerNumber();
        }
        return overallWrongAnswers;
    }

    /**
     * Sum up practice times in a tag
     */
    public static int getTagTotal(Tag tag) {
        return getTagTotalCorrect(tag) + getTagTotalWrong(tag);
    }

    /**
     * Sum up all the correct answers in the cardList
     */
    public static int getTotalCorrect(FlashcardList cardList) {
        int overallCorrectAnswer = 0;
        for (Flashcard card : cardList.getAllFlashcards()) {
            overallCorrectAnswer = overallCorrectAnswer + card.getScore().getCorrectAnswerNumber();
        }
        return overallCorrectAnswer;
    }

    /**
     * Sum up all the wrong answers in the cardList
     */
    public static int getTotalWrong(FlashcardList cardList) {
        int overallWrongAnswer = 0;
        for (Flashcard card : cardList.getAllFlashcards()) {
            overallWrongAnswer = overallWrongAnswer + card.getScore().getWrongAnswerNumber();
        }
        return overallWrongAnswer;
    }

    /**
     * Sum up practice times in a cardList
     */
    public static int getTotal(FlashcardList cardList) {
        return getTotalCorrect(cardList) + getTotalWrong(cardList);
    }
}
