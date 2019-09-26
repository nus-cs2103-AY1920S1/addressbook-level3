package seedu.address.model.person.schedule;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeslotTest {

    LocalDateTime time1 = LocalDateTime.parse("2007-12-03T10:15:30");
    LocalDateTime time2 = LocalDateTime.parse("2007-12-03T10:16:30");
    Venue venue = new Venue("venue");
    Timeslot timeslot = new Timeslot(time1, time2, venue);


    @Test
    void testEquals() {
        assertTrue(timeslot.equals(timeslot));
    }

    @Test
    void getStartTime() {
        LocalDateTime time = timeslot.getStartTime();
        assertTrue(time.compareTo(time1) == 0);
    }

    @Test
    void getEndTime() {
        LocalDateTime time = timeslot.getEndTime();
        assertTrue(time.compareTo(time2) == 0);
    }

    @Test
    void getVenue() {
        assertTrue(venue.equals(timeslot.getVenue()));
    }
}