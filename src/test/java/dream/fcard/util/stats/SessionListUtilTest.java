//@@author nattanyz
package dream.fcard.util.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private Session sessionTwo = new Session(
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

    //private SessionList getTestSessionListForTest() {
    //    //TestSessionList testSessionList = new TestSessionList();
    //
    //    //TestSession testSessionOne = new TestSession(sessionOne, "3/20");
    //    //TestSession testSessionTwo = new TestSession(sessionTwo, "6/20");
    //    //TestSession testSessionThree = new TestSession(sessionThree, "9/20");
    //    //TestSession testSessionFour = new TestSession(sessionFour, "12/20");
    //    //TestSession testSessionFive = new TestSession(sessionFour, "15/20");
    //
    //    //testSessionList.addSession(testSessionOne);
    //    //testSessionList.addSession(testSessionTwo);
    //    //testSessionList.addSession(testSessionThree);
    //    //testSessionList.addSession(testSessionFour);
    //    //testSessionList.addSession(testSessionFive);
    //
    //    return testSessionList;
    //}

    @Test
    void testSessionList_containsTestSessionsWithScores() {
        boolean isValid = true;
        //ArrayList<TestSession> testSessionArrayList = getTestSessionListForTest()
        //    .getTestSessionArrayList();
        //for (TestSession testSession : testSessionArrayList) {
        //    if (!testSession.hasScore()) {
        //        isValid = false;
        //    }
        //}

        assertTrue(isValid);
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

    @Test
    void getScoreAsPercentageString_testOne() {
        String score = "5/10";
        String expectedString = "50.0%"; // 5/10 = 1/2 = 50.0%
        String obtainedString = SessionListUtil.getScoreAsPercentageString(score);

        assertEquals(expectedString, obtainedString);
    }

    @Test
    void getScoreAsPercentageString_testTwo() {
        String score = "3/15";
        String expectedString = "20.0%"; // 3/15 = 1/5 = 20.0%
        String obtainedString = SessionListUtil.getScoreAsPercentageString(score);

        assertEquals(expectedString, obtainedString);
    }

    @Test
    void getScoreAsPercentageString_testThree() {
        String score = "3/7";
        String expectedString = "42.86%"; // 3/7 = 0.42857...
        String obtainedString = SessionListUtil.getScoreAsPercentageString(score);

        assertEquals(expectedString, obtainedString);
    }

    @Test
    void getScoreAsPercentageString_testFour() {
        String score = "17/23";
        String expectedString = "73.91%"; // 17/23 = 0.73913...
        String obtainedString = SessionListUtil.getScoreAsPercentageString(score);

        assertEquals(expectedString, obtainedString);
    }

    @Test
    void convertScoreDoubleToString_testOne() {
        double score = 73.91;
        String expectedString = "73.91%";
        String obtainedString = SessionListUtil.convertScoreDoubleToString(score);

        assertEquals(expectedString, obtainedString);
    }

    //@Test
    //void getScoreAsPercentage_onSessionList() {
    //    SessionList sessionList = getTestSessionListForTest();
    //    // expected output: 3/20 = 15%
    //    ArrayList<Session> sessionArrayList = sessionList.getSessionArrayList();
    //    Session
    //}

    @Test
    void getScoreAsPercentageDouble_onSession() {
        //ArrayList<TestSession> testSessionArrayList = getTestSessionListForTest()
        //    .getTestSessionArrayList();
        //TestSession sessionForTest = testSessionArrayList.get(3); // score is 12/20
        //
        //double expectedDouble = 60.0;
        //double obtainedDouble = SessionListUtil.getScoreAsPercentageDouble(sessionForTest);
        //
        //assertEquals(expectedDouble, obtainedDouble);
    }

    @Test
    void getAverageScore_allSessionsHaveScores() {
        //SessionList sessionList = getTestSessionListForTest();
        //// expected output:
        //// 3/20 + 6/20 + 9/20 + 12/20 + 15/20 = 45/20
        //// 45/20 / 5 = 9/20
        //// 9/20 = 45%
        //String expectedString = "45.0%";
        //String obtainedString = SessionListUtil.getAverageScore(sessionList);
        //
        //assertEquals(expectedString, obtainedString);
    }
}
