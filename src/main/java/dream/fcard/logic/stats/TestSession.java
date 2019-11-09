//@@author nattanyz
package dream.fcard.logic.stats;

import dream.fcard.logic.storage.Schema;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import dream.fcard.util.json.jsontypes.JsonObject;
import dream.fcard.util.json.jsontypes.JsonValue;
import dream.fcard.util.stats.DateTimeUtil;

/** Represents a review session involving a particular deck. */
public class TestSession extends Session {
    // all methods and fields except toJson() are inherited from Session

    @Override
    public JsonValue toJson() {
        JsonObject obj = new JsonObject();
        try {
            obj.put(Schema.SESSION_START,
                DateTimeUtil.getJsonFromDateTime(sessionStart).getObject());
            obj.put(Schema.SESSION_END,
                DateTimeUtil.getJsonFromDateTime(sessionEnd).getObject());
            obj.put(Schema.SESSION_RESULT, score);
        } catch (JsonWrongValueException e) {
            System.out.println("DATETIME JSON MUST BE AN OBJECT\n" + e.getMessage());
        }
        return new JsonValue(obj);
    }
}
