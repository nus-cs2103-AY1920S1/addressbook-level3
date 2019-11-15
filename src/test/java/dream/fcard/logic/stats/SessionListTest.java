//@@author nattanyz
package dream.fcard.logic.stats;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class SessionListTest {
    @Test
    public void createMultipleSessionsAndAddToArrayList_verifyNoStackOverflow() {
        ArrayList<Session> arrayListOfSessions = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            LocalDateTime start = LocalDateTime.of(2019, 10, 30, 18, 59);
            LocalDateTime end = LocalDateTime.of(2019, 10, 30, 23, 59);

            // expected output: new Session with specified start and end times, duration of 5 hours
            Session session = new Session(start, end);
            arrayListOfSessions.add(session);
        }

        SessionList sessionList = new SessionList(arrayListOfSessions);
    }
}
