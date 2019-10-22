package dream.fcard.model.cards;

import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonValue;
import javafx.scene.Node;

/**
 * Interface all flash card types must implement.
 */
public abstract class FlashCard implements JsonInterface {
    protected String front;
    protected String back;
    protected Integer priority;

    /**
     * Returns render of front of this flash card.
     *
     * @return JavaFX Node
     */
    public abstract Node renderFront();

    /**
     * Returns render of back of this flash card
     *
     * @return JavaFX Node
     */
    public abstract Node renderBack();

    /**
     * Evaluate if the input matches the card
     *
     * @param in input
     * @return true if its a valid match
     */
    public abstract Boolean evaluate(String in) throws IndexNotFoundException;

    /**
     *
     * @return
     */
    public abstract String getFront();

    /**
     *
     * @return
     */
    public abstract String getBack();

    /**
     *
     * @param newText
     */
    public abstract void editFront(String newText);

    /**
     *
     * @param newText
     */
    public abstract void editBack(String newText);

    /**
     *
     * @return
     */
    @Override
    public JsonValue toJson() {
        return null;
    }
}
