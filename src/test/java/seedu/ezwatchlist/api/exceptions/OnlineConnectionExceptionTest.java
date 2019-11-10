package seedu.ezwatchlist.api.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OnlineConnectionExceptionTest {

    @Test
    public void constructor() {
        assertEquals(new OnlineConnectionException("Message").getMessage(), "Message");
    }

}
