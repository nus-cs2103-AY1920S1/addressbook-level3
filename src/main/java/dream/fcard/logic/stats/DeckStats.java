//@@author nattanyz
package dream.fcard.logic.stats;

/**
 * Represents the user's statistics pertaining to a specific Deck.
 */
public class DeckStats {
    // todo: should implement JsonInterface. need help with toJson().

    /** List of Sessions involving the Deck this DeckStats object corresponds to. */
    private SessionList deckTestSessions;

    /**
     * Adds the given session to the deckTestSessions list contained in this DeckStats object.
     * @param session The Session to be added.
     */
    public void addSession(Session session) {
        this.deckTestSessions.addSession(session);
    }

    //@Override
    //public JsonValue toJson() {
    //    JsonArray statsJson = new JsonArray();
    //    for (Session session : deckTestSessions) {
    //        statsJson.add(session.toJson());
    //    }
    //    // to replace with creating new JsonObject and object.put?
    //    return new JsonValue(statsJson);
    //}

    /** Returns the number of test sessions. */
    public int numberOfTestSessions() {
        return this.deckTestSessions.numberOfSessions();
    }
}
