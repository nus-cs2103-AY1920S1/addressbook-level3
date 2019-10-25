package seedu.address.websocket.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImageQueryTest {

    @Test
    void execute() {
        assertDoesNotThrow(()->ImageQuery.execute("foo","foo"));
    }
}