package dream.fcard.logic.exam;

import java.util.ArrayList;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;

/**
 * Untimed Exam mode.
 */
public class UntimedExam implements Exam {

    private final ArrayList<FlashCard> testDeck;
    private Result result;
    private int index = 0;

    public UntimedExam(ArrayList<FlashCard> deck) {
        this.testDeck = deck;
        this.result = new Result(testDeck.size());
    }

    @Override
    public FlashCard getCurrentCard() {
        return testDeck.get(index);
    }

    @Override
    public void parseUserInputAndGrade(String answer) throws IndexNotFoundException {
        FlashCard currentCard = testDeck.get(index);
        boolean isCorrect = currentCard.evaluate(answer);
        result.mark(isCorrect);
    }

    @Override
    public void upIndex() {
        this.index++;
    }

    @Override
    public String getResult() {
        return this.result.getScore();
    }



}
