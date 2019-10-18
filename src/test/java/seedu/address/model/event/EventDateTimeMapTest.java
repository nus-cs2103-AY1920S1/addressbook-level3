package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

class EventDateTimeMapTest {

    @Test
    void mapDateTime() {
        EventDateTimeMap map = new EventDateTimeMap();
        EventDate eventDate = new EventDate(LocalDate.of(2019, 10, 20));
        EventDayTime eventDayTime = new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 30));
        map.mapDateTime(eventDate, eventDayTime);
        assertTrue(map.getDateTimeMap().containsKey(eventDate));
        assertTrue(map.getDateTimeMap().containsValue(eventDayTime));
    }

    @Test
    void isSameEventDateTimeMap() {
        EventDateTimeMap map = new EventDateTimeMap();
        EventDateTimeMap map2 = new EventDateTimeMap();
        EventDate date = new EventDate(LocalDate.of(2019, 10, 20));
        EventDayTime time = new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 30));
        EventDayTime time2 = new EventDayTime(LocalTime.of(10, 20), LocalTime.of(10, 31));

        map.mapDateTime(date, time);
        map2.mapDateTime(date, time);
        assertTrue(map.equals(map2)); //same K-V values

        map.mapDateTime(date, time2);
        assertFalse(map.equals(map2)); //different K-V values
    }
}
