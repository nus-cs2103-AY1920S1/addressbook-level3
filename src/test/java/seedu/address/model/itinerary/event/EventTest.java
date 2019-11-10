package seedu.address.model.itinerary.event;

import static seedu.address.logic.parser.ParserDateUtil.DATE_TIME_FORMATTER;
import static seedu.address.model.ModelTestUtil.CLASHING_EVENT_1;
import static seedu.address.model.ModelTestUtil.CLASHING_EVENT_2;
import static seedu.address.model.ModelTestUtil.CLASHING_EVENT_3;
import static seedu.address.model.ModelTestUtil.CLASHING_EVENT_4;
import static seedu.address.model.ModelTestUtil.VALID_EVENT_1;
import static seedu.address.model.ModelTestUtil.VALID_NAME_EVENT_2;
import static seedu.address.model.ModelTestUtil.VALID_STARTDATE_DAY_2_2;
import static seedu.address.model.ModelTestUtil.VALID_STARTDATE_EVENT_2_2;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.testutil.EventBuilder;

class EventTest {

    @Test
    void isSameEvent() {
        //Same object
        Assertions.assertTrue(VALID_EVENT_1.isSameEvent(VALID_EVENT_1));

        //Same fields different objects
        Assertions.assertTrue(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1).build()));

        //All fields same except name
        Assertions.assertFalse(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1)
                .setName(new Name(VALID_NAME_EVENT_2)).build()));

        //All fields same except Budget TODO
        //        assertTrue(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1)
        //                .setTotalBudget(new Expenditure().build()));

        //All fields same except start date
        Assertions.assertFalse(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1)
                .setStartDate(LocalDate.parse(VALID_STARTDATE_EVENT_2_2, DATE_TIME_FORMATTER).atStartOfDay()).build()));
    }

    @Test
    void isClashingWith() {
        //Same object
        Assertions.assertTrue(VALID_EVENT_1.isClashingWith(VALID_EVENT_1));

        //Same fields, different object
        Assertions.assertTrue(VALID_EVENT_1.isClashingWith(EventBuilder.of(VALID_EVENT_1).build()));

        //Start date == other start date, end date > other end date
        Assertions.assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_1));

        //start date > other start date, end date == other end date
        Assertions.assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_2));

        //start date > other start date, end date > other end date
        Assertions.assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_3));

        //start date < other start date, end date < other end date
        Assertions.assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_4));
    }

    @Test
    void equals() {
        // Same instance
        Assertions.assertTrue(VALID_EVENT_1.equals(VALID_EVENT_1));

        // Different instance same properties
        Assertions.assertTrue(VALID_EVENT_1.equals(EventBuilder.of(VALID_EVENT_1).build()));

        //The following tests assumes all other properties are the same
        // Different Start Date
        Assertions.assertFalse(VALID_EVENT_1.equals(EventBuilder.of(VALID_EVENT_1)
                .setStartDate(LocalDateTime.parse(VALID_STARTDATE_DAY_2_2, DATE_TIME_FORMATTER)).build()));

        // Different End Date
        Assertions.assertFalse(VALID_EVENT_1.equals(EventBuilder.of(VALID_EVENT_1)
                .setEndDate(LocalDateTime.parse("5/1/2019 1200", DATE_TIME_FORMATTER)).build()));

        // Different Destination
        Assertions.assertFalse(VALID_EVENT_1.equals(EventBuilder.of(VALID_EVENT_1)
                .setLocation(new Location("Zimbabwe")).build()));

        // Different Name
        Assertions.assertFalse(VALID_EVENT_1.equals(EventBuilder.of(VALID_EVENT_1)
                .setName(new Name("Different Name")).build()));

    }
}
