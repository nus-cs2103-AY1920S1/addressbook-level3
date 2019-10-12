package dream.fcard.model;

import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonObject;
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
    private String name;

    public Deck (String deckName) {
        name = deckName;
        cards = new ArrayList<>();
    }

    public Deck(String deckName, ArrayList<FlashCard> initialCards) {
        name = deckName;
        cards = initialCards;
    }

    @Override
    public JsonValue toJson() {
        JsonArray cardJson = new JsonArray();
        for (FlashCard card : cards) {
            cardJson.add(card.toJson());
        }

        JsonObject obj = new JsonObject();
        obj.put("name", name);
        obj.put("cards", cardJson);
        return new JsonValue(obj);
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

    public String getName() {
        return name;
    }
}
