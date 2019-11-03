package com.typee.logic.interactive.parser;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InteractiveParserUtil {

    private static final String FORMAT_DATE_TIME = "dd/MM/yyyy/HHmm";

    public static boolean isValidDateTime(String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME);
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
