package seedu.address.websocket.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

class ParserUtilTest {

    @Test
    void parseStringToJsonObject() {
        String happyInput = "{}";
        String sadInput = "";
        assertEquals(ParserUtil.parseStringToJsonObject(happyInput), new JSONObject());
        assertEquals(ParserUtil.parseStringToJsonObject(sadInput), null);
    }

    @Test
    void parseStringToJsonArray() {
        String happyInput = "[]";
        String sadInput = "";
        assertEquals(ParserUtil.parseStringToJsonArray(happyInput), new JSONArray());
        assertEquals(ParserUtil.parseStringToJsonArray(sadInput), null);
    }
}
