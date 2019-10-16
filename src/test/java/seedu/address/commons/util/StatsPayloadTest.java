package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_REVENUE_1;

import org.junit.jupiter.api.Test;

public class StatsPayloadTest {
    @Test
    void isSameStatsPayload() {
        // same object -> returns true
        assertTrue(DEFAULT_STATS_PAYLOAD_REVENUE_1.equals(DEFAULT_STATS_PAYLOAD_REVENUE_1));
    }

}
