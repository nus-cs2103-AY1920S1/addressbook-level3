package seedu.scheduler.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;

import org.junit.jupiter.api.Test;

class JsonAdaptedIntervieweeTest {

    @Test
    public void toModelType_validIntervieweeDetails_returnsInterviewee() throws Exception {
        JsonAdaptedInterviewee interviewee = new JsonAdaptedInterviewee(ALICE_INTERVIEWEE);
        assertEquals(ALICE_INTERVIEWEE, interviewee.toModelType());
    }
}
