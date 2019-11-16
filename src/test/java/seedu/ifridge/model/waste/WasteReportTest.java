package seedu.ifridge.model.waste;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_CURRENT_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_LAST_MONTH;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_THREE_MONTHS_AGO;
import static seedu.ifridge.logic.commands.WasteListCommandTestUtil.WASTE_MONTH_TWO_MONTHS_AGO;
import static seedu.ifridge.model.waste.WasteStatisticTest.WASTE_STATISTIC_CURRENT_MONTH;
import static seedu.ifridge.model.waste.WasteStatisticTest.WASTE_STATISTIC_LAST_MONTH;
import static seedu.ifridge.model.waste.WasteStatisticTest.WASTE_STATISTIC_THREE_MONTHS_AGO;
import static seedu.ifridge.model.waste.WasteStatisticTest.WASTE_STATISTIC_TWO_MONTHS_AGO;
import static seedu.ifridge.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class WasteReportTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WasteReport(null));
    }

    @Test
    public void iteratorTest() {
        Map<WasteMonth, WasteStatistic> historicalData = Map.ofEntries(
                entry(new WasteMonth(LocalDate.now()), WASTE_STATISTIC_CURRENT_MONTH),
                entry(new WasteMonth(LocalDate.now()).minusWasteMonth(1), WASTE_STATISTIC_LAST_MONTH),
                entry(new WasteMonth(LocalDate.now()).minusWasteMonth(2), WASTE_STATISTIC_TWO_MONTHS_AGO),
                entry(new WasteMonth(LocalDate.now()).minusWasteMonth(3), WASTE_STATISTIC_THREE_MONTHS_AGO)
        );
        WasteReport wr = new WasteReport(historicalData);
        for (Map.Entry<WasteMonth, WasteStatistic> entry : wr) {
            assertNotNull(entry);
        }
    }

    @Test
    public void equalsTest() {
        Map<WasteMonth, WasteStatistic> historicalData = Map.ofEntries(
                entry(new WasteMonth(LocalDate.now()), WASTE_STATISTIC_CURRENT_MONTH),
                entry(new WasteMonth(LocalDate.now()).minusWasteMonth(1), WASTE_STATISTIC_LAST_MONTH),
                entry(new WasteMonth(LocalDate.now()).minusWasteMonth(2), WASTE_STATISTIC_TWO_MONTHS_AGO),
                entry(new WasteMonth(LocalDate.now()).minusWasteMonth(3), WASTE_STATISTIC_THREE_MONTHS_AGO)
        );
        WasteReport wr1 = new WasteReport(historicalData);
        Map<WasteMonth, WasteStatistic> historicalData2 = Map.ofEntries(
                entry(WASTE_MONTH_CURRENT_MONTH, WASTE_STATISTIC_CURRENT_MONTH),
                entry(WASTE_MONTH_LAST_MONTH, WASTE_STATISTIC_LAST_MONTH),
                entry(WASTE_MONTH_TWO_MONTHS_AGO, WASTE_STATISTIC_TWO_MONTHS_AGO),
                entry(WASTE_MONTH_THREE_MONTHS_AGO, WASTE_STATISTIC_THREE_MONTHS_AGO)
        );
        WasteReport wr2 = new WasteReport(historicalData2);
        assertEquals(wr1, wr2);
    }

}
