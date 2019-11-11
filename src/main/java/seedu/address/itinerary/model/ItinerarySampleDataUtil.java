package seedu.address.itinerary.model;

import seedu.address.itinerary.model.event.Date;
import seedu.address.itinerary.model.event.Description;
import seedu.address.itinerary.model.event.Event;
import seedu.address.itinerary.model.event.Location;
import seedu.address.itinerary.model.event.Tag;
import seedu.address.itinerary.model.event.Time;
import seedu.address.itinerary.model.event.Title;

/**
 * If the itinerary file is empty, start off with the sample data.
 */
public class ItinerarySampleDataUtil {
    public static Event[] getSampleEvents() {

        Event eventTest = new Event(new Title("Return library books"), new Date("01112019"),
                new Location("NUS Central Library"), new Description("Due date of books is on 3 November"),
                new Time("1400"), new Tag("Priority: Medium"));

        Event eventTest2 = new Event(new Title("Attend GER1000 meeeting"), new Date("31082019"),
                new Location("COM1 Discussion Room 1"), new Description("-"),
                new Time("1000"), new Tag("Priority: Medium"));

        eventTest.markIsDone();
        eventTest2.markIsDone();

        return new Event[]{
            new Event(new Title("Do CS2103T features"), new Date("05112019"), new Location("PGP"),
                        new Description("In my room, turning coffee into code"), new Time("2030"),
                        new Tag("Priority: High")),

            eventTest,

            new Event(new Title("Watch Avenger: End Game"), new Date("30082019"), new Location("SOC"),
                        new Description("SOC Movie night, remember to bring popcorn"), new Time("2130"),
                        new Tag("Priority: None")),

            new Event(new Title("Prepare CS2103T demo"), new Date("07112019"), new Location("COM1 #02-16"),
                        new Description("Each person to do 3-4 minutes of feature demo"), new Time("1700"),
                        new Tag("Priority: Critical")),

            new Event(new Title("Celebrate Ben's Birthday"), new Date("03111000"), new Location("Bedok Reservoir"),
                        new Description("-"), new Time("0000"),
                        new Tag("Priority: High")),

            new Event(new Title("Visit All Blue"), new Date("31123999"), new Location("-"),
                        new Description("-"), new Time("0800"),
                        new Tag("Priority: Low")),

            new Event(new Title("Navigate around Uganda"), new Date("10101010"), new Location("Uganda"),
                        new Description("Do you know da wae?"), new Time("0000"),
                        new Tag("Priority: Medium")),

            new Event(new Title("Visiting Relatives"), new Date("28022019"), new Location("Malaysia"),
                        new Description("Meeting my long lost brother"), new Time("0123"),
                        new Tag("Priority: High")),

            new Event(new Title("Attend CS2103T PE"), new Date("15112019"), new Location("iCube Auditorium"),
                        new Description("Time to break some bad code and do some math"), new Time("1600"),
                        new Tag("Priority: Critical")),

            new Event(new Title("Watch GoT season 7"), new Date("13071997"), new Location("-"),
                        new Description("Netflix and we cool"), new Time("1900"),
                        new Tag("Priority: None")),

            new Event(new Title("Do Naruto Run"), new Date("01043000"), new Location("Area 51"),
                        new Description("-"), new Time("1500"),
                        new Tag("Priority: Critical")),

            new Event(new Title("Attend Soccer Training"), new Date("15042011"), new Location("Stadium"),
                        new Description("Friendly match with Lakers"), new Time("1200"),
                        new Tag("Priority: Medium")),

            new Event(new Title("Buy a sponge"), new Date("15092999"), new Location("Bikini Bottom"),
                        new Description("Get some squid and crab too"), new Time("0913"),
                        new Tag("Priority: None")),

            new Event(new Title("Do ST2334 Tutorial 10"), new Date("01112019"), new Location("-"),
                        new Description("-"), new Time("1516"),
                        new Tag("Priority: Low")),

            new Event(new Title("Attend club meeting"), new Date("11111111"), new Location("Nihongo"),
                        new Description("Otaku for life!"), new Time("0420"),
                        new Tag("Priority: Low")),

            new Event(new Title("Shooting for the moon"), new Date("06062190"), new Location("Outer Space"),
                        new Description("It's just a smudge on the lens"), new Time("2137"),
                        new Tag("Priority: None")),

            new Event(new Title("Greet Santa Claus"), new Date("25122019"), new Location("-"),
                        new Description("Prepare cookies and milk"), new Time("0000"),
                        new Tag("Priority: High")),

            new Event(new Title("Buy groceries"), new Date("09091999"), new Location("GIANT"),
                        new Description("-"), new Time("1110"),
                        new Tag("Priority: Critical")),

            new Event(new Title("Get a hot grill"), new Date("20102969"), new Location("-"),
                        new Description("-"), new Time("1418"),
                        new Tag("Priority: Medium")),

            eventTest2,
        };
    }

    public static ReadOnlyItinerary getSampleItinerary() {
        Itinerary sampleItinerary = new Itinerary();
        for (Event sampleEvent : getSampleEvents()) {
            sampleItinerary.addEvent(sampleEvent);
        }
        return sampleItinerary;
    }

    public static void doNothing(ReadOnlyItinerary readOnlyItinerary) {
    }

}
