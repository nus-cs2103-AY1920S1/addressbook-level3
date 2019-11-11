package dream.fcard.model.cards;

import java.util.ArrayList;

import dream.fcard.logic.storage.Schema;
import dream.fcard.model.TestCase;
import dream.fcard.util.DeepCopy;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

//import dream.fcard.util.json.JsonInterface;



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
        cardResult = -1;

        // attribute from flashcard class
        priority = Priority.LOW_PRIORITY;
    }

    public JavaCard(String question, ArrayList<TestCase> testCases, int priorityLevel) {
        this.question = question;
        this.testCases = testCases;
        cardResult = -1;

        // attribute from flashcard class
        priority = priorityLevel;
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
    public JsonValue toJson() {
        JsonArray arr = new JsonArray();
        for (TestCase t : testCases) {
            try {
                arr.add(t.toJson().getObject());
            } catch (JsonWrongValueException e) {
                System.out.println("ERROR IN TESTCASES : " + e.getMessage());
            }
        }

        JsonObject obj = new JsonObject();
        obj.put(Schema.TYPE_FIELD, Schema.JAVA_TYPE);
        obj.put(Schema.FRONT_FIELD, question);
        obj.put(Schema.TEST_CASES_FIELD, arr);
        return new JsonValue(obj);
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
        if (isCorrect) {
            this.cardResult = 1;
        } else {
            this.cardResult = 0;
        }
    }

    @Override
    public int getCardResult() {
        return this.cardResult;
    }

    public ArrayList<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(ArrayList<TestCase> testCases) {
        this.testCases = testCases;
    }
}
