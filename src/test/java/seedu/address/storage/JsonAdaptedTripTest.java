package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelTestUtil;
import seedu.address.model.diary.Diary;
import seedu.address.storage.diary.JsonAdaptedDiary;


class JsonAdaptedTripTest {
    //Blank Name
    @Test
    public void toModelType_invalidName_throwsIllegalValueException() throws ParseException {
        JsonAdaptedTrip jsonAdaptedTrip = new JsonAdaptedTrip("",
                ParserDateUtil.getDateTimeFromString(ModelTestUtil.VALID_STARTDATE_AFRICA_2),
                ParserDateUtil.getDateTimeFromString(ModelTestUtil.VALID_ENDDATE_AFRICA_2),
                ModelTestUtil.VALID_DESTINATION_AFRICA,
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
    @Test
    public void toModelType_invalidEndDateBeforeStartDate_throwsIllegalValueException() throws ParseException {
        JsonAdaptedTrip jsonAdaptedTrip = new JsonAdaptedTrip("",
                ParserDateUtil.getDateTimeFromString(ModelTestUtil.VALID_STARTDATE_AFRICA_2),
                LocalDateTime.of(2018, 12, 15, 0, 0),
                ModelTestUtil.VALID_DESTINATION_AFRICA,
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
