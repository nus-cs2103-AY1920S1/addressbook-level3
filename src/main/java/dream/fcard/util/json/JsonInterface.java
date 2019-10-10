package dream.fcard.util.json;

import dream.fcard.util.json.jsontypes.JsonValue;

/**
 * All objects that can be represented by JSON should implement this interface.
 */
public interface JsonInterface {

    /**
     * Returns JSONValue representation of this object.
     *
     * @return JSONValue
     */
    JsonValue toJson();
}
