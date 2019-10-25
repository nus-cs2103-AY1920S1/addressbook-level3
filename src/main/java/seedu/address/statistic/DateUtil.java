package seedu.address.statistic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import seedu.address.model.order.Order;

/**
 * Utility class to help with extracting date value for use in statistic calculations
 */
public class DateUtil {

    public static int extractMonth(Order order) {
        return order.getSchedule().get().getCalendar().get(Calendar.MONTH);
    }

    public static int extractYear(Order order) {
        return order.getSchedule().get().getCalendar().get(Calendar.YEAR);
    }

    public static List<Calendar> getListOfYearMonth(StatsPayload statsPayload) {

        List<Calendar> listOfYearMonth = new ArrayList<>();
        Calendar startDate = statsPayload.getStartingDate();
        int startingMonth = startDate.get(Calendar.MONTH);
        int startingYear = startDate.get(Calendar.YEAR);
        int startingDay = startDate.get(Calendar.DAY_OF_MONTH);
        Calendar endDate = statsPayload.getEndingDate();
        int endingMonth = endDate.get(Calendar.MONTH);
        int endingYear = endDate.get(Calendar.YEAR);
        int endingDay = endDate.get(Calendar.DAY_OF_MONTH);

        Calendar startDateCloned = new Calendar.Builder()
                .setDate(startingYear, startingMonth, startingDay)
                .build();
        Calendar endDateCloned = new Calendar.Builder()
                .setDate(endingYear, endingMonth, endingDay)
                .build();
        while (startDateCloned.before(endDateCloned)) {
            if (startDateCloned.get(Calendar.MONTH) == endDateCloned.get(Calendar.MONTH)
                    && startDateCloned.get(Calendar.YEAR) == endDateCloned.get(Calendar.YEAR)) {
                //same month same year i dont add
                break;
            } else {
                Calendar temp = (Calendar) startDateCloned.clone();
                listOfYearMonth.add(temp);
                startDateCloned.add(Calendar.MONTH, 1);
            }
        }
        listOfYearMonth.add(endDate);
        return listOfYearMonth;
    }
}
