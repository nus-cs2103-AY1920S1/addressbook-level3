package seedu.address.statistic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
;

import seedu.address.commons.util.StatsPayload;
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


        while(startDate.before(endDate)) {
            //System.out.println(convertCalendarDateToString(startDate));
            Calendar temp = (Calendar) startDate.clone();
            listOfYearMonth.add(temp);
            startDate.add(Calendar.MONTH,1);
        }
        listOfYearMonth.add(endDate);
        return listOfYearMonth;
    }
}
