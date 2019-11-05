package seedu.address.storage;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.diary.Diary;
import seedu.address.storage.diary.JsonAdaptedDiary;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.model.ModelTestUtil.*;
import static seedu.address.testutil.Assert.assertThrows;

class JsonAdaptedTripTest {
    //Blank Name
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws ParseException {
        JsonAdaptedTrip jsonAdaptedTrip = new JsonAdaptedTrip("",
                ParserDateUtil.getDateTimeFromString(VALID_STARTDATE_AFRICA_2),
                ParserDateUtil.getDateTimeFromString(VALID_ENDDATE_AFRICA_2),
                VALID_DESTINATION_AFRICA,
                1234d,
                new ArrayList<JsonAdaptedDay>(),
                new ArrayList<JsonAdaptedExpenditure>(),
                new JsonAdaptedDiary(new Diary()),
                Optional.empty(),
                new ArrayList<JsonAdaptedInventory>()
        );
        String expectedMessage = seedu.address.model.itinerary.Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedTrip::toModelType);
    }


}