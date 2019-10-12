package dream.fcard.util.json.jsontypes;

import dream.fcard.util.json.JsonParser;
import dream.fcard.util.json.exceptions.JsonWrongValueException;

/**
 * Algebraic data type of JsonValueTypes.
 */
public class JsonValue {

    private JsonValueTypes type;
    private int valueInt;
    private double valueDouble;
    private boolean valueBoolean;
    private String valueString;
    private JsonObject valueObject;
    private JsonArray valueArray;

    /**
     * Construct the value as an int.
     * @param value int value
     */
    public JsonValue(int value) {
        type = JsonValueTypes.INT;
        valueInt = value;
    }

    /**
     * Construct the value as a double.
     * @param value double value
     */
    public JsonValue(double value) {
        type = JsonValueTypes.DOUBLE;
        valueDouble = value;
    }

    /**
     * Construct the value as a boolean.
     * @param value boolean value
     */
    public JsonValue(boolean value) {
        type = JsonValueTypes.BOOLEAN;
        valueBoolean = value;
    }

    /**
     * Construct the value as a string.
     * @param value string value
     */
    public JsonValue(String value) {
        type = JsonValueTypes.STRING;
        valueString = value;
    }

    /**
     * Construct the value as a JsonObject.
     * @param value JsonObject value
     */
    public JsonValue(JsonObject value) {
        type = JsonValueTypes.OBJECT;
        valueObject = value;
    }

    /**
     * Construct the value as a JsonArray.
     * @param value JsonArray value
     */
    public JsonValue(JsonArray value) {
        type = JsonValueTypes.ARRAY;
        valueArray = value;
    }

    /**
     * Get the type of this value.
     * @return  type
     */
    public JsonValueTypes getType() {
        return type;
    }

    /**
     * Get the value if it is an int.
     * @return  value
     * @throws JsonWrongValueException  exception if value type is not a match
     */
    public int getInt() throws JsonWrongValueException {
        if (type != JsonValueTypes.INT) {
            throw new JsonWrongValueException(JsonValueTypes.INT, type);
        }
        return valueInt;
    }

    /**
     * Get the value if it is an double.
     * @return  value
     * @throws JsonWrongValueException  exception if value type is not a match
     */
    public double getDouble() throws JsonWrongValueException {
        if (type != JsonValueTypes.DOUBLE) {
            throw new JsonWrongValueException(JsonValueTypes.DOUBLE, type);
        }
        return valueDouble;
    }

    /**
     * Get the value if it is an boolean.
     * @return  value
     * @throws JsonWrongValueException  exception if value type is not a match
     */
    public boolean getBoolean() throws JsonWrongValueException {
        if (type != JsonValueTypes.BOOLEAN) {
            throw new JsonWrongValueException(JsonValueTypes.BOOLEAN, type);
        }
        return valueBoolean;
    }

    /**
     * Get the value if it is an string.
     * @return  value
     * @throws JsonWrongValueException  exception if value type is not a match
     */
    public String getString() throws JsonWrongValueException {
        if (type != JsonValueTypes.STRING) {
            throw new JsonWrongValueException(JsonValueTypes.STRING, type);
        }
        return valueString;
    }

    /**
     * Get the value if it is an JsonObject.
     * @return  value
     * @throws JsonWrongValueException  exception if value type is not a match
     */
    public JsonObject getObject() throws JsonWrongValueException {
        if (type != JsonValueTypes.OBJECT) {
            throw new JsonWrongValueException(JsonValueTypes.OBJECT, type);
        }
        return valueObject;
    }

    /**
     * Get the value if it is an JsonArray.
     * @return  value
     * @throws JsonWrongValueException  exception if value type is not a match
     */
    public JsonArray getArray() throws JsonWrongValueException {
        if (type != JsonValueTypes.ARRAY) {
            throw new JsonWrongValueException(JsonValueTypes.ARRAY, type);
        }
        return valueArray;
    }

    /**
     * Get string representation of object.
     * @return  string representation
     */
    public String toString() {
        try {
            switch (type) {
            case INT:
                return Integer.toString(getInt());
            case DOUBLE:
                return Double.toString(getDouble());
            case BOOLEAN:
                return Boolean.toString(getBoolean());
            case STRING:
                return JsonParser.formatStringForJson(getString());
            case OBJECT:
                return getObject().toString();
            case ARRAY:
                return getArray().toString();
            default:
                System.out.println("ERROR: unexpected json value type " + type);
                System.exit(-1);
            }
        } catch (JsonWrongValueException e) {
            System.out.println("ERROR: json value type does not match value its holding");
            e.printStackTrace();
            System.exit(-1);
        }
        return "";
    }
}
