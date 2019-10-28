package seedu.address.websocket.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;

class ApiQueryTest {
    Logger logger = LogsCenter.getLogger(this.getClass());
    @Test
    void execute() {
        ApiQuery happyApiQuery = new ApiQuery("https://www.google.com.sg");
        QueryResult happyQueryResult = happyApiQuery.execute();
        assertTrue(happyQueryResult.process(logger));
    }
}