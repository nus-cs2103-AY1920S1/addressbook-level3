package dream.fcard.logic.exam;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;

public class UntimedExam implements Exam {

    private final ArrayList<FlashCard> testDeck;
    private Result result;
    private boolean examOngoing;
    private final Scanner scanner = new Scanner(System.in);

    public UntimedExam(ArrayList<FlashCard> deck) {
        this.testDeck = deck;
        this.result = new Result(testDeck.size());
        this.examOngoing = true;
    }

    @Override
    public void nextCard() {
        if (examOngoing) {
            try {
                FlashCard nextCard = testDeck.get(0);
                System.out.println(nextCard.getFront());
                String answer = promptUserInput();
                result.mark(nextCard.evaluate(answer));
                System.out.println(nextCard.getBack());
                testDeck.remove(0);
            } catch (IndexOutOfBoundsException | IndexNotFoundException e) {
                this.examOngoing = false;
            }
        }
    }

    private String promptUserInput() {
        System.out.print("Your answer for this card:");
        return scanner.nextLine();
    }

}
