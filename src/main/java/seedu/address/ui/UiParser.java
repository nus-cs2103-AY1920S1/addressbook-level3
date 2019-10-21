package seedu.address.ui;

import java.text.DateFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * A component to convert/parse the terms in post logic into readable, UI format for the users.
 */
public class UiParser {

    /**
     * Returns an array of Integer, size 3, containing {day, month, year} of the event.
     * @param date The time of a given event source
     * @return an array of Integer, size 3, containing {day, month, year} of the event
     */
    public Integer[] getDateToNumbers(Instant date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZoneId.systemDefault());
        String eventDate = dateFormat.format(date);
        String[] dayMonthYear = eventDate.split("/");
        Integer[] parsedDate = {Integer.valueOf(dayMonthYear[0]),
                Integer.valueOf(dayMonthYear[1]),
                Integer.valueOf(dayMonthYear[2])};
        return parsedDate;
    }

    /**
     * Returns an array of String indicating the date and time for the users to read.
     * @param date The given date of the event.
     * @return an array of String indicating the date and time for the users to read.
     */
    public String parseDateToString(Instant date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZoneId.systemDefault());
        return dateFormat.format(date);
    }

    public Integer getDay(Instant date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd")
                .withZone(ZoneId.systemDefault());
        String eventDate = dateFormat.format(date);
        return Integer.valueOf(eventDate);
    }

    public Integer getHour(Instant time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH")
                .withZone(ZoneId.systemDefault());
        return Integer.valueOf(timeFormatter.format(time));
    }

    public String getTime(Instant time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.systemDefault());
        return timeFormatter.format(time);
    }

    public Integer getWeek(Instant date) {
        Integer[] dayMonthYear = getDateToNumbers(date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(dayMonthYear[2], dayMonthYear[1] - 1, dayMonthYear[0]);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public Integer getEventIndex(Instant date) {
        Integer[] dayMonthYear = getDateToNumbers(date);
        LocalDate localDate = LocalDate.of(dayMonthYear[2], dayMonthYear[1], dayMonthYear[0]);
        return localDate.getDayOfWeek().getValue();
    }

    public Integer getPreviousMonthDays(int currentMonth, int currentYear) {
        // Checks for January
        if(currentMonth == 1) {
            return YearMonth.of(currentYear - 1, 12).lengthOfMonth();
        } else {
            return YearMonth.of(currentYear, currentMonth - 1).lengthOfMonth();
        }
    }

    public Integer getStartingDay(int week, int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = LocalDate.of(year, month, 1);
        int weekIndex = localDate.getDayOfWeek().getValue() - 1;
        int currentWeek = 0;    // Week is 0 index
        for(int currentDay = 1; currentDay < yearMonth.lengthOfMonth(); currentDay++) {
            if(currentWeek == week) {
                return currentDay;
            }
            weekIndex++;
            if(weekIndex == 6) {
                currentWeek++;
                weekIndex = 0;
            }
        }
        // TODO: If return null here means user input some wonky week number.
        return null;    // Not supposed to happen.
    }

    public String getEnglishDate(Integer day, Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return day + " " + monthStr + " " + year;
    }

    public String getEnglishDate(Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return monthStr + " " + year;
    }

    public String getEnglishWeekDate(Integer week, Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return week + " week of " + monthStr + " " + year;
    }

    public Integer getPreviousMonth(int month) {
        if(month == 1) {
            return 12;
        } else {
            return month - 1;
        }
    }

    public Integer getNextMonth(int month) {
        if(month == 12) {
            return 1;
        } else {
            return month + 1;
        }
    }

    public Integer getPreviousYear(int month, int year) {
        if(month == 1) {
            return year - 1;
        } else {
            return year;
        }
    }

    public Integer getNextYear(int month, int year) {
        if(month == 12) {
            return year + 1;
        } else {
            return year;
        }
    }

}
