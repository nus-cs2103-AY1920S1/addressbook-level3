package dream.fcard.model.cards;

import javafx.scene.Node;

/**
 * Interface all flash card types must implement.
 */
public interface FlashCard {

    /**
     * Returns render of front of this flash card.
     *
     * @return JavaFX Node
     */
    Node renderFront();

    /**
     * Returns render of back of this flash card
     *
     * @return JavaFX Node
     */
    Node renderBack();

    /**
     * Evaluate if the input matches the card
     *
     * @param in input
     * @return true if its a valid match
     */
    Boolean evaluate(String in);

    public void editFront(String newText);

    public void editBack(String newText);
}
