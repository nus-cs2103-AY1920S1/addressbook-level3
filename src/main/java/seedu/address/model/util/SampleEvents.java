package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

/**
 * Sample Events.
 */
public class SampleEvents {

    public static final Event LUNCH1 = new Event(
            "Lunch 1",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 30)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(12, 30)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 30)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13, 0)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(11, 45)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 15)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 0)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(13, 30)),
                            new Venue("Canteen"))
            ))
    );

    public static final Event LUNCH2 = new Event(
            "Lunch 2",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 30)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 30)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 30)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(12, 15)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(12, 45)),
                            new Venue("Canteen")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(12, 30)),
                            new Venue("Canteen"))
            ))
    );

    public static final Event LECTURE1 = new Event(
            "CS2103 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(15, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(16, 30)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(9, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(10, 30)),
                            new Venue("LT17")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(16, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(18, 30)),
                            new Venue("I3"))
            ))
    );

    public static final Event LECTURE2 = new Event(
            "MA1521 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(15, 30)),
                            new Venue("S17")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(16, 30)),
                            new Venue("S17")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(17, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(18, 0)),
                            new Venue("AS6")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(15, 30)),
                            new Venue("S17"))
            ))
    );

    public static final Event LECTURE3 = new Event(
            "LSM1301 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 30)),
                            new Venue("S42")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(10, 30)),
                            new Venue("S42")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(16, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(17, 0)),
                            new Venue("S42")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(14, 30)),
                            new Venue("S42"))
            ))
    );

    public static final Event LECTURE4 = new Event(
            "GEQ1000 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 30)),
                            new Venue("UTown")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(9, 30)),
                            new Venue("UTown")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(15, 0)),
                            new Venue("UTown")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(17, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(18, 30)),
                            new Venue("UTown"))
            ))
    );

    public static final Event LECTURE5 = new Event(
            "CS2101 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 30)),
                            new Venue("UTown")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(16, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(17, 30)),
                            new Venue("UTown")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(10, 0)),
                            new Venue("UTown")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(9, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(10, 30)),
                            new Venue("UTown"))
            ))
    );

}
