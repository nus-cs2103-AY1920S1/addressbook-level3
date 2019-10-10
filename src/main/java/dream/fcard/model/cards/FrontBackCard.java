package dream.fcard.model.cards;

import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonValue;
import javafx.scene.Node;

/**
 * Card that evaluates input to match back of card.
 */
public class FrontBackCard implements FlashCard, JsonInterface {

    protected String back;
    protected String front;

    public FrontBackCard(String frontString, String backString) {
        back = backString;
        front = frontString;
    }

    @Override
    public JsonValue toJson() {
        return null;
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

    public void editFront(String newText) {
        front = newText;
    }

    public void editBack(String newText) {
        back = newText;
    }
}
