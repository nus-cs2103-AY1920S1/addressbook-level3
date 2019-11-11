package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.earnings.JsonAdaptedEarnings.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEarnings.CS2107_EARNINGS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.classid.ClassId;
import seedu.address.model.earnings.Amount;
import seedu.address.model.earnings.Claim;
import seedu.address.model.earnings.Count;
import seedu.address.model.earnings.Date;
import seedu.address.model.earnings.Type;
import seedu.address.storage.earnings.JsonAdaptedEarnings;

public class JsonAdaptedEarningsTest {
    private static final String INVALID_DATE = "523/23-2033";
    private static final String INVALID_TYPE = "meeting+consultation";
    private static final String INVALID_CLASSID = " ";
    private static final String INVALID_AMOUNT = "23.241";
    private static final String INVALID_CLAIM = "waiting";
    private static final String INVALID_COUNT = "15";

    private static final String VALID_DATE = CS2107_EARNINGS.getDate().toString();
    private static final String VALID_TYPE = CS2107_EARNINGS.getType().toString();
    private static final String VALID_CLASSID = CS2107_EARNINGS.getClassId().toString();
    private static final String VALID_AMOUNT = CS2107_EARNINGS.getAmount().toString();
    private static final String VALID_CLAIM = CS2107_EARNINGS.getClaim().toString();
    private static final String VALID_COUNT = CS2107_EARNINGS.getCount().toString();


    @Test
    public void toModelType_validEarningsDetails_returnsEarnings() throws Exception {
        JsonAdaptedEarnings earnings = new JsonAdaptedEarnings(CS2107_EARNINGS);
        assertEquals(CS2107_EARNINGS, earnings.toModelType());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(INVALID_DATE, VALID_CLASSID,
                VALID_AMOUNT, VALID_TYPE, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(null, VALID_CLASSID, VALID_AMOUNT, VALID_TYPE, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID,
                        VALID_AMOUNT, INVALID_TYPE, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = Type.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID, VALID_AMOUNT, null, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_invalidClassId_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, INVALID_CLASSID,
                        VALID_AMOUNT, VALID_TYPE, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = ClassId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_nullClassId_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, null, VALID_AMOUNT, VALID_TYPE, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ClassId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID,
                        INVALID_AMOUNT, VALID_TYPE, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = Amount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID, null, VALID_TYPE, VALID_CLAIM, VALID_COUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Amount.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_invalidClaim_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID,
                        VALID_AMOUNT, VALID_TYPE, INVALID_CLAIM, VALID_COUNT);
        String expectedMessage = Claim.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_nullClaim_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID, VALID_AMOUNT, VALID_TYPE, null, VALID_COUNT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Claim.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_invalidCount_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID,
                        VALID_AMOUNT, VALID_TYPE, VALID_CLAIM, INVALID_COUNT);
        String expectedMessage = Count.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }

    @Test
    public void toModelType_nullCount_throwsIllegalValueException() {
        JsonAdaptedEarnings earnings =
                new JsonAdaptedEarnings(VALID_DATE, VALID_CLASSID, VALID_AMOUNT, VALID_TYPE, VALID_CLAIM, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Count.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, earnings::toModelType);
    }
}
