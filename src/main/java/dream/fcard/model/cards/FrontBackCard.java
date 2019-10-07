package dream.fcard.model.cards;

import dream.fcard.util.json.jsontypes.JSONValue;
import javafx.scene.Node;

public class FrontBackCard implements FlashCard {

    protected String back;
    protected String front;

    public FrontBackCard(String frontString, String backString) {
        back = backString;
        front = frontString;
    }

    @Override
    public JSONValue toJSON() {
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
}
