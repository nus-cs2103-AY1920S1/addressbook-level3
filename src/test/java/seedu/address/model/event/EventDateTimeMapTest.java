package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEventDates.OCT_19_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_20_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_21_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_22_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_23_2019;
import static seedu.address.testutil.TypicalEventDayTimes.DEFAULT_DAY_TIME;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_0800_TO_1230;
import static seedu.address.testutil.TypicalEventDayTimes.TIME_1200_TO_1800;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

class EventDateTimeMapTest {

    @Test
    void totalHoursTest() {
        EventDateTimeMap map = new EventDateTimeMap(defaultMap());
        assertEquals(map.totalHours(), 30.5);

        map.deleteDateKey(OCT_23_2019); //Try delete, 4.5hrs removed
        assertEquals(map.totalHours(), 26);

        map.mapDateTime(OCT_23_2019, DEFAULT_DAY_TIME); //Try put, 10hrs added
        assertEquals(map.totalHours(), 36);
    }

    @Test
    void insertDeleteTest() {
        EventDateTimeMap map = new EventDateTimeMap(defaultMap());
        assertTrue(map.containsDateKey(OCT_23_2019));
        assertFalse(map.containsDateKey(OCT_22_2019));

        map.deleteDateKey(OCT_23_2019);
        assertFalse(map.containsDateKey(OCT_23_2019));
    }

    @Test
    void flushEventDatesTest() {
        EventDateTimeMap map;

        // last key is flushed
        map = new EventDateTimeMap(defaultMap());
        map.flushEventDates(OCT_19_2019, OCT_21_2019);
        assertFalse(map.containsDateKey(OCT_23_2019));
        assertTrue(map.containsDateKey(OCT_21_2019));
        assertEquals(map.getDateTimeMap().get(OCT_21_2019), TIME_1200_TO_1800);

        //default EventDate-EventDateTime inserted for new range
        map = new EventDateTimeMap(defaultMap());
        map.flushEventDates(OCT_19_2019, OCT_22_2019);
        assertFalse(map.containsDateKey(OCT_23_2019));
        assertTrue(map.containsDateKey(OCT_22_2019));
        assertEquals(map.getDateTimeMap().get(OCT_22_2019), DEFAULT_DAY_TIME);
    }

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

    /**
     * Generates a default TreeMap for easy access and usage.
     */
    Map<EventDate, EventDayTime> defaultMap() {
        Map<EventDate, EventDayTime> map = new TreeMap<>();
        map.put(OCT_19_2019, DEFAULT_DAY_TIME);
        map.put(OCT_20_2019, DEFAULT_DAY_TIME);
        map.put(OCT_21_2019, TIME_1200_TO_1800);
        map.put(OCT_23_2019, TIME_0800_TO_1230);

        return map;
    }
}
