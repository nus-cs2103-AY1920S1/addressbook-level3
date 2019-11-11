/*
@@author shihaoyap
 */

package seedu.address.model.distinctdate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.DistinctDateBuilder;

class DistinctDateTest {
    private final EventName name = new EventName("Orchestra");
    private final EventName otherName = new EventName("Dance");
    private final EventVenue venue = new EventVenue("Esplanade");
    private final EventManpowerNeeded manpowerNeeded = new EventManpowerNeeded("10");
    private final EventDate startDate = new EventDate(ParserUtil.parseAnyDate("20/10/2019"));
    private final EventDate endDate = new EventDate(ParserUtil.parseAnyDate("25/10/2019"));
    private final Set<Tag> tags = new HashSet<>();
    private final Event eventTest = new Event(name, venue,
            manpowerNeeded, startDate, endDate, tags);
    private final Event otherEventTest = new Event(otherName, venue,
            manpowerNeeded, startDate, endDate, tags);
    private final List<Event> eventListTest = new ArrayList<Event>(Arrays.asList(eventTest));
    private final List<Event> otherEventListTest = new ArrayList<Event>(Arrays.asList(otherEventTest));
    private final EventDate dateTest = new EventDate(ParserUtil.parseAnyDate("20/10/2019"));
    private final EventDate otherDateTest = new EventDate(ParserUtil.parseAnyDate("21/10/2019"));

    private final DistinctDate distinctDateTest = new DistinctDate(dateTest, eventListTest);
    private final DistinctDate otherDistinctDateTest = new DistinctDate(otherDateTest, otherEventListTest);

    DistinctDateTest() throws ParseException {
    }

    @Test
    public void isSameDistinctDate() throws ParseException {

        //same DistinctDate object --> Return true;
        assertTrue(distinctDateTest.isSameDistinctDate(distinctDateTest));

        // null -> returns false
        assertFalse(distinctDateTest.isSameDistinctDate(null));

        // Same object with different date and same list -> returns false
        DistinctDate editedDate = new DistinctDateBuilder(distinctDateTest)
                .withDate(ParserUtil.parseAnyDate("21/10/2019"))
                .withList(new ArrayList<>(Arrays.asList(eventTest))).build();
        assertFalse(distinctDateTest.isSameDistinctDate(editedDate));

        // Same object with same date and different list -> returns false
        DistinctDate editedDate2 = new DistinctDateBuilder(distinctDateTest)
                .withDate(ParserUtil.parseAnyDate("20/10/2019"))
                .withList(new ArrayList<>(Arrays.asList(otherEventTest))).build();
        assertFalse(distinctDateTest.isSameDistinctDate(editedDate2));

        //different DistinctDate Object with same date, same list --> Return false;
        EventDate newEventDate1 = new EventDate(ParserUtil.parseAnyDate("20/10/2019"));
        DistinctDate newDistinctDateTest1 = new DistinctDate(newEventDate1, eventListTest);
        assertTrue(distinctDateTest.isSameDistinctDate(newDistinctDateTest1));

        //different DistinctDate Object with different date, same list --> Return false;
        EventDate newEventDate2 = new EventDate(ParserUtil.parseAnyDate("21/10/2019"));
        DistinctDate newDistinctDateTest2 = new DistinctDate(newEventDate2, eventListTest);
        assertFalse(distinctDateTest.isSameDistinctDate(newDistinctDateTest2));

        //different DistinctDate Object with same date, different list --> Return false;
        EventDate newEventDate3 = new EventDate(ParserUtil.parseAnyDate("20/10/2019"));
        DistinctDate newDistinctDateTest3 = new DistinctDate(newEventDate3, otherEventListTest);
        assertFalse(distinctDateTest.isSameDistinctDate(newDistinctDateTest3));
    }

    @Test
    public void equals() throws ParseException {
        // same values -> returns true
        DistinctDate distinctDateCopy1 = new DistinctDateBuilder(distinctDateTest).build();
        assertTrue(distinctDateTest.equals(distinctDateCopy1));

        // same object -> returns true
        assertTrue(distinctDateTest.equals(distinctDateTest));

        // null -> returns false
        assertFalse(distinctDateTest.equals(null));

        // different type -> returns false
        assertFalse(distinctDateTest.equals(5));

        // different distinctDate Object -> returns false
        assertFalse(distinctDateTest.equals(otherDistinctDateTest));

        // different date, same list -> returns false
        DistinctDate editedDistinctDate1 = new DistinctDateBuilder(distinctDateTest)
                .withDate(ParserUtil.parseAnyDate("21/10/2019")).build();
        assertFalse(distinctDateTest.equals(editedDistinctDate1));

        // different list, same date -> returns false
        DistinctDate editedDistinctDate2 = new DistinctDateBuilder(distinctDateTest)
                .withList(otherEventListTest).build();
        assertFalse(distinctDateTest.equals(editedDistinctDate2));
    }

}
