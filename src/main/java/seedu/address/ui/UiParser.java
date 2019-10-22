package seedu.address.ui;

import java.text.DateFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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

    /**
     * Returns an Integer of the day of a date.
     *
     * @param date The given date to parse.
     * @return Returns an Integer of the day of a date.
     */
    public Integer getDay(Instant date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd")
                .withZone(ZoneId.systemDefault());
        String eventDate = dateFormat.format(date);
        return Integer.valueOf(eventDate);
    }

    /**
     * Returns an Integer of an hour of a time.
     *
     * @param time The given time to parse.
     * @return Returns an Integer of an hour of a time.
     */
    public Integer getHour(Instant time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH")
                .withZone(ZoneId.systemDefault());
        return Integer.valueOf(timeFormatter.format(time));
    }

    /**
     * Returns a String containing the time.
     *
     * @param time The given time to parse.
     * @return Returns a String of a given time.
     */
    public String getTime(Instant time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                .withZone(ZoneId.systemDefault());
        return timeFormatter.format(time);
    }

    /**
     * Returns an Integer of Index of a week.
     *
     * @param date The given date to parse.
     * @return Returns an Integer of Index of a week.
     */
    public Integer getWeekIndex(Instant date) {
        Integer[] dayMonthYear = getDateToNumbers(date);
        LocalDate localDate = LocalDate.of(dayMonthYear[2], dayMonthYear[1], dayMonthYear[0]);
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * Returns an Integer of previous month total days.
     *
     * @param currentMonth The given month to check for the previous one.
     * @param currentYear The given current year.
     * @return Returns an Integer of previous month total days.
     */
    public Integer getPreviousMonthDays(int currentMonth, int currentYear) {
        // Checks for January
        if (currentMonth == 1) {
            return YearMonth.of(currentYear - 1, 12).lengthOfMonth();
        } else {
            return YearMonth.of(currentYear, currentMonth - 1).lengthOfMonth();
        }
    }

    public String getFullEnglishDateTime(Instant date) {
        Integer[] dayMonthYear = getDateToNumbers(date);
        String englishDate = getEnglishDate(dayMonthYear[0], dayMonthYear[1], dayMonthYear[2]);
        return englishDate + " " + getTime(date);
    }


    /**
     * Returns a String of the english date of a given day, month and year.
     *
     * @param day The given day.
     * @param month The given month.
     * @param year The given year.
     * @return the english date of a given day, month and year.
     */
    public String getEnglishDate(Integer day, Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return day + " " + monthStr + " " + year;
    }

    /**
     * Returns a String of the english date of a given month and year.
     *
     * @param month The given month.
     * @param year The given year.
     * @return the english date of a given month and year.
     */
    public String getEnglishDate(Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return monthStr + " " + year;
    }

    /**
     * Returns a String of the english date of a given week, month and year.
     *
     * @param week The given week.
     * @param month The given month.
     * @param year The given year.
     * @return the english date of a given week, month and year.
     */
    public String getEnglishWeekDate(Integer week, Integer month, Integer year) {
        String monthStr = new DateFormatSymbols().getMonths()[month - 1].toLowerCase();
        monthStr = monthStr.substring(0, 1).toUpperCase() + monthStr.substring(1);
        return "Week " + week + " of " + monthStr + " " + year;
    }

    /**
     * Returns an Integer of the previous month of a given month.
     *
     * @param month The given month.
     * @return Returns an Integer of the previous month of a given month.
     */
    public Integer getPreviousMonth(int month) {
        if (month == 1) {
            return 12;
        } else {
            return month - 1;
        }
    }

    /**
     * Returns an Integer of the next month of a given month.
     *
     * @param month The given month.
     * @return Returns an Integer of the next month of a given month.
     */
    public Integer getNextMonth(int month) {
        if (month == 12) {
            return 1;
        } else {
            return month + 1;
        }
    }

    /**
     * Returns an Integer of the previous year if the current month is January.
     *
     * @param month The current month to check if it is January.
     * @param year The current year.
     * @return Returns an Integer of the previous year if the current month is January.
     */
    public Integer getPreviousYear(int month, int year) {
        if (month == 1) {
            return year - 1;
        } else {
            return year;
        }
    }

    /**
     * Returns an Integer of the next year if the current month is December.
     *
     * @param month The current month to check if it is December.
     * @param year The current year.
     * @return Returns an Integer of the next year if the current month is December.
     */
    public Integer getNextYear(int month, int year) {
        if (month == 12) {
            return year + 1;
        } else {
            return year;
        }
    }

}
