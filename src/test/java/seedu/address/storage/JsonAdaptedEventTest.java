package seedu.address.storage;

import static seedu.address.model.ModelTestUtil.VALID_DESTINATION_EVENT_1;
import static seedu.address.model.ModelTestUtil.VALID_ENDDATE_EVENT_1_2;
import static seedu.address.model.ModelTestUtil.VALID_NAME_EVENT_1;
import static seedu.address.model.ModelTestUtil.VALID_STARTDATE_EVENT_1_2;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.itinerary.event.Event;

class JsonAdaptedEventTest {
    //Blank Name
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws ParseException {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent("",
                ParserDateUtil.getDateTimeFromString(VALID_STARTDATE_EVENT_1_2),
                ParserDateUtil.getDateTimeFromString(VALID_ENDDATE_EVENT_1_2),
                VALID_DESTINATION_EVENT_1,
                Optional.empty(),
                Optional.empty()
        );
        String expectedMessage = seedu.address.model.itinerary.Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedEvent::toModelType);
    }

    @Test
    public void toModelType_endDateBeforeStartDate_throwsIllegalValueException() throws ParseException {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(VALID_NAME_EVENT_1,
                ParserDateUtil.getDateTimeFromString(VALID_ENDDATE_EVENT_1_2),
                ParserDateUtil.getDateTimeFromString(VALID_STARTDATE_EVENT_1_2),
                VALID_DESTINATION_EVENT_1,
                Optional.empty(),
                Optional.empty()
        );
        String expectedMessage = Event.MESSAGE_INVALID_DATETIME;
        assertThrows(IllegalArgumentException.class, expectedMessage, jsonAdaptedEvent::toModelType);
    }

}
