package dream.fcard.util.json.jsontypes;

import java.util.ArrayList;

import dream.fcard.util.Printer;

/**
 * A representation of a json array.
 */
public class JsonArray extends ArrayList<JsonValue> {
    /**
     * Adds an int to the array.
     * @param value int value
     * @return      add result of array list
     */
    public boolean add(int value) {
        return super.add(new JsonValue(value));
    }

    /**
     * Adds a double to the array.
     * @param value double value
     * @return      add result of array list
     */
    public boolean add(double value) {
        return super.add(new JsonValue(value));
    }

    /**
     * Adds a boolean to the array.
     * @param value boolean value
     * @return      add result of array list
     */
    public boolean add(boolean value) {
        return super.add(new JsonValue(value));
    }

    /**
     * Adds a string to the array.
     * @param value string value
     * @return      add result of array list
     */
    public boolean add(String value) {
        return super.add(new JsonValue(value));
    }

    /**
     * Adds a JsonObject to the array.
     * @param value JsonObject value
     * @return      add result of array list
     */
    public boolean add(JsonObject value) {
        return super.add(new JsonValue(value));
    }

    /**
     * Adds a JsonArray to the array.
     * @param value JsonArray value
     * @return      add result of array list
     */
    public boolean add(JsonArray value) {
        return super.add(new JsonValue(value));
    }

    /**
     * Adds a int to the array at the specified index.
     * @param index index
     * @param value int value
     */
    public void add(int index, int value) {
        super.add(index, new JsonValue(value));
    }

    /**
     * Adds a double to the array at the specified index.
     * @param index index
     * @param value double value
     */
    public void add(int index, double value) {
        super.add(index, new JsonValue(value));
    }

    /**
     * Adds a boolean to the array at the specified index.
     * @param index index
     * @param value boolean value
     */
    public void add(int index, boolean value) {
        super.add(index, new JsonValue(value));
    }

    /**
     * Adds a string to the array at the specified index.
     * @param index index
     * @param value string value
     */
    public void add(int index, String value) {
        super.add(index, new JsonValue(value));
    }

    /**
     * Adds a JsonObject to the array at the specified index.
     * @param index index
     * @param value JsonObject value
     */
    public void add(int index, JsonObject value) {
        super.add(index, new JsonValue(value));
    }

    /**
     * Adds a JsonArray to the array at the specified index.
     * @param index index
     * @param value JsonArray value
     */
    public void add(int index, JsonArray value) {
        super.add(index, new JsonValue(value));
    }

    /**
     * Returns string representation of JsonArray.
     * @return  string representation
     */
    public String toString() {
        StringBuilder formattedString = new StringBuilder("");
        boolean empty = true;
        for (JsonValue entry : this) {
            if (!empty) {
                formattedString.append(",\n");
            } else {
                empty = false;
            }
            formattedString.append(entry.toString());
        }
        return "[\n" + Printer.indentString(formattedString.toString()) + "]";
    }
}
