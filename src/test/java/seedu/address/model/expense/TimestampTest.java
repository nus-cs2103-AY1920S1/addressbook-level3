package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class TimestampTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Timestamp(null));
    }

    //    @Test
    //    public void constructor_invalidTimestamp_throwsIllegalArgumentException() {
    //        String invalidTimestamp = "";
    //        assertThrows(IllegalArgumentException.class, () -> Timestamp.createTimestampIfValid(invalidTimestamp));
    //    }

    @Test
    public void createTimestampIfValid() {
        // null timestamp
        assertThrows(NullPointerException.class, () -> Timestamp.createTimestampIfValid(null));

        // invalid description
        Optional<Timestamp> invalidTimestampOne = Timestamp.createTimestampIfValid(""); // empty string
        Optional<Timestamp> invalidTimestampTwo = Timestamp.createTimestampIfValid("40-01"); // invalid day
        Optional<Timestamp> invalidTimestampThree = Timestamp.createTimestampIfValid("40-01-2019"); // invalid day
        Optional<Timestamp> invalidTimestampFour = Timestamp.createTimestampIfValid("01-13"); // invalid month
        Optional<Timestamp> invalidTimestampFive = Timestamp.createTimestampIfValid("01-13-2019"); // invalid month
        Optional<Timestamp> invalidTimestampSix = Timestamp.createTimestampIfValid("01-01-10000"); // invalid year
        assertTrue(invalidTimestampOne.isEmpty());
        assertTrue(invalidTimestampTwo.isEmpty());
        assertTrue(invalidTimestampThree.isEmpty());
        assertTrue(invalidTimestampFour.isEmpty());
        assertTrue(invalidTimestampFive.isEmpty());
        assertTrue(invalidTimestampSix.isEmpty());

        // valid description
        Optional<Timestamp> validTimestampOne = Timestamp.createTimestampIfValid("20-10"); // without year
        Optional<Timestamp> validTimestampTwo = Timestamp.createTimestampIfValid("12-01-2019"); // with year
        assertTrue(validTimestampOne.isPresent());
        assertTrue(validTimestampTwo.isPresent());
    }
}
