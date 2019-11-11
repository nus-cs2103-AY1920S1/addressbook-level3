package seedu.moolah.model.general;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.testutil.Assert.assertThrows;

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
        Optional<Timestamp> invalidTimestampOne =
                Timestamp.createTimestampIfValid(""); // empty string
        Optional<Timestamp> invalidTimestampTwo =
                Timestamp.createTimestampIfValid("zshfkbsuhf"); // gibberish
        assertTrue(invalidTimestampOne.isEmpty());
        assertTrue(invalidTimestampTwo.isEmpty());

        // valid description
        Optional<Timestamp> validTimestampOne =
                Timestamp.createTimestampIfValid("20-10"); // without year
        Optional<Timestamp> validTimestampTwo =
                Timestamp.createTimestampIfValid("12-01-2019"); // with year
        Optional<Timestamp> validTimestampThree =
                Timestamp.createTimestampIfValid("yesterday noon"); // natural language datetime
        Optional<Timestamp> validTimestampFour =
                Timestamp.createTimestampIfValid("1 week from now"); // natural language date
        Optional<Timestamp> validTimestampFive =
                Timestamp.createTimestampIfValid("4pm"); // natural language time
        assertTrue(validTimestampOne.isPresent());
        assertTrue(validTimestampTwo.isPresent());
        assertTrue(validTimestampThree.isPresent());
        assertTrue(validTimestampFour.isPresent());
        assertTrue(validTimestampFive.isPresent());
    }
}
