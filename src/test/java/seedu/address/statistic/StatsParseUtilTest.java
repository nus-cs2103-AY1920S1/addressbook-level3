package seedu.address.statistic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class StatsParseUtilTest {

    @Test
    public void checkDate() {

        Calendar minExpDate = new Calendar
                .Builder()
                .setInstant(new Date(Long.MIN_VALUE))
                .build();
        Calendar maxExpDate = new Calendar
                .Builder()
                .setInstant(new Date(Long.MAX_VALUE))
                .build();

        assertTrue(minExpDate.equals(StatsParseUtil.MIN_DATE));
        assertTrue(maxExpDate.equals(StatsParseUtil.MAX_DATE));

    }

    @Test
    public void isValidCheckCorrect() {
        assertTrue(StatsParseUtil.isValidStatType("profit"));
        assertTrue(StatsParseUtil.isValidStatType("cost"));
        assertTrue(StatsParseUtil.isValidStatType("revenue"));
        assertFalse(StatsParseUtil.isValidStatType("PROFIT"));
        assertFalse(StatsParseUtil.isValidStatType("COST"));
        assertFalse(StatsParseUtil.isValidStatType("REVENUE"));
        assertFalse(StatsParseUtil.isValidStatType("REVEN"));
        assertFalse(StatsParseUtil.isValidStatType("  "));
        assertFalse(StatsParseUtil.isValidStatType("dadawawdn udhuksehakhiuihqkaahkiakiwkjahdkja"));
    }

}
