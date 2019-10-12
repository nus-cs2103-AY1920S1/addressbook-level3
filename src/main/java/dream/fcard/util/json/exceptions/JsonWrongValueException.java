package dream.fcard.util.json.exceptions;

import dream.fcard.util.json.jsontypes.JsonValueTypes;

/**
 * Exception when access wrong value of a JsonValue object.
 */
public class JsonWrongValueException extends Exception {
    JsonWrongValueException(JsonValueTypes expected, JsonValueTypes got) {
        super("Expecting " + expected + ", however got " + got);
    }
}
