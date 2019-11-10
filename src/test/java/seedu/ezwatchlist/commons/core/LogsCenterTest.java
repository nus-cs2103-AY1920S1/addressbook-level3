package seedu.ezwatchlist.commons.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Level;

import org.junit.jupiter.api.Test;

class LogsCenterTest {

    private Config config = new Config();
    private LogsCenter logsCenter = new LogsCenter();

    @Test
    void init() {
        logsCenter.init(config);
        assertTrue(logsCenter.getCurrentLogLevel() instanceof Level);
    }

    @Test
    void getLogger() {
    }

    @Test
    void testGetLogger() {
    }
}
