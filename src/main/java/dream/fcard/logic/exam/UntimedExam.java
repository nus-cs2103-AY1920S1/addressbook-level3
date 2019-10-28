package dream.fcard.logic.exam;

import java.util.ArrayList;

import dream.fcard.model.cards.FlashCard;

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
    public FlashCard nextCard() {
        FlashCard card = testDeck.get(index);
        card.getFront();
        index++;
        return card;
    }


}
