//@@author nattanyz
package dream.fcard.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

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
}
