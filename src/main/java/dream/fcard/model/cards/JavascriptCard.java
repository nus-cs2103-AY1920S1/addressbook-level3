package dream.fcard.model.cards;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import javafx.scene.Node;

/**
 * Card that evaluates input as javascript code whose output has to match back of card.
 */
public class JavascriptCard extends FlashCard {

    /**
     *
     * @param frontString
     * @param outputString
     */
    public JavascriptCard(String frontString, String outputString) {
        front = frontString;
        back = outputString;
        priority = 1;
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
    //@autor

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
     * @return
     */
    @Override
    public Node renderFront() {
        // temporary
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public Node renderBack() {
        // temporary
        return null;
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
}
