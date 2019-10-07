package dream.fcard.model;

import dream.fcard.model.cards.FlashCard;
import dream.fcard.util.json.JSONInterface;
import dream.fcard.util.json.jsontypes.JSONValue;
import java.util.ArrayList;
import javafx.scene.Node;

public class Deck implements JSONInterface {

    private ArrayList<FlashCard> cards;

    public Deck () {
        cards = new ArrayList<>();
    }

    public Deck(ArrayList<FlashCard> initialCards) {
        cards = initialCards;
    }

    @Override
    public JSONValue toJSON() {
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
}
