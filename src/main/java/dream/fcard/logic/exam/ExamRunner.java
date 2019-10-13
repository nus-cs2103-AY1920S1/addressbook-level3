package dream.fcard.logic.exam;

import java.util.Scanner;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;



/**
 * ...
 */
public class ExamRunner {

    private Deck deck;
    private final int deckSize;
    private final Scanner scanner;
    private int score;

    public ExamRunner(Deck deck) {
        this.deck = deck;
        this.deckSize = deck.getCards().size();
        this.scanner = new Scanner(System.in);
        this.score = 0;
    }

    /**
     * Exam driver method.
     */
    public void runExam() {
        System.out.println("Beginning test:");
        iterateThroughDeck();
        System.out.println("Score:" + score + "/" + deckSize);
    }

    /**
     * Method that cycles through the deck and asks each question.
     */
    private void iterateThroughDeck() {
        for (FlashCard card : deck.getCards()) {
            //Load front of card
            card.renderFront();
            // Ask user for input
            String ans = promptUserInput();
            // Check if the answer is correct
            if (card.evaluate(ans)) {
                score++;
            }
            //Display the correct answer
            card.renderBack();
        }
    }

    private String promptUserInput() {
        System.out.print("Your answer for this card:");
        return scanner.nextLine();
    }
}
