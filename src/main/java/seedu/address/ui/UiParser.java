package seedu.address.ui;

import java.text.DateFormatSymbols;
import java.time.Instant;
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
}
