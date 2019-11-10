package dream.fcard.logic.exam;

import java.util.ArrayList;

import dream.fcard.gui.controllers.displays.test.EndOfTestAlert;
import dream.fcard.model.cards.FlashCard;

/**
 * Singleton class that ensures that there is only ONE instance of exam.
 */
public class ExamRunner {

    private static Exam exam;
    private static boolean examOngoing;

    private ExamRunner() {
    }

    /**
     * static method that creates an exam.
     * @param deck contains cards for the exam.
     * @param duration contains duration of exam in seconds.
     */
    public static void createExam(ArrayList<FlashCard> deck, int duration) {
        exam = new StandardExam(deck, duration);
        examOngoing = true;
    }

    public static Exam getCurrentExam() {
        return exam;
    }

    /**
     * method that terminates an exam.
     * Calls a popup window that displays the result.
     * Removes the instance of the exam in singleton class.
     */
    public static void terminateExam() {
        String result = exam.getResult();
        EndOfTestAlert.display("Results", "Final Score: " + result);
        examOngoing = false;
    }

    public static void clearExam() {
        exam = null;
    }

    public static boolean isExamOngoing() {
        return examOngoing;
    }
}
