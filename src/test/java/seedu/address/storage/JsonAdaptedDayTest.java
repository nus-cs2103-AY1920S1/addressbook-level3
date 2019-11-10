package seedu.address.storage;

import static seedu.address.model.ModelTestUtil.VALID_DESTINATION_DAY_1;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelTestUtil;

class JsonAdaptedDayTest {

    //
    @Test
    public void toModelType_invalidEndDateBeforeStartDate_throwsIllegalValueException() throws ParseException {
        JsonAdaptedDay jsonAdaptedDay =
                new JsonAdaptedDay(ParserDateUtil.getDateTimeFromString(ModelTestUtil.VALID_STARTDATE_DAY_1_2),
                LocalDateTime.of(2019, 1, 1, 0, 0),
                VALID_DESTINATION_DAY_1 ,
                Optional.empty(),
                Optional.empty(),
                new ArrayList<>(),
                Optional.empty()
        );
        String expectedMessage = seedu.address.model.itinerary.Name.MESSAGE_CONSTRAINTS;
        assertThrows(AssertionError.class, null,
                jsonAdaptedDay::toModelType);
    }
}
