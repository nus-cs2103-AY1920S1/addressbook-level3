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
                            new Venue("Canteen11")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 30)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13, 0)),
                            new Venue("Canteen12")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(11, 45)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 15)),
                            new Venue("Canteen13")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 0)),
                            new Venue("Canteen14")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(13, 30)),
                            new Venue("Canteen15"))
            ))
    );

    public static final Event LUNCH2 = new Event(
            "Lunch 2",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 30)),
                            new Venue("Canteen21")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 30)),
                            new Venue("Canteen22")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 30)),
                            new Venue("Canteen23")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(12, 15)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(12, 45)),
                            new Venue("Canteen24")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(12, 30)),
                            new Venue("COM1"))
            ))
    );

    public static final Event LECTURE1 = new Event(
            "CS2103 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(15, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(16, 30)),
                            new Venue("L11")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(9, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(10, 30)),
                            new Venue("L12")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(16, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(18, 30)),
                            new Venue("L13"))
            ))
    );

    public static final Event LECTURE2 = new Event(
            "MA1521 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(15, 30)),
                            new Venue("L21")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(16, 30)),
                            new Venue("L22")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(17, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(18, 0)),
                            new Venue("L23")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(15, 30)),
                            new Venue("L24"))
            ))
    );

    public static final Event LECTURE3 = new Event(
            "LSM1301 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 30)),
                            new Venue("L31")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(10, 30)),
                            new Venue("L32")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(16, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(17, 0)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(14, 30)),
                            new Venue("L34"))
            ))
    );

    public static final Event LECTURE4 = new Event(
            "GEQ1000 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 30)),
                            new Venue("L41")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(9, 30)),
                            new Venue("L42")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(15, 0)),
                            new Venue("L43")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(17, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(18, 30)),
                            new Venue("LT17"))
            ))
    );

    public static final Event LECTURE5 = new Event(
            "CS2101 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 30)),
                            new Venue("L51")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(16, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(17, 30)),
                            new Venue("L52")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(10, 0)),
                            new Venue("L53")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(9, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(10, 30)),
                            new Venue("L54"))
            ))
    );

}
