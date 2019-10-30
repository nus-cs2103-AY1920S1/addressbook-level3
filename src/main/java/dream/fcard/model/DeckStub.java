package dream.fcard.model;

import dream.fcard.logic.stats.DeckStats;
import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * Temporary class for testing DeckStats-related methods.
 */
public class DeckStub extends Deck {

    /** Name of this Deck object. */
    private String deckName;

    /** DeckStats object storing the statistics of this deck. */
    private DeckStats deckStats;

    /**
     * Constructor to create a Deck object with no name and cards.
     */
    public DeckStub() {
        //cards = new ArrayList<>();
        deckName = "untitled";
        //
        //highPriorityList = new ArrayList<>();
        //lowPriorityList = new ArrayList<>();

        deckStats = new DeckStats();
    }

    /**
     * Constructor to create a Deck object with name and FlashCard objects.
     *
     * @param name String name of the Deck object.
     */
    public DeckStub(String name) {
        //cards = new ArrayList<>();
        deckName = name;

        //highPriorityList = new ArrayList<>();
        //lowPriorityList = new ArrayList<>();

        deckStats = new DeckStats();
    }


    /**
     * Returns Json format of Deck.
     *
     * @return JsonValue Json object of current Deck.
     */
    @Override
    public JsonValue toJson() {
        // code for storing cards in deck
        //JsonArray cardJson = new JsonArray();
        //for (FlashCard card : cards) {
        //    cardJson.add(card.toJson());
        //}
        //
        //JsonObject obj = new JsonObject();
        //obj.put(Schema.DECK_NAME, deckName);
        //obj.put(Schema.DECK_CARDS, cardJson);

        // call toJson on the DeckStats object
        //JsonValue statsJson = deckStats.toJson();
        //JsonObject obj = new JsonObject();
        //obj.put(Schema.DECK_NAME, deckName);
        //
        //return new JsonValue(obj);
        return null;
    }

    /** Get the statistics pertaining to this deck. */
    public DeckStats getDeckStats() {
        return this.deckStats;
    }
}
