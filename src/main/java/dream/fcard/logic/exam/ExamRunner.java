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
    private Result result;
    private boolean examOngoing;

    public ExamRunner(Deck deck) {
        this.deck = deck;
        this.deckSize = deck.getCards().size();
        this.scanner = new Scanner(System.in);
        this.result = new Result(deckSize);
    }

    /**
     * Exam driver method.
     */
    @Override
    public void initExam() throws IndexNotFoundException {
        System.out.println("Beginning test:");
        this.examOngoing = true;
    }

    /**
     * Method that pulls up the next card.
     */
    public void nextCard() {
        if (examOngoing) {
            try {
                FlashCard nextCard = deck.getCards().get(0);
                System.out.println(nextCard.getFront());
                String answer = promptUserInput();
                result.mark(nextCard.evaluate(answer));
                System.out.println(nextCard.getBack());
                deck.getCards().remove(0);
            } catch (IndexOutOfBoundsException | IndexNotFoundException e) {
                this.examOngoing = false;

            }
        }
        /* for (FlashCard card : deck.getCards()) {
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
        } */
    }

    private String promptUserInput() {
        System.out.print("Your answer for this card:");
        return scanner.nextLine();
    }
}
