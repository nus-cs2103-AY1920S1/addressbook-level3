//@@author nattanyz
package dream.fcard.util.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;

public class SessionListUtilTest {

    private Session sessionOne = new Session(
        LocalDateTime.of(2019, 7, 23, 9, 15),
        LocalDateTime.of(2019, 7, 24, 0, 5)
    );

    private Session sessionTwo =new Session(
        LocalDateTime.of(2019, 8, 15, 6, 7),
        LocalDateTime.of(2019, 8, 15, 8, 12)
    );

    private Session sessionThree = new Session(
        LocalDateTime.of(2019, 11, 2, 10, 20),
        LocalDateTime.of(2019, 11, 2, 15, 6)
    );

    private Session sessionFour = new Session(
        LocalDateTime.of(2019, 10, 30, 10, 20),
        LocalDateTime.of(2019, 11, 1, 3, 1)
    );

    private Session sessionFive = new Session(
        LocalDateTime.of(2019, 10, 29, 18, 29),
        LocalDateTime.of(2019, 10, 30, 15, 6)
    );

    private SessionList getSessionListForTest() {
        SessionList sessionListForTest = new SessionList();

        sessionListForTest.addSession(sessionOne);
        sessionListForTest.addSession(sessionTwo);
        sessionListForTest.addSession(sessionThree);
        sessionListForTest.addSession(sessionFour);
        sessionListForTest.addSession(sessionFive);

        return sessionListForTest;
    }

    @Test
    void getSublistAfterCutoff_testOne() {
        SessionList sessionListForTest = getSessionListForTest();
        LocalDateTime now = LocalDateTime.of(2019, 11, 6, 9, 38);
        LocalDateTime cutoff = DateTimeUtil.getLastWeekCutoffDate(now);
        // expected: should get only sessionThree and sessionFour, but not sessionFive

        SessionList obtainedList = SessionListUtil.getSublistAfterCutoff(sessionListForTest, cutoff);
        ArrayList<Session> obtainedArrayList = obtainedList.getSessionArrayList();

        SessionList expectedList = new SessionList();
        expectedList.addSession(sessionThree);
        expectedList.addSession(sessionFour);
        ArrayList<Session> expectedArrayList = expectedList.getSessionArrayList();

        assertEquals(expectedArrayList, obtainedArrayList);
    }

}
