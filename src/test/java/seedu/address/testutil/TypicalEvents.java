/*
@@author shihaoyap
 */

package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANPOWER_COUNT_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANPOWER_COUNT_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PARTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MUSICAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_PARTY;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Manually added
    public static final Event MUSICAL_COMPETITION = new EventBuilder().withName("Music Competition")
            .withVenue("123, Jurong West Ave 6, #08-111").withManpowerNeeded("5")
            .withStartDate(LocalDate.parse("12/10/2019", FORMATTER))
            .withEndDate(LocalDate.parse("13/10/2019", FORMATTER))
            .withTags("free").build();

    public static final Event RUNNING = new EventBuilder().withName("NUS Run")
            .withVenue("NUS Utown Green").withManpowerNeeded("20")
            .withStartDate(LocalDate.parse("05/12/2019", FORMATTER))
            .withEndDate(LocalDate.parse("07/12/2019", FORMATTER))
            .withTags("sports").build();

    public static final Event NEW_YEAR = new EventBuilder().withName("New Year Countdown")
            .withVenue("Marina Bay Sands").withManpowerNeeded("20")
            .withStartDate(LocalDate.parse("31/12/2019", FORMATTER))
            .withEndDate(LocalDate.parse("01/01/2020", FORMATTER))
            .withTags("fun").build();

    public static final Event BIRTHDAY_PARTY = new EventBuilder().withName("John 21st Birthday Party")
            .withVenue("Orchard Hotel").withManpowerNeeded("5")
            .withStartDate(LocalDate.parse("10/10/2019", FORMATTER))
            .withEndDate(LocalDate.parse("15/10/2019", FORMATTER))
            .withTags("celebration").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event PARTY = new EventBuilder().withName(VALID_EVENT_NAME_PARTY)
            .withVenue(VALID_VENUE_PARTY).withManpowerNeeded(VALID_MANPOWER_COUNT_PARTY)
            .withStartDate(LocalDate.parse(VALID_DATE_1_PARTY, FORMATTER))
            .withEndDate(LocalDate.parse(VALID_DATE_2_PARTY, FORMATTER))
            .withTags(VALID_TAG_PARTY).build();

    public static final Event MUSICAL = new EventBuilder().withName(VALID_EVENT_NAME_MUSICAL)
            .withVenue(VALID_VENUE_MUSICAL).withManpowerNeeded(VALID_MANPOWER_COUNT_MUSICAL)
            .withStartDate(LocalDate.parse(VALID_DATE_1_MUSICAL, FORMATTER))
            .withEndDate(LocalDate.parse(VALID_DATE_2_MUSICAL, FORMATTER))
            .withTags(VALID_TAG_MUSICAL).build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static EventBook getTypicalEventBook() {
        EventBook eb = new EventBook();
        for (Event event : getTypicalEvents()) {
            eb.addEvent(event);
        }
        return eb;
    }

    /**
     * Returns an empty {@code EventBook} with all the typical events.
     */
    public static EventBook getEmptyEventBook() {
        EventBook eb = new EventBook();
        return eb;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(MUSICAL_COMPETITION, RUNNING, NEW_YEAR, BIRTHDAY_PARTY));
    }
}
