package dream.fcard.model.cards;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import javafx.scene.Node;

/**
 * Card that evaluates input as javascript code whose output has to match back of card.
 */
public class JavascriptCard implements FlashCard {

    protected String front;
    protected String back;

    public JavascriptCard(String frontString, String outputString) {
        front = frontString;
        back = outputString;
    }

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        obj.put(Schema.TYPE_FIELD, Schema.JAVASCRIPT_TYPE);
        obj.put(Schema.FRONT_FIELD, front);
        obj.put(Schema.BACK_FIELD, back);
        return new JsonValue(obj);
    }

    @Override
    public Node renderFront() {
        return null;
    }

    @Override
    public Node renderBack() {
        return null;
    }

    @Override
    public Boolean evaluate(String in) {
        return null;
    }
}
