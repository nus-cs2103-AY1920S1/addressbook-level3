package com.typee.logic.interactive.parser;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InteractiveParserUtil {

    private static final String FORMAT_DATE_TIME = "dd/MM/yyyy/HHmm";

    public static boolean isValidDateTime(String dateTime) {
        try {
            makeDateTimeFromPattern(dateTime);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    private static LocalDateTime makeDateTimeFromPattern(String dateTime) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
        return localDateTime;
    }

    public static boolean isValidTimeSlot(String startDate, String endDate) {
        LocalDateTime start = makeDateTimeFromPattern(startDate);
        LocalDateTime end = makeDateTimeFromPattern(endDate);
        return start.isBefore(end);
    }
}
