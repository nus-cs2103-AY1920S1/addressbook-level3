package dream.fcard.util.json.jsontypes;

import java.util.HashMap;

import dream.fcard.util.Printer;
import dream.fcard.util.json.JsonParser;

/**
 * A representation of a json object.
 */
public class JsonObject extends HashMap<String, JsonValue> {
    /**
     * Puts an int into the object.
     * @param key   key for the value
     * @param value int value
     * @return      result of the put function from HashMap
     */
    public JsonValue put(String key, int value) {
        return super.put(key, new JsonValue(value));
    }

    /**
     * Puts an double into the object.
     * @param key   key for the value
     * @param value double value
     * @return      result of the put function from HashMap
     */
    public JsonObject put(String key, double value) {
        super.put(key, new JsonValue(value));
        return this;
    }

    /**
     * Puts an boolean into the object.
     * @param key   key for the value
     * @param value boolean value
     * @return      result of the put function from HashMap
     */
    public JsonObject put(String key, boolean value) {
        super.put(key, new JsonValue(value));
        return this;
    }

    /**
     * Puts an string into the object.
     * @param key   key for the value
     * @param value string value
     * @return      result of the put function from HashMap
     */
    public JsonObject put(String key, String value) {
        super.put(key, new JsonValue(value));
        return this;
    }

    /**
     * Puts an JsonObject into the object.
     * @param key   key for the value
     * @param value JsonObject value
     * @return      result of the put function from HashMap
     */
    public JsonObject put(String key, JsonObject value) {
        super.put(key, new JsonValue(value));
        return this;
    }

    /**
     * Puts an JsonArray into the object.
     * @param key   key for the value
     * @param value JsonArray value
     * @return      result of the put function from HashMap
     */
    public JsonObject put(String key, JsonArray value) {
        super.put(key, new JsonValue(value));
        return this;
    }

    /**
     * Returns string representation of JsonObject.
     * @return  string representation
     */
    public String toString() {
        StringBuilder formattedString = new StringBuilder("");
        boolean empty = true;
        for (HashMap.Entry<String, JsonValue> entry : entrySet()) {
            if (!empty) {
                formattedString.append(",\n");
            } else {
                empty = false;
            }
            String key = JsonParser.formatStringForJson(entry.getKey());
            String value = entry.getValue().toString();
            formattedString.append(key).append(": ").append(value);
        }
        return "{\n" + Printer.indentString(formattedString.toString()) + "}";
    }
}
