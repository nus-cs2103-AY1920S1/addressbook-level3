package dream.fcard.logic.exam;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;

import java.util.ArrayList;

/**
 * Singleton class that ensures that there is only ONE instance of exam.
 */
public class ExamRunner {

    private static Exam exam;

    private ExamRunner() {
    }

    public static void createExam(ArrayList<FlashCard> deck) {
        if (exam == null) {
            exam = new UntimedExam(deck);
        }
    }

    public static Exam getCurrentExam() {
        return exam;
    }

    public static void terminateExam() {
        exam = null;
    }
}
