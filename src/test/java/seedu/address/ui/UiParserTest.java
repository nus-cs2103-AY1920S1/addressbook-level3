package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class UiParserTest {

    private LocalDate date = LocalDate.parse("2019-11-18");
    private Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();

    @Test
    void getDateToNumbersTest() {
        UiParser uiParser = new UiParser();
        Integer[] dayMonthYear = uiParser.getDateToNumbers(instant);
        assertEquals(dayMonthYear[0], 18);
        assertEquals(dayMonthYear[1], 11);
        assertEquals(dayMonthYear[2], 2019);
    }

    @Test
    void parseDateToStringTest() {
        UiParser uiParser = new UiParser();
        String dateString = uiParser.parseDateToString(instant);
        assertEquals(dateString, "18/11/2019 00:00");
    }

    @Test
    void getDayTest() {
        UiParser uiParser = new UiParser();
        Integer day = uiParser.getDay(instant);
        assertEquals(day, 18);
    }

    @Test
    void getHourTest() {
        UiParser uiParser = new UiParser();
        Integer hour = uiParser.getHour(instant);
        assertEquals(hour, 0);
    }

    @Test
    void getTimeTest() {
        UiParser uiParser = new UiParser();
        String time = uiParser.getTime(instant);
        assertEquals(time, "00:00");
    }

    @Test
    void getEnglishDateTest() {
        UiParser uiParser = new UiParser();
        String dayMonthYear = uiParser.getEnglishDate(20, 5, 1998);
        String dayMonth = uiParser.getEnglishDate(7, 2007);
        assertEquals(dayMonthYear, "20 May 1998");
        assertEquals(dayMonth, "July 2007");;
    }
}
