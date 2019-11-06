//@@author nattanyz
package dream.fcard.logic.stats;

/**
 * Represents the user's statistics pertaining to a specific Deck.
 * The sessionList contained in this object represents the test sessions involving this deck the
 * user has engaged in.
 */
public class DeckStats extends Stats {
    // todo: should implement JsonInterface. need help with toJson().

    private String deckName;
    private int numCards;

    public DeckStats() {
        super();
        System.out.println("New DeckStats object created.");
        this.sessionList = new SessionList();
        System.out.println("New SessionList for deck test sessions created.");
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

    //public Double getAverageScore() {
    //    // todo: cannot get score if Session is not DeckSession
    //    Double score = 0.0;
    //    ArrayList<Session> sessions = this.getSessionsAsArrayList();
    //    for (Session session : sessions) {
    //        score += session.getScore();
    //    }
    //}
    // todo: need to get score as Double
}
