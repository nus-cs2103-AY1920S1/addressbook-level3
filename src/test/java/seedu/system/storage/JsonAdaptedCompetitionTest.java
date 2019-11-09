package seedu.system.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.system.storage.JsonAdaptedCompetition.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.system.testutil.Assert.assertThrows;
import static seedu.system.testutil.TypicalCompetitions.NUS_OPEN;

import org.junit.jupiter.api.Test;

import seedu.system.commons.exceptions.IllegalValueException;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Name;

public class JsonAdaptedCompetitionTest {
    private static final String INVALID_NAME = "NT@ OPEN";
    private static final String INVALID_START_DATE = "+01/01/2001";
    private static final String INVALID_END_DATE = "01/O1/2002";

    private static final String VALID_NAME = NUS_OPEN.getName().toString();
    private static final String VALID_START_DATE = NUS_OPEN.getStartDate().toString();
    private static final String VALID_END_DATE = NUS_OPEN.getEndDate().toString();


    @Test
    public void toModelType_validCompetitionDetails_returnsCompetition() throws Exception {
        JsonAdaptedCompetition competition = new JsonAdaptedCompetition(NUS_OPEN);
        assertEquals(NUS_OPEN, competition.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson competition =
                new JsonAdaptedPerson(INVALID_NAME, VALID_START_DATE, VALID_END_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, competition::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedCompetition competition = new JsonAdaptedCompetition(null, VALID_START_DATE, VALID_END_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, competition::toModelType);
    }

    @Test
    public void toModelType_invalidStartDate_throwsIllegalValueException() {
        JsonAdaptedCompetition competition =
                new JsonAdaptedCompetition(VALID_NAME, INVALID_START_DATE, VALID_END_DATE);
        String expectedMessage = CustomDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, competition::toModelType);
    }

    @Test
    public void toModelType_nullStartDate_throwsIllegalValueException() {
        JsonAdaptedCompetition competition = new JsonAdaptedCompetition(VALID_NAME, null, VALID_END_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CustomDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, competition::toModelType);
    }

    @Test
    public void toModelType_invalidEndDate_throwsIllegalValueException() {
        JsonAdaptedCompetition competition =
            new JsonAdaptedCompetition(VALID_NAME, VALID_START_DATE, INVALID_END_DATE);
        String expectedMessage = CustomDate.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, competition::toModelType);
    }

    @Test
    public void toModelType_nullEndDate_throwsIllegalValueException() {
        JsonAdaptedCompetition competition = new JsonAdaptedCompetition(VALID_NAME, VALID_START_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CustomDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, competition::toModelType);
    }
}
