package dream.fcard.logic.exam;

import java.util.ArrayList;
import java.util.Scanner;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;


/**
 * ...
 */
public class ExamRunner {

    private Deck deck;

    public ExamRunner(Deck deck) {
        this.deck = deck;
    }

    /**
     * Exam driver method.
     */
    public UntimedExam initExam() throws IndexNotFoundException {
        ArrayList<FlashCard> deckForTest = this.deck.getSubsetForTest();
        UntimedExam exam = new UntimedExam(deckForTest);
        return exam;
    }
}
