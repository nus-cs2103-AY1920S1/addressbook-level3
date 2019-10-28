package seedu.address.websocket.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;

class QueryResultTest {
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private QueryResult queryResultHappy;
    private QueryResult queryResultSad;
    @BeforeEach
    void init() {
        queryResultHappy = new QueryResult(200, "foo bar foo");
        queryResultSad = new QueryResult(400, "foo bar foo");
    }
    @Test
    void getResponseResult() {
        QueryResult queryResult = new QueryResult(200, "foo bar foo");
        assertEquals(queryResultHappy.getResponseResult(), "foo bar foo");
    }

    @Test
    void process() {
        assertTrue(queryResultHappy.process(logger));
        assertFalse(queryResultSad.process(logger));
        QueryResult queryResult = new QueryResult(null, "foo bar foo");
        assertFalse(queryResult.process(logger));
    }
}
