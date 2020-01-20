package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class MeetingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Meeting(null, null));
    }

    @Test
    public void isValidTask() throws ParseException {
        Description description = new Description("Spotify Premium");
        Time time = new Time("19/09/2019 1900");
        assertTrue(Meeting.isValidMeeting(new Meeting(time, description)));
    }
}

