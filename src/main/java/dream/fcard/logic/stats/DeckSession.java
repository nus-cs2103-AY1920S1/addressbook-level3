//@@author nattanyz
package dream.fcard.logic.stats;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.stats.DateTimeUtil;

/** A DeckSession represents a review session involving a particular deck. */
public class DeckSession extends Session {
    /** The Result of this particular session. */
    private String result; // call getScore() to get score

    /** Gets the Result of this particular session. */
    public String getResult() {
        return this.result;
    }

    /** Sets the Result of this particular session. */
    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public void endSession() {
        super.endSession();
    }

    /**
     * Ends the session, and sets the result to the given value.
     * @param result Result of the session.
     */
    public void endSession(String result) {
        this.endSession();
        this.setResult(result);
    }

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        try {
            obj.put(Schema.SESSION_START,
                DateTimeUtil.getJsonFromDateTime(sessionStart).getObject());
            obj.put(Schema.SESSION_END,
                DateTimeUtil.getJsonFromDateTime(sessionEnd).getObject());
            obj.put(Schema.SESSION_RESULT, result);
        } catch (JsonWrongValueException e) {
            System.out.println("DATETIME JSON MUST BE AN OBJECT\n" + e.getMessage());
        }
        return new JsonValue(obj);
    }
}
