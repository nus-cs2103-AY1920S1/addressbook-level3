package dream.fcard.model.cards;

import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import javafx.scene.Node;

/**
 * Card that evaluates input to match back of card.
 */
public class FrontBackCard implements FlashCard {

    protected String back;
    protected String front;

    public FrontBackCard(String frontString, String backString) {
        back = backString;
        front = frontString;
    }

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        obj.put("front", front);
        obj.put("back", back);
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
        return in.equals(back);
    }
}
