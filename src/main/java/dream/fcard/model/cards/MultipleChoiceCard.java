package dream.fcard.model.cards;

import java.util.ArrayList;

import dream.fcard.util.json.jsontypes.JsonValue;

import javafx.scene.Node;

/**
 * FrontBackCard with additional data of multiple choices.
 */
public class MultipleChoiceCard extends FrontBackCard {

    private ArrayList<String> choices;

    public MultipleChoiceCard(String frontString, String backString, ArrayList<String> choicesArg) {
        super(frontString, backString);
        choices = choicesArg;
    }

    @Override
    public Boolean evaluate(String in) {
        return in.equals(back);
    }

    public void editFront(String newText){
        front = newText;
    }

    public void editBack(String newText){
        back = newText;
    }

    @Override
    public JsonValue toJson() {
        return super.toJson();
    }

    @Override
    public Node renderFront() {
        return super.renderFront();
    }
}
