package seedu.address.statistic;

import static seedu.address.commons.util.StringUtil.convertCalendarDateToString;

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
        Calendar endDate = statsPayload.getEndingDate();
        System.out.println(startDate.getTime());
        System.out.println(startDate.get(Calendar.MONTH));

        System.out.println(endDate.getTime());
        System.out.println(endDate.get(Calendar.MONTH));


        while (startDate.before(endDate) && !(startDate.get(Calendar.MONTH) == endDate.get(Calendar.MONTH))) {
            System.out.println(convertCalendarDateToString(startDate));
            Calendar temp = (Calendar) startDate.clone();
            listOfYearMonth.add(temp);
            startDate.add(Calendar.MONTH, 1);
        }
        listOfYearMonth.add(endDate);
        return listOfYearMonth;
    }
}
