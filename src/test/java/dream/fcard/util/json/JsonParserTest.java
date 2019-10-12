package dream.fcard.util.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import dream.fcard.util.json.exceptions.JsonFormatException;
import dream.fcard.util.json.exceptions.JsonWrongValueException;
import org.junit.jupiter.api.Test;

public class JsonParserTest {
    @Test
    void validParse() {
        try {
            try {
                assertEquals(123, JsonParser.parseJsonInput("123").getInt());
                assertEquals(3.14, JsonParser.parseJsonInput("3.14").getDouble());
                assertEquals(true, JsonParser.parseJsonInput("true").getBoolean());
                assertEquals("abc", JsonParser.parseJsonInput("\"abc\"").getString());
                assertEquals(123, JsonParser.parseJsonInput("{\"num\": 123}").getObject().get("num").getInt());
                assertEquals(123, JsonParser.parseJsonInput("[123]").getArray().get(0).getInt());
            } catch (JsonWrongValueException ev) {
                fail();
            }
        } catch (JsonFormatException ef) {
            fail();
        }
    }
}
