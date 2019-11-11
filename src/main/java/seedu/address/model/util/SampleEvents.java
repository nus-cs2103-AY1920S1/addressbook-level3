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

    public static final Event EVENT1_1 = new Event(
            "Event1",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 0)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 30)),
                            new Venue("Orchard")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(11, 0)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(11, 0)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(11, 0)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(11, 0)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(11, 0)),
                            new Venue("I3")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(11, 0)),
                            new Venue("I3"))

            ))
    );

    public static final Event EVENT1_2 = new Event(
            "Event2",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(14, 0)),
                            new Venue("BIZ2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(14, 0)),
                            new Venue("BIZ2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(14, 0)),
                            new Venue("BIZ2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(14, 0)),
                            new Venue("BIZ2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(14, 0)),
                            new Venue("BIZ2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(14, 0)),
                            new Venue("BIZ2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(14, 0)),
                            new Venue("BIZ2"))

            ))
    );

    public static final Event EVENT2_1 = new Event(
            "Work on project",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(11, 30)),
                            new Venue("BIZ1")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 30)),
                            new Venue("Orchard")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(12, 30)),
                            new Venue("BIZ2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(13, 30)),
                            new Venue("S16")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(12, 15)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(12, 45)),
                            new Venue("SDE1")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(12, 30)),
                            new Venue("COM1"))
            ))
    );

    public static final Event EVENT2_2 = new Event(
            "MA1521 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(15, 30)),
                            new Venue("E1")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(16, 30)),
                            new Venue("AS6")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(17, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(18, 0)),
                            new Venue("USP")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(15, 30)),
                            new Venue("UTSRC-SR2"))
            ))
    );

    public static final Event EVENT3_1 = new Event(
            "Swimming class",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(13, 30)),
                            new Venue("S1A")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 30)),
                            new Venue("Orchard")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(13, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(13, 30)),
                            new Venue("S2")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(11, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(12, 30)),
                            new Venue("S5")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(10, 15)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(12, 45)),
                            new Venue("S12")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(12, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(13, 0)),
                            new Venue("S16"))
            ))
    );

    public static final Event EVENT3_2 = new Event(
            "LSM1301 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(15, 30)),
                            new Venue("TP-SR1")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(4), LocalTime.of(10, 30)),
                            new Venue("UTSRC-LT51")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(16, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(17, 0)),
                            new Venue("UT-AUD1")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(13, 30)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(14, 30)),
                            new Venue("UT-AUD2"))
            ))
    );

    public static final Event EVENT4_1 = new Event(
            "Hall activities",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(11, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 30)),
                            new Venue("LT19")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 30)),
                            new Venue("Orchard")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(10, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(11, 30)),
                            new Venue("LT20")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(11, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(11, 30)),
                            new Venue("LT34")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(11, 15)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(12, 30)),
                            new Venue("LT21")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(11, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(6), LocalTime.of(11, 30)),
                            new Venue("LT19"))
            ))
    );


    public static final Event EVENT4_2 = new Event(
            "GEQ1000 lecture",
            new ArrayList<>(List.of(
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(15, 30)),
                            new Venue("MD1")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(3), LocalTime.of(9, 30)),
                            new Venue("PGPH-FR4")),
                    new Timeslot(
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(14, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(5), LocalTime.of(15, 0)),
                            new Venue("SDE-ER4")),
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
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 0)),
                            LocalDateTime.of(LocalDate.now().plusDays(0), LocalTime.of(8, 30)),
                            new Venue("Orchard")),
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
