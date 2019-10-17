package seedu.address.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Parse string date time using a formatter dd/MM/yyyy HHmm
 */
public class DefinedDateTimeParser implements DateTimeParser {

    /**
     * Processes the string using the given format and returns a LocalDateTime
     * @param stringDateTime of the format "dd/MM/yyyy HHmm"
     * @return LocalDateTime representation of the string
     */
    public LocalDateTime parseDateTime(String stringDateTime) throws DateTimeParseException {
        try {
            String[] splitTime = stringDateTime.split(" ");

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate processedDate = LocalDate.parse(splitTime[0], dateFormatter);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
            LocalTime processedTime = LocalTime.parse(splitTime[1], timeFormatter);

            LocalDateTime processedDateTime = LocalDateTime.of(processedDate, processedTime);
            return processedDateTime;
        } catch (Exception e) {
            throw new DateTimeParseException("Date Time format given is incorrect."
                    + " Should be \"25/09/2019 2300\"", e.getMessage(), 0);
        }
    }
}