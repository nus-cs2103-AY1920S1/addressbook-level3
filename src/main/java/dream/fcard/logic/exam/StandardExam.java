package dream.fcard.logic.exam;

import java.util.ArrayList;

import dream.fcard.gui.controllers.cards.backview.McqCardBack;
import dream.fcard.gui.controllers.cards.backview.SimpleCardBack;
import dream.fcard.gui.controllers.cards.frontview.BasicFrontBackCard;
import dream.fcard.gui.controllers.cards.frontview.JavaFront;
import dream.fcard.gui.controllers.cards.frontview.JsCard;
import dream.fcard.gui.controllers.cards.frontview.McqCard;
import dream.fcard.logic.respond.Consumers;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.JavaCard;
import dream.fcard.model.cards.JavascriptCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.IndexNotFoundException;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Untimed Exam mode.
 */
public class StandardExam implements Exam {

    private final ArrayList<FlashCard> testDeck;
    private Result result;
    private int index = 0;
    private ArrayList<FlashCard> initialDeck;
    private int durationInSeconds;

    public StandardExam(ArrayList<FlashCard> deck, int durationInSeconds) {
        this.initialDeck = deck;
        this.testDeck = createTestDeck();
        this.result = new Result(testDeck.size());
        this.durationInSeconds = durationInSeconds;
    }

    @Override
    public FlashCard getCurrentCard() {
        assert(index >= 0);
        return testDeck.get(index);
    }

    @Override
    public void gradeQuestion(Boolean isCorrect) throws IndexNotFoundException {
        if (isCorrect) {
            result.mark(true);
        }
    }

    @Override
    public boolean upIndex() {
        if (index < testDeck.size() - 1) {
            this.index++;
            return false;
        } else if (index == testDeck.size()) {
            Consumers.doTask("DISPLAY_MESSAGE", "No more cards in this deck!");
            return true;
        } else {
            return true;
        }
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

    /**
     * Duplicates the test deck so that stats class can use it (for Nat).
     * @return ArrayList of Flashcards from the initial deck.
     */
    private ArrayList<FlashCard> createTestDeck() {
        ArrayList<FlashCard> testDeckConsumer = new ArrayList<>();
        for (FlashCard card : initialDeck) {
            FlashCard duplicateCard = card.duplicate();
            testDeckConsumer.add(duplicateCard);
        }
        return testDeckConsumer;
    }

    @Override
    public int getDuration() {
        return this.durationInSeconds;
    }

    @Override
    public Pane getCardDisplayFront() {
        FlashCard cardOnDisplay = getCurrentCard();
        String typeOfCard = cardOnDisplay.getClass().getSimpleName();
        switch (typeOfCard) {
        case "FrontBackCard":
            return new BasicFrontBackCard(cardOnDisplay);
        case "MultipleChoiceCard":
            return new McqCard((MultipleChoiceCard) cardOnDisplay);
        case "JavascriptCard":
            Consumers.doTask("CLEAR_CARD_DISPLAY", true);
            return new JsCard((JavascriptCard) cardOnDisplay);
        case "JavaCard":
            Consumers.doTask("CLEAR_CARD_DISPLAY", true);
            return new JavaFront((JavaCard) cardOnDisplay);
        default:
            return new VBox();
        }
    }

    @Override
    public Pane getCardDisplayBack() {
        FlashCard card = getCurrentCard();
        Consumers.doTask("CLEAR_DECK_DISPLAY", true);
        String typeOfCard = card.getClass().getSimpleName();
        if (typeOfCard.equals("FrontBackCard")) {
            String back = card.getBack();
            return new SimpleCardBack(back);
        } else if (typeOfCard.equals("MultipleChoiceCard")) {
            return new McqCardBack((MultipleChoiceCard) card);
        } else {
            return new VBox();
        }
    }
}
