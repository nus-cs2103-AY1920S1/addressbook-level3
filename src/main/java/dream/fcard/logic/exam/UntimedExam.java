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
    private ArrayList<FlashCard> statDeck;

    public UntimedExam(ArrayList<FlashCard> deck) {
        this.testDeck = deck;
        this.result = new Result(testDeck.size());
        this.statDeck = createStatDeck();
    }

    @Override
    public FlashCard getCurrentCard() {
        return testDeck.get(index);
    }

    @Override
    public FlashCard getCurrentStatCard() {
        return statDeck.get(index);
    }

    @Override
    public boolean parseUserInputAndGrade(Boolean isCorrect) throws IndexNotFoundException {
        if (isCorrect) {
            result.mark(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void upIndex() {
        this.index++;
    }

    @Override
    public void downIndex() {
        if (this.index != 0) {
            this.index--;
        }
    }

    @Override
    public String getResult() {
        return this.result.getScore();
    }

    @Override
    public ArrayList<FlashCard> getDeck() {
        return this.testDeck;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void addDuplicateCardToDeck(FlashCard card) {
        this.statDeck.add(card);
    }

    private ArrayList<FlashCard> createStatDeck() {
        ArrayList<FlashCard> statDeckConsumer = new ArrayList<>();
        for (FlashCard card : testDeck) {
            FlashCard duplicateCard = card.duplicate();
            statDeckConsumer.add(duplicateCard);
        }
        return statDeckConsumer;
    }
}
