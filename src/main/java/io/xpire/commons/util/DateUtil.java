package io.xpire.commons.util;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;

/**
 * Helper functions for handling dates.
 */
public class DateUtil {
    private static final Logger logger = LogsCenter.getLogger(DateUtil.class);

    public static String convertDateToString(LocalDate date, String dateFormat) {
        requireAllNonNull(date, dateFormat);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            return date.format(formatter);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to recognise date format : " + StringUtil.getDetails(e));
        } catch (DateTimeException e) {
            logger.warning("Failed to parse date to string : " + StringUtil.getDetails(e));
        }
        return "";
    }

    public static LocalDate convertStringToDate(String dateInString, String dateFormat) {
        requireAllNonNull(dateInString, dateFormat);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            return LocalDate.parse(dateInString, formatter);
        } catch (IllegalArgumentException e) {
            logger.warning("Failed to recognise date format : " + StringUtil.getDetails(e));
        } catch (DateTimeException e) {
            logger.warning("Failed to parse string to date : " + StringUtil.getDetails(e));
        }
        return null;
    }

    public static boolean isWithinRange(int days, LocalDate earlierDate, LocalDate laterDate) {
        requireAllNonNull(earlierDate, laterDate);
        AppUtil.checkArgument(earlierDate.compareTo(laterDate) > 0 || days < 0);
        return earlierDate.plusDays(days).compareTo(laterDate) >= 0;
    }

    public static int getOffsetDays(LocalDate earlierDate, LocalDate laterDate) {
        requireAllNonNull(earlierDate, laterDate);
        int offset = earlierDate.until(laterDate).getDays();
        return Math.max(offset, 0);
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
