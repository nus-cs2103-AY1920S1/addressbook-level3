package dream.fcard.model.cards;

import static dream.fcard.model.cards.Priority.LOW_PRIORITY;

import dream.fcard.gui.Gui;
import dream.fcard.logic.storage.Schema;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import javafx.scene.Node;

/**
 * Card that evaluates input to match back of card.
 */
public class FrontBackCard extends FlashCard {
    /**
     * Constructor to create a FrontBackCard.
     * Takes in 2 String, front text and back text.
     *
     * @param frontString
     * @param backString
     */
    public FrontBackCard(String frontString, String backString) {
        back = backString;
        front = frontString;

        // Default priority is 1
        priority = LOW_PRIORITY;
    }

    public FrontBackCard(String frontString, String backString, int priorityLevel) {
        back = backString;
        front = frontString;

        // Default priority is 1
        priority = priorityLevel;
    }

    /**
     *
     *
     * @return
     */
    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        obj.put(Schema.TYPE_FIELD, Schema.FRONT_BACK_TYPE);
        obj.put(Schema.FRONT_FIELD, front);
        obj.put(Schema.BACK_FIELD, back);
        return new JsonValue(obj);
    }

    /**
     *
     * @param in input
     * @return
     * @throws IndexNotFoundException
     */
    public Boolean evaluate(String in) throws IndexNotFoundException {
        return in.equals(back);
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
