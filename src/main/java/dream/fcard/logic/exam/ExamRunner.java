package dream.fcard.logic.exam;

import dream.fcard.model.Deck;

/**
 * Singleton class that ensures that there is only ONE instance of exam.
 */
public class ExamRunner {

    private static Exam exam;

    private ExamRunner() {
    }

    public static void createExam(Deck deck) {
        exam = new UntimedExam(deck.getSubsetForTest());
    }

    public static Exam getCurrentExam() {
        return exam;
    }
}
