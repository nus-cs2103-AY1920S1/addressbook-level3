package seedu.ezwatchlist.api.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NoRecommendationsExceptionTest {

    @Test
    void getMessage() {
        assertEquals(new NoRecommendationsException("Message").getMessage(), "Message");
    }
}
