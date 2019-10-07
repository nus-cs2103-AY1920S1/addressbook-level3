package dream.fcard.model.cards;

import dream.fcard.util.json.jsontypes.JSONValue;
import java.util.ArrayList;
import javafx.scene.Node;

public class MultipleChoiceCard extends FrontBackCard {

    private ArrayList<String> choices;

    public MultipleChoiceCard(String frontString, String backString, ArrayList<String> choicesArg) {
        super(frontString, backString);
        choices = choicesArg;
    }

    @Override
    public JSONValue toJSON() {
        return super.toJSON();
    }

    @Override
    public Node renderFront() {
        return super.renderFront();
    }
}
