package dream.fcard.logic.exam;

import java.util.ArrayList;
import java.util.Scanner;

import dream.fcard.model.Deck;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.exceptions.IndexNotFoundException;


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
