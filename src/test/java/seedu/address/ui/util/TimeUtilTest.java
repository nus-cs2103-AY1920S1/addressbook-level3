package seedu.address.ui.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeUtilTest {

    @Test
    public void formatTimeToIntCorrectly() {
        //Max value.
        assertEquals(TimeUtil.formatTimeToInt(LocalTime.MAX), 2359);
        //Min value.
        assertEquals(TimeUtil.formatTimeToInt(LocalTime.MIN), 0);
        //Normal value
        assertEquals(TimeUtil.formatTimeToInt(LocalTime.of(9, 30)), 930);
    }

    @Test
    public void formatHourToStringCorrectly() {
        //Max value
        assertEquals(TimeUtil.formatHourToString(23), "2300");
        //Min value
        assertEquals(TimeUtil.formatHourToString(0000), "0000");
        //Case before 1000
        assertEquals(TimeUtil.formatHourToString(2), "0200");
        //Case after 1000
        assertEquals(TimeUtil.formatHourToString(16), "1600");
    }

    @Test
    public void getTimeDifferenceCorrectly() {
        //Before 1200
        assertEquals(TimeUtil.getTimeDifference(LocalTime.of(9, 35),
                LocalTime.of(10, 40)), 65);
        //After 1200
        assertEquals(TimeUtil.getTimeDifference(LocalTime.of(13, 45),
                LocalTime.of(15, 20)), 95);
        //During 1200
        assertEquals(TimeUtil.getTimeDifference(LocalTime.of(11, 20),
                LocalTime.of(13, 0)), 100);
    }
}
