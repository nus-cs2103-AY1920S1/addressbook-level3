//@@author nattanyz
package dream.fcard.logic.stats;

public class DeckStats {
    // todo: should implement JsonInterface. need help with toJson().

    /** List of Sessions involving the Deck this DeckStats object corresponds to. */
    private SessionList deckTestSessions;

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

    public int numberOfTestSessions() {
        return this.deckTestSessions.numberOfSessions();
    }
}
