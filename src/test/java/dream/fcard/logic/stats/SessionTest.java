//@@author nattanyz
package dream.fcard.logic.stats;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class SessionTest {
    @Test
    public void createSessionWithStartAndEnd_verifyNoStackOverflow() {
        LocalDateTime start = LocalDateTime.of(2019, 10, 30, 18, 59);
        LocalDateTime end = LocalDateTime.of(2019, 10, 30, 23, 59);

        // expected output: new Session with specified start and end times, duration of 5 hours
        Session session = new Session(start, end);
    }
}
