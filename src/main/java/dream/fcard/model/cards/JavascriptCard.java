package dream.fcard.model.cards;

import dream.fcard.util.json.JSONInterface;
import dream.fcard.util.json.jsontypes.JSONValue;
import javafx.scene.Node;

public class JavascriptCard implements FlashCard, JSONInterface {

    protected String front;
    protected String output;

    public JavascriptCard(String frontString, String outputString) {
        front = frontString;
        output = outputString;
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
        return null;
    }
}
