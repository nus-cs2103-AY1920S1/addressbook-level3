package seedu.savenus.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class TimeFormatterTest {

    @Test
    public void getDaysAgoTest() {
        LocalDateTime testLocalDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(Long.parseLong("1570680000000")), ZoneId.systemDefault()); // 2019/10/10 12:00:00

        LocalDateTime testLocalDateTime2DaysAgo = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(Long.parseLong("1570507200000")), ZoneId.systemDefault()); // 2019/10/08 12:00:00

        assertTrue(TimeFormatter.getDaysAgo(testLocalDateTime2DaysAgo, testLocalDateTime) == 2);
    }
}
