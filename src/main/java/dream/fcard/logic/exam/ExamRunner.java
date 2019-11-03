package dream.fcard.logic.exam;

import java.util.ArrayList;

import dream.fcard.gui.controllers.displays.test.EndOfTestAlert;
import dream.fcard.model.cards.FlashCard;

/**
 * Singleton class that ensures that there is only ONE instance of exam.
 */
public class ExamRunner {

    private static Exam exam;

    private ExamRunner() {
    }

    public static void createExam(ArrayList<FlashCard> deck, int duration) {
        exam = new UntimedExam(deck, duration);
    }

    public static Exam getCurrentExam() {
        return exam;
    }

    public static void terminateExam() {
        String result = exam.getResult();
        EndOfTestAlert.display("Results", "Final Score: " + result);
        exam = null;
    }
}
