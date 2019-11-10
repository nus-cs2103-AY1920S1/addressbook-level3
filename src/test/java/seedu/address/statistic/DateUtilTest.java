package seedu.address.statistic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.Order;
import seedu.address.testutil.TypicalOrders;

public class DateUtilTest {


    @Test
    public void testExtractMonth() {
        Order testOrder = TypicalOrders.ORDERONESTATS;
        assertEquals(testOrder.getSchedule().get().getCalendar().get(Calendar.MONTH), DateUtil.extractMonth(testOrder));
    }

    @Test
    public void testExtractYear() {
        Order testOrder = TypicalOrders.ORDERONESTATS;
        assertEquals(testOrder.getSchedule().get().getCalendar().get(Calendar.YEAR), DateUtil.extractYear(testOrder));
    }

    @Test
    public void testExtractListOfYearMonth() {
        TestListOfYearMonthStub testStub = new TestListOfYearMonthStub();
        List<Calendar> test = DateUtil.getListOfYearMonth(TypicalStatsPayload.DEFAULT_STATS_PAYLOAD_GRAPH);
        List<String> expectedMonths = testStub.getTestMonths();
        List<String> expectedYears = testStub.getTestYears();
        for (int i = 0; i < test.size(); i++) {
            String testMonth = test.get(i).getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            String testYear = String.valueOf(test.get(i).get(Calendar.YEAR));
            assertEquals(expectedMonths.get(i), testMonth);
            assertEquals(expectedYears.get(i), testYear);
        }
    }

    /**
     * stub class to help test the extraction
     */
    private static class TestListOfYearMonthStub {
        private final List<String> test1Month;
        private final List<String> test1Year;

        TestListOfYearMonthStub() {
            test1Month = new ArrayList<>();
            test1Year = new ArrayList<>();
            test1Month.add("Jan");
            test1Month.add("Feb");
            test1Month.add("Mar");
            test1Month.add("Apr");
            test1Month.add("May");
            test1Month.add("Jun");
            test1Month.add("Jul");
            test1Month.add("Aug");
            test1Month.add("Sep");
            test1Month.add("Oct");
            test1Month.add("Nov");
            test1Month.add("Dec");

            for (int i = 0; i < 12; i++) {
                test1Year.add("2019");
            }
        }

        public List<String> getTestMonths() {
            return this.test1Month;
        }

        public List<String> getTestYears() {
            return this.test1Year;
        }


    }

}
