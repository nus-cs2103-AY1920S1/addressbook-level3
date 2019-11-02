package seedu.address.calendar.model.util;

import seedu.address.calendar.model.date.MonthOfYear;

import java.util.Optional;
import java.util.stream.Stream;

public class MonthOfYearUtil {
    private static int NUM_MONTHS_IN_YEAR = 12;

    static int getNumMonthsInYear() {
        return NUM_MONTHS_IN_YEAR;
    }

    static MonthOfYear convertJavaMonth(int javaMonth) {
        return MonthOfYear.values()[javaMonth];
    }

    static boolean isValidMonthNum(int monthNum) {
        return monthNum < MonthOfYear.values().length && monthNum > 0;
    }

    static MonthOfYear convertNumToMonth(int monthNum) {
        return convertJavaMonth(monthNum);
    }

    static boolean isValidMonthStr(String monthStr) {
        return Stream.of(MonthOfYear.values())
                .anyMatch(month -> {
                    String monthLowerCase = month.toString().toLowerCase();
                    String monthStrLowerCase = monthStr.toLowerCase();
                    return monthLowerCase.contains(monthStrLowerCase);
                });
    }

    static MonthOfYear convertStrToMonth(String monthStr) {
        Optional<MonthOfYear> monthOfYear = Stream.of(MonthOfYear.values())
                .filter(month -> {
                    String monthLowerCase = month.toString().toLowerCase();
                    String monthStrLowerCase = monthStr.toLowerCase();
                    return monthLowerCase.contains(monthStrLowerCase);
                })
                .findFirst();

        if (monthOfYear.isEmpty()) {
            assert false : "monthStr should be a valid representation of a month";
        }

        return monthOfYear.get();
    }
}
