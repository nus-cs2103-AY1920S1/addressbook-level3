package dream.fcard.logic.exam;

import java.util.Scanner;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;


/**
 * ...
 */
public class ExamRunner implements Exam {

    private final int deckSize;
    private final Scanner scanner;
    private Deck deck;
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
    @Override
    public void runExam() throws IndexNotFoundException {
        System.out.println("Beginning test:");
        iterateThroughDeck();
        System.out.println("Score:" + score + "/" + deckSize);
    }

    /**
     * Method that cycles through the deck and asks each question.
     */
    private void iterateThroughDeck() throws IndexNotFoundException {
        for (FlashCard card : deck.getCards()) {
            //Load front of card
            System.out.println(card.getFront());
            // Ask user for input
            String answer = promptUserInput();
            // Check if the answer is correct

            boolean isAnswerCorrect;
            try {
                isAnswerCorrect = card.evaluate(answer);
            } catch (IndexNotFoundException i) {
                // for mcq test, if input is invalid index

                // do something
                // decide when to handle
                throw new IndexNotFoundException(i.getMessage());
            }

            if (isAnswerCorrect) {
                score++;
            }
            //Display the correct answer
            System.out.println("Correct Answer:" + card.getBack());
        }
    }

    private String promptUserInput() {
        System.out.print("Your answer for this card:");
        return scanner.nextLine();
    }
}
