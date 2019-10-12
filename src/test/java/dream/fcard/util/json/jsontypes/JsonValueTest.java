package dream.fcard.util.json.jsontypes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import dream.fcard.util.json.exceptions.JsonWrongValueException;

public class JsonValueTest {
    @Test
    void validAccess() {
        try {
            assertEquals(123, new JsonValue(123).getInt());
            assertEquals(3.14, new JsonValue(3.14).getDouble());
            assertEquals(true, new JsonValue(true).getBoolean());
            assertEquals("abc", new JsonValue("abc").getString());
            JsonObject obj = new JsonObject();
            assertEquals(obj, new JsonValue(obj).getObject());
            JsonArray arr = new JsonArray();
            assertEquals(arr, new JsonValue(arr).getArray());
        } catch (JsonWrongValueException e) {
            fail();
        }
    }

    @Test
    void invalidAccess() {
        JsonWrongValueException e = null;
        JsonValue v = null;
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();
        for (JsonValueTypes t1 : JsonValueTypes.values()) {
            switch (t1) {
            case INT:
                v = new JsonValue(123);
                break;
            case DOUBLE:
                v = new JsonValue(3.14);
                break;
            case BOOLEAN:
                v = new JsonValue(true);
                break;
            case STRING:
                v = new JsonValue("abc");
                break;
            case OBJECT:
                v = new JsonValue(obj);
                break;
            case ARRAY:
                v = new JsonValue(arr);
                break;
            default:
                fail();
            }
            for (JsonValueTypes t2 : JsonValueTypes.values()) {
                if (t1.equals(t2)) {
                    continue;
                }
                switch (t2) {
                case INT:
                    e = assertThrows(JsonWrongValueException.class, v::getInt);
                    break;
                case DOUBLE:
                    e = assertThrows(JsonWrongValueException.class, v::getDouble);
                    break;
                case BOOLEAN:
                    e = assertThrows(JsonWrongValueException.class, v::getBoolean);
                    break;
                case STRING:
                    e = assertThrows(JsonWrongValueException.class, v::getString);
                    break;
                case OBJECT:
                    e = assertThrows(JsonWrongValueException.class, v::getObject);
                    break;
                case ARRAY:
                    e = assertThrows(JsonWrongValueException.class, v::getArray);
                    break;
                default:
                    fail();
                }
                assertEquals("Expecting " + t2 + ", however got " + t1, e.getMessage());
            }
        }
    }
}
