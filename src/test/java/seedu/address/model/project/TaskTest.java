package seedu.address.model.project;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class TaskTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Task(null, null, true));
    }

    @Test
    public void isValidTask() throws ParseException {
        Description description = new Description("peter the 2nd");
        Time time = new Time("05/05/2019 1600");
        assertTrue(Task.isValidTask(new Task(description, time, true)));
    }
}
