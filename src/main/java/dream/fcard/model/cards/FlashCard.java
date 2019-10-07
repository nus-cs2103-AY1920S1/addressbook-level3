package dream.fcard.model.cards;

import dream.fcard.util.json.jsontypes.JSONValue;
import javafx.scene.Node;

public interface FlashCard {

    /**
     * Returns a JSONObject representation of this flashcard.
     * @return  JSONObject
     */
    JSONValue toJSON();

    /**
     * Returns render of front of this flash card.
     * @return  JavaFX Node
     */
    Node renderFront();

    /**
     * Returns render of back of this flash card
     * @return  JavaFX Node
     */
    Node renderBack();

    /**
     * Evaluate if the input matches the card
     * @param in    input
     * @return      true if its a valid match
     */
    Boolean evaluate(String in);
}
