package dream.fcard.logic.exam;

/**
 * Singleton class that ensures that there is only ONE instance of exam.
 */
public class ExamRunner {

    private static Exam exam;

    private ExamRunner() {
    }

    /**
     * Method that pulls up the next card.
     */
    public void nextCard() {
        //if (examOngoing) {
        //    try {
        //        FlashCard nextCard = deck.getCards().get(0);
        //        System.out.println(nextCard.getFront());
        //        String answer = promptUserInput();
        //        result.mark(nextCard.evaluate(answer));
        //        System.out.println(nextCard.getBack());
        //        deck.getCards().remove(0);
        //    } catch (IndexOutOfBoundsException | IndexNotFoundException e) {
        //        this.examOngoing = false;
        //
        //    }
        //}
    }

    public static Exam getCurrentExam() {
        return exam;
    }
}
