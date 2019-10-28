package dream.fcard.logic.exam;

import dream.fcard.model.Deck;

import dream.fcard.model.cards.FlashCard;

/**
 * Singleton class that ensures that there is only ONE instance of exam.
 */
public class ExamRunner {

    private static Exam exam;

    private ExamRunner() {

    /**
     * Exam driver method.
     */
    @Override
    public void initExam() {
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
            } catch (IndexOutOfBoundsException e) {
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

    public static void createExam(Deck deck) {
        exam = new UntimedExam(deck.getSubsetForTest());
    }

    public static Exam getCurrentExam() {
        return exam;
    }
}
