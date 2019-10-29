package dream.fcard.logic.stats;

import dream.fcard.util.json.JsonInterface;
import dream.fcard.util.json.jsontypes.JsonArray;
import dream.fcard.util.json.jsontypes.JsonValue;
import java.util.ArrayList;

public class DeckStats implements JsonInterface {

    /** List of Sessions involving the Deck this DeckStats object corresponds to. */
    private ArrayList<Session> deckTestSessions;

    @Override
    public JsonValue toJson() {
        JsonArray statsJson = new JsonArray();
        for (Session session : deckTestSessions) {
            statsJson.add(session.toJson());
        }
        // to replace with creating new JsonObject and object.put?
        return new JsonValue(statsJson);
    }
}
