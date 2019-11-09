package seedu.address.model.performance;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMING;
import static seedu.address.logic.parser.DeleteCommandParserTest.VALID_DATE;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RecordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // both null
        assertThrows(NullPointerException.class, () -> new Record(null, null));
        // date null
        assertThrows(NullPointerException.class, () -> new Record(null, new Timing(VALID_TIMING)));
        // timing null
        assertThrows(NullPointerException.class, () -> new Record(VALID_DATE, null));
    }

}
