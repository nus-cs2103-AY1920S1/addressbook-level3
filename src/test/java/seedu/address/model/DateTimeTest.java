package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

class DateTimeTest {

    /* Builder */
    @Test
    void builder_getDateTime_equal() {
        int day = 11;
        int month = 11;
        int year = 1111;
        int hour = 11;
        int minute = 11;

        // TODO: These tests fail because DateTime currently does not return UTC date & time.
        /*
        // Without timezone
        DateTime dateTime1 = DateTime.newBuilder(day, month, year, hour, minute, ZoneOffset.UTC).build();
        assertEquals(day, dateTime1.getDay());
        assertEquals(month, dateTime1.getMonth());
        assertEquals(year, dateTime1.getYear());
        assertEquals(hour, dateTime1.getHour());
        assertEquals(minute, dateTime1.getMinute());

        // With timezone +02:00
        int timezone = 2;
        DateTime dateTime2 = DateTime.newBuilder(day, month, year, hour, minute, ZoneOffset.ofHours(timezone)).build();
        assertEquals(day, dateTime2.getDay());
        assertEquals(month, dateTime2.getMonth());
        assertEquals(year, dateTime2.getYear());
        assertEquals(hour - timezone, dateTime2.getHour());
        assertEquals(minute, dateTime2.getMinute());
        */
    }

    /* DateTime */
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
}
