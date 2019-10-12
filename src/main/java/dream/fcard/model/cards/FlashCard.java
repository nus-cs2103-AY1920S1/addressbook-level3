package dream.fcard.model.cards;

import dream.fcard.util.json.JsonInterface;
import javafx.scene.Node;

/**
 * Interface all flash card types must implement.
 */
public interface FlashCard extends JsonInterface {

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
