package dream.fcard.model.cards;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Card that evaluates input as javascript code whose output has to match back of card.
 * Format of back of card is a string of assert functions, e.g. "assert(f(4),10); assert(f(14),20);"
 * which evaluate will then run against the user's typed code (user will have to define a function f
 * in a popup editor window)
 */
public class JavascriptCard extends FlashCard {

    protected String front; //question
    protected String back;


    /**
     *
     * @param frontString
     * @param outputString
     */
    public JavascriptCard(String frontString, String outputString) {
        front = frontString;
        back = outputString;
        //priority = LOW_PRIORITY;
        //cardStats = new CardStats();
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
        //priority = priorityLevel;
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

    ///**
    // * Returns boolean value false.
    // * Since no choices exist in this class.
    // *
    // * @return Boolean value false.
    // */
    //@Override
    //public boolean hasChoices() {
    //    return false;
    //}
}
