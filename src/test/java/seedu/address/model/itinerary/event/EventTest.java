package seedu.address.model.itinerary.event;

import org.junit.jupiter.api.Test;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;
import seedu.address.testutil.EventBuilder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.parser.ParserDateUtil.DATE_TIME_FORMATTER;
import static seedu.address.model.ModelTestUtil.*;

class EventTest {

    @Test
    void isSameEvent() {
        //Same object
        assertTrue(VALID_EVENT_1.isSameEvent(VALID_EVENT_1));

        //Same fields different objects
        assertTrue(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1).build()));

        //All fields same except name
        assertFalse(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1)
                .setName(new Name(VALID_NAME_EVENT_2)).build()));

        //All fields same except Budget TODO
//        assertTrue(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1)
//                .setTotalBudget(new Expenditure().build()));

        //All fields same except start date
        assertFalse(VALID_EVENT_1.isSameEvent(EventBuilder.of(VALID_EVENT_1)
                .setStartDate(LocalDate.parse(VALID_STARTDATE_EVENT_2_2, DATE_TIME_FORMATTER).atStartOfDay()).build()));
    }

    @Test
    void isClashingWith() {
        //Same object
        assertTrue(VALID_EVENT_1.isClashingWith(VALID_EVENT_1));

        //Same fields, different object
        assertTrue(VALID_EVENT_1.isClashingWith(EventBuilder.of(VALID_EVENT_1).build()));

        //Start date == other start date, end date > other end date
        assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_1));

        //start date > other start date, end date == other end date
        assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_2));

        //start date > other start date, end date > other end date
        assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_3));

        //start date < other start date, end date < other end date
        assertTrue(VALID_EVENT_1.isClashingWith(CLASHING_EVENT_4));
    }
}