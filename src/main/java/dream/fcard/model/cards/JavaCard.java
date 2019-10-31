package dream.fcard.model.cards;

import java.util.ArrayList;

import dream.fcard.model.TestCase;
import dream.fcard.util.DeepCopy;

/**
 * Card that evaluates input as javascript code whose output has to match back of card.
 */
public class JavaCard extends FlashCard {

    private String question;
    private ArrayList<TestCase> testCases;
    private String attempt;

    public JavaCard(String question, ArrayList<TestCase> testCases) {
        this.question = question;
        this.testCases = testCases;
    }

    @Override
    public Boolean evaluate(String in) {
        return null;
    }

    @Override
    public String getFront() {
        return question;
    }

    @Override
    public String getBack() {
        return null;
    }

    @Override
    public void editFront(String newText) {
        this.front = newText;
    }

    @Override
    public void editBack(String newText) {
        //unused
    }

    @Override
    public FlashCard duplicate() {
        String front = question;
        return new JavaCard(front, DeepCopy.duplicateTestCases(testCases));
    }

    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    @Override
    public void updateScore(Boolean isCorrect) {

    }
}
