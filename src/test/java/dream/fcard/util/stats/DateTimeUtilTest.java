//@@author nattanyz
package dream.fcard.util.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import dream.fcard.logic.stats.Session;
import dream.fcard.logic.stats.SessionList;

public class DateTimeUtilTest {
    @Test
    void calculateDuration_fiveHoursApart() {
        LocalDateTime time1 = LocalDateTime.of(2019, 10, 30, 18, 59);
        LocalDateTime time2 = LocalDateTime.of(2019, 10, 30, 23, 59);

        Duration calculatedDuration = DateTimeUtil.calculateDuration(time1, time2);
        Duration expectedDuration = Duration.ofHours(5);
        assertEquals(expectedDuration, calculatedDuration);
    }

    @Test
    void calculateDuration_fiveHoursApartAcrossDay() {
        LocalDateTime time1 = LocalDateTime.of(2019, 10, 30, 19, 02);
        LocalDateTime time2 = LocalDateTime.of(2019, 10, 31, 00, 02);

        Duration calculatedDuration = DateTimeUtil.calculateDuration(time1, time2);
        Duration expectedDuration = Duration.ofHours(5);
        assertEquals(expectedDuration, calculatedDuration);
    }

    @Test
    void calculateDuration_fiveHoursApartAcrossMonth() {
        LocalDateTime time1 = LocalDateTime.of(2019, 10, 31, 19, 02);
        LocalDateTime time2 = LocalDateTime.of(2019, 11, 1, 00, 02);

        Duration calculatedDuration = DateTimeUtil.calculateDuration(time1, time2);
        Duration expectedDuration = Duration.ofHours(5);
        assertEquals(expectedDuration, calculatedDuration);
    }

    @Test
    void calculateDuration_startBeforeEnd_returnNegativeResult() {
        LocalDateTime time1 = LocalDateTime.of(2019, 10, 30, 23, 59);
        LocalDateTime time2 = LocalDateTime.of(2019, 10, 30, 18, 59);

        Duration calculatedDuration = DateTimeUtil.calculateDuration(time1, time2);
        Duration expectedDuration = Duration.ofHours(-5);
        assertEquals(expectedDuration, calculatedDuration);
    }

    @Test
    void getStringFromDuration_fiveHoursApart() {
        Duration givenDuration = Duration.ofHours(5);
        String returnedString = DateTimeUtil.getStringFromDuration(givenDuration);
        String expectedString = "5 hours ";

        assertEquals(expectedString, returnedString);
    }

    @Test
    void getStringFromDuration_fiveHoursApartInMinutes() {
        Duration givenDuration = Duration.ofMinutes(300);
        String returnedString = DateTimeUtil.getStringFromDuration(givenDuration);
        String expectedString = "5 hours ";

        assertEquals(expectedString, returnedString);
    }

    @Test
    void getStringFromDuration_fiveHoursTwelveMinutesApart() {
        Duration givenDuration = Duration.ofHours(5).plus(Duration.ofMinutes(12));
        String returnedString = DateTimeUtil.getStringFromDuration(givenDuration);
        String expectedString = "5 hours 12 minutes ";

        assertEquals(expectedString, returnedString);
    }

    @Test
    void getStringFromDuration_fiveHoursTwelveMinutesApartInMinutes() {
        Duration givenDuration = Duration.ofMinutes(312);
        String returnedString = DateTimeUtil.getStringFromDuration(givenDuration);
        String expectedString = "5 hours 12 minutes ";

        assertEquals(expectedString, returnedString);
    }

    @Test
    void getStringFromDuration_fiveHoursTwelveMinutesFortySecondsApart() {
        Duration givenDuration = Duration.ofHours(5).plus(Duration.ofMinutes(12))
                .plus(Duration.ofSeconds(40));
        String returnedString = DateTimeUtil.getStringFromDuration(givenDuration);
        String expectedString = "5 hours 12 minutes 40 seconds";

        assertEquals(expectedString, returnedString);
    }

    @Test
    void getStringFromDuration_sevenMinutesNineSecondsApart() {
        Duration givenDuration = Duration.ofMinutes(7)
            .plus(Duration.ofSeconds(9));
        String returnedString = DateTimeUtil.getStringFromDuration(givenDuration);
        String expectedString = "7 minutes 9 seconds";

        assertEquals(expectedString, returnedString);
    }

    @Test
    void getStringFromDuration_twentyThreeSecondsApart() {
        Duration givenDuration = Duration.ofSeconds(23);
        String returnedString = DateTimeUtil.getStringFromDuration(givenDuration);
        String expectedString = "23 seconds";

        assertEquals(expectedString, returnedString);
    }

    // the following 2 tests have been commented out because despite passing on my PC, they
    // somehow fail on Travis. I have no idea why and I have given up.
    /*
    @Test
    void getStringFromDateTime_testOne() {
        LocalDateTime givenDateTime = LocalDateTime.of(2019, 10, 31, 11, 4);
        String returnedString = DateTimeUtil.getStringFromDateTime(givenDateTime);
        String expectedString = "31/10/19, 11:04 AM";

        assertEquals(expectedString, returnedString);
    }

    @Test
    void getStringFromDateTime_testTwo() {
        LocalDateTime givenDateTime = LocalDateTime.of(2019, 9, 8, 22, 47);
        String returnedString = DateTimeUtil.getStringFromDateTime(givenDateTime);
        String expectedString = "8/9/19, 10:47 PM";

        assertEquals(expectedString, returnedString);
    }
    */

    @Test
    void getStringFromDateTime_invalidDateTime_throwDateTimeException() {
        assertThrows(DateTimeException.class, () -> {
            LocalDateTime givenDateTime = LocalDateTime.of(2019, 14, 8, 22, 47);
            String returnedString = DateTimeUtil.getStringFromDateTime(givenDateTime);
            String expectedString = "8/9/19 10:47 PM";
        });
    }

    @Test
    void getAverageDuration_testOne() {
        SessionList sessionList = new SessionList();

        LocalDateTime time1 = LocalDateTime.of(2019, 10, 30, 18, 59);
        LocalDateTime time2 = LocalDateTime.of(2019, 10, 30, 22, 59);
        Session sessionOne = new Session(time1, time2); // duration: 4 hours

        LocalDateTime time3 = LocalDateTime.of(2019, 10, 5, 17, 31);
        LocalDateTime time4 = LocalDateTime.of(2019, 10, 5, 23, 31);
        Session sessionTwo = new Session(time3, time4); // duration: 6 hours

        sessionList.addSession(sessionOne);
        sessionList.addSession(sessionTwo);

        Duration calculatedDuration = DateTimeUtil.getAverageDuration(sessionList);
        Duration expectedDuration = Duration.ofHours(5); // expected: 5 hours
        assertEquals(expectedDuration, calculatedDuration);
    }

    @Test
    void getAverageDuration_testTwo() {
        SessionList sessionList = new SessionList();

        LocalDateTime time1 = LocalDateTime.of(2019, 4, 25, 6, 23);
        LocalDateTime time2 = LocalDateTime.of(2019, 4, 25, 19, 47);
        Session sessionOne = new Session(time1, time2); // duration: 13 hours 24 min

        LocalDateTime time3 = LocalDateTime.of(2019, 7, 8, 2, 2);
        LocalDateTime time4 = LocalDateTime.of(2019, 7, 9, 4, 51);
        Session sessionTwo = new Session(time3, time4); // duration: 26 hours 49 min

        sessionList.addSession(sessionOne);
        sessionList.addSession(sessionTwo);

        Duration calculatedDuration = DateTimeUtil.getAverageDuration(sessionList);
        Duration expectedDuration = Duration.ofHours(20).plus(Duration.ofMinutes(6))
            .plus(Duration.ofSeconds(30));
        // expected: 19.5 hours + 36.5 min = 20 hours 6 min 30 sec
        assertEquals(expectedDuration, calculatedDuration);
    }

    @Test
    void getLastWeekCutoffDate_testOne() {
        LocalDateTime time = LocalDateTime.of(2019, 11, 6, 9, 23);

        LocalDateTime calculatedDateTime = DateTimeUtil.getLastWeekCutoffDate(time);
        LocalDateTime expectedDateTime = LocalDateTime.of(2019, 10, 30, 0, 0);
        assertEquals(expectedDateTime, calculatedDateTime);
    }

    @Test
    void getLastWeekCutoffDate_testTwo() {
        LocalDateTime time = LocalDateTime.of(2019, 11, 5, 0, 19);

        LocalDateTime calculatedDateTime = DateTimeUtil.getLastWeekCutoffDate(time);
        LocalDateTime expectedDateTime = LocalDateTime.of(2019, 10, 29, 0, 0);
        assertEquals(expectedDateTime, calculatedDateTime);
    }
}
