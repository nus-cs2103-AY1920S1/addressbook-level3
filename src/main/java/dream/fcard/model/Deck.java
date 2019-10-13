package dream.fcard.model;

import java.util.ArrayList;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonValue;

import javafx.scene.Node;

/**
 * Collection of cards.
 */
public class Deck implements JsonInterface {

    private ArrayList<FlashCard> cards;

    public Deck () {
        cards = new ArrayList<>();
    }

    public Deck(ArrayList<FlashCard> initialCards) {
        cards = initialCards;
    }

    @Override
    public JsonValue toJson() {
        return null;
    }

    /**
     * Render all the cards of the deck in a list.
     * @return  node of list
     */
    public Node renderListView() {
        return null;
    }

    /**
     * Render tile icon of this deck to display alongside other decks.
     * @return  node of tile
     */
    public Node renderTileView() {
        return null;
    }

    public ArrayList<FlashCard> getCards() {
        return this.cards;
    }
}
