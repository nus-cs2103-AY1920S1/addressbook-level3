package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

class DateTimeTest {

    /* Builder */
    @Test
    void builder_getEpoch_equal() {
        int day = 1;
        int month = 11;
        int year = 2019;
        int hour = 11;
        int minute = 11;

        // Without timezone
        DateTime dateTime1 = DateTime.newBuilder(day, month, year, hour, minute, ZoneOffset.UTC).build();
        assertEquals(1572606660, dateTime1.toEpochSecond());

        // With timezone
        int timezone = 2;
        DateTime dateTime2 = DateTime.newBuilder(day, month, year, hour, minute, ZoneOffset.ofHours(timezone)).build();
        assertEquals(1572599460, dateTime2.toEpochSecond());
    }

    @Test
    void builderFromInstant_getEpoch_equal() {
        Instant instant = Instant.now();
        DateTime dateTime = DateTime.newBuilder(instant).build();
        assertEquals(instant.getEpochSecond(), dateTime.toEpochSecond());
    }

    /* DateTime */
    @Test
    void equals_instantNowEpoch_success() {
        long epoch1 = DateTime.now().toEpochSecond();
        long epoch2 = Instant.now().getEpochSecond();
        assertEquals(epoch1, epoch2);
    }

    @Test
    void equals_sameDateTime_success() {
        DateTime dateTime1 = DateTime.newBuilder(11, 11, 1111, 11, 11, ZoneOffset.UTC).build();
        DateTime dateTime2 = DateTime.newBuilder(11, 11, 1111, 11, 11, ZoneOffset.UTC).build();
        assertEquals(dateTime1, dateTime2);
    }

    @Test
    void equals_differentTimezoneSameEpoch_success() {
        DateTime dateTime1 = DateTime.newBuilder(11, 11, 1111, 11, 11, ZoneOffset.UTC).build();
        DateTime dateTime2 = DateTime.newBuilder(11, 11, 1111, 13, 11, ZoneOffset.ofHours(2)).build();
        assertEquals(dateTime1, dateTime2);
    }

    @Test
    void equals_differentObject_failure() {
        Instant instant = Instant.now();
        DateTime dateTime = DateTime.newBuilder(instant).build();
        assertNotEquals(dateTime, instant);
    }
}
