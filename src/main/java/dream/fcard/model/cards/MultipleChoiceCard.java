package dream.fcard.model.cards;

import java.util.ArrayList;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;

import javafx.scene.Node;

/**
 * FrontBackCard with additional data of multiple choices.
 */
public class MultipleChoiceCard extends FrontBackCard {

    private ArrayList<String> choices;
    private int answerIndex;

    /**
     * Construct a multiple choice card.
     * @param frontString   front string
     * @param backString    original sorted answer index
     * @param choicesArg    original sorted choices
     */
    public MultipleChoiceCard(String frontString, String backString, ArrayList<String> choicesArg) {
        super(frontString, backString);
        choices = choicesArg;
        answerIndex = Integer.parseInt(back);
    }

    @Override
    public JsonValue toJson() {
        try {
            JsonObject obj = super.toJson().getObject();

            JsonArray choicesJson = new JsonArray();
            for (String option : choices) {
                choicesJson.add(option);
            }
            obj.put(Schema.TYPE_FIELD, Schema.MULTIPLE_CHOICE_TYPE);
            obj.put(Schema.CHOICES_FIELD, choicesJson);
            return new JsonValue(obj);
        } catch (JsonWrongValueException e) {
            System.out.println("Inherited FrontBackCard unexpected json object");
        }
        return super.toJson();
    }

    @Override
    public Node renderFront() {
        //TODO generate a random mapping of choices and update answerIndex
        return super.renderFront();
    }

    @Override
    public Boolean evaluate(String in) {
        return Integer.parseInt(in) == answerIndex;
    }
}
