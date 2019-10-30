package dream.fcard.model.cards;

import static dream.fcard.model.cards.Priority.LOW_PRIORITY;

import dream.fcard.logic.stats.Statistics;
import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Card that evaluates input as javascript code whose output has to match back of card.
 */
public class JavascriptCard extends FlashCard {

    protected String front; //question
    protected String back;
    protected Statistics stats;
    protected String attempt;


    /**
     *
     * @param frontString
     * @param outputString
     */
    public JavascriptCard(String frontString, String outputString) {
        front = frontString;
        back = outputString;
        priority = LOW_PRIORITY;
        stats = new Statistics();

    }

    /**
     * Constructor to create a Javascript card with user specified priority.
     *
     * @param frontString String of front text.
     * @param outputString String of back text.
     * @param priorityLevel Integer priority level of card.
     */
    //@@author huiminlim
    public JavascriptCard(String frontString, String outputString, int priorityLevel) {
        front = frontString;
        back = outputString;
        priority = priorityLevel;
    }
    //@author

    /**
     *
     * @return
     */
    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        obj.put(Schema.TYPE_FIELD, Schema.JAVASCRIPT_TYPE);
        obj.put(Schema.FRONT_FIELD, front);
        obj.put(Schema.BACK_FIELD, back);
        return new JsonValue(obj);
    }

    @Override
    public FlashCard duplicate() {
        return new JavascriptCard(front, back, 0);
    }

    /**
     *
     * @param in input
     * @return
     */
    @Override
    public Boolean evaluate(String in) {
        return null;
    }

    /**
     *
     * @param newText
     */
    public void editFront(String newText) {
        front = newText;
    }

    /**
     *
     * @param newText
     */
    public void editBack(String newText) {
        back = newText;
    }

    /**
     *
     * @return
     */
    public String getFront() {
        return front;
    }

    /**
     *
     * @return
     */
    public String getBack() {
        return back;
    }

    /**
     * Return the user's attempted code.
     * @return code attempt
     */
    public String getAttempt() {
        return attempt;
    }

    /**
     * Store the user's attempted code.
     * @param attempt code attempt
     */
    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }
}
