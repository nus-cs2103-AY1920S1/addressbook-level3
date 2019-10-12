package dream.fcard.util.json.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JsonFormatExceptionTest {
    @Test
    void errorMessageTest() {
        assertEquals("hi\nabc\n ^\n(1:2)\n", new JsonFormatException("abc".toCharArray(), 1, "hi").getMessage());
    }
}
