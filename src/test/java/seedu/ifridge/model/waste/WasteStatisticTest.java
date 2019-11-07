package seedu.ifridge.model.waste;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.ifridge.testutil.Assert.assertThrows;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_CURRENT_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_LAST_MONTH;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_THREE_MONTHS_AGO;
import static seedu.ifridge.testutil.TypicalWasteArchive.WASTE_LIST_TWO_MONTHS_AGO;

import java.util.List;

import org.junit.jupiter.api.Test;

public class WasteStatisticTest {

    public static final WasteStatistic WASTE_STATISTIC_CURRENT_MONTH =
            new WasteStatistic(0.0f, 0.0f, 14.0f);
    public static final WasteStatistic WASTE_STATISTIC_LAST_MONTH =
            new WasteStatistic(0.7f, 0.0f, 5.0f);
    public static final WasteStatistic WASTE_STATISTIC_TWO_MONTHS_AGO =
            new WasteStatistic(0.4f, 0.4f, 1.0f);
    public static final WasteStatistic WASTE_STATISTIC_THREE_MONTHS_AGO =
            new WasteStatistic(0.5f, 2.2f, 1.0f);


    @Test
    public void constructor_invalidValues_throwsIllegalArgumentException() {
        float invalidWeightValue = -2.5f;
        float invalidVolumeValue = -1.7f;
        float invalidQuantityValue = -3.5f;
        float validWeightValue = 2.5f;
        float validVolumeValue = 1.7f;
        float validQuantityValue = 3.5f;
        assertThrows(IllegalArgumentException.class, () ->
                new WasteStatistic(invalidWeightValue, validVolumeValue, validQuantityValue));
        assertThrows(IllegalArgumentException.class, () ->
                new WasteStatistic(validWeightValue, invalidVolumeValue, validQuantityValue));
        assertThrows(IllegalArgumentException.class, () ->
                new WasteStatistic(validWeightValue, validVolumeValue, invalidQuantityValue));
    }

    @Test
    public void constructor_valid() {
        float validWeight = 7.6f;
        float validVolume = 12.2f;
        float validQuantity = 0.0f;
        assertTrue(WasteStatistic.isValidStatistic(validWeight, validVolume, validQuantity));
    }

    @Test
    void getWasteStatistic() {
        assertEquals(WASTE_STATISTIC_CURRENT_MONTH,
                WasteStatistic.getWasteStatistic(WASTE_LIST_CURRENT_MONTH.getIterableWasteList()));
        assertEquals(WASTE_STATISTIC_LAST_MONTH,
                WasteStatistic.getWasteStatistic(WASTE_LIST_LAST_MONTH.getIterableWasteList()));
        assertEquals(WASTE_STATISTIC_TWO_MONTHS_AGO,
                WasteStatistic.getWasteStatistic(WASTE_LIST_TWO_MONTHS_AGO.getIterableWasteList()));
        assertEquals(WASTE_STATISTIC_THREE_MONTHS_AGO,
                WasteStatistic.getWasteStatistic(WASTE_LIST_THREE_MONTHS_AGO.getIterableWasteList()));
    }

    @Test
    void getWeightedStatistics() {
        WasteStatistic currentWasteStatistic =
                WasteStatistic.interpolateCurrentWasteStatistic(WASTE_STATISTIC_CURRENT_MONTH);

        // 1 month data
        assertEquals(currentWasteStatistic,
                WasteStatistic.getWeightedStatistics(WASTE_LIST_CURRENT_MONTH, List.of()));

        // 2 months data
        List<WasteStatistic> statisticsTwoMonths = List.of(currentWasteStatistic, WASTE_STATISTIC_LAST_MONTH);
        WasteStatistic ws2months = WasteStatistic.predictUsingTwoMonths(statisticsTwoMonths);
        assertEquals(ws2months,
                WasteStatistic.getWeightedStatistics(WASTE_LIST_CURRENT_MONTH, List.of(WASTE_LIST_LAST_MONTH)));

        // 3 months data
        List<WasteStatistic> statisticsThreeMonths = List.of(currentWasteStatistic, WASTE_STATISTIC_LAST_MONTH,
                WASTE_STATISTIC_TWO_MONTHS_AGO);
        WasteStatistic ws3months = WasteStatistic.predictUsingThreeMonths(statisticsThreeMonths);
        assertEquals(ws3months, WasteStatistic.getWeightedStatistics(WASTE_LIST_CURRENT_MONTH,
                List.of(WASTE_LIST_LAST_MONTH, WASTE_LIST_TWO_MONTHS_AGO)));

        // 4 months data
        List<WasteStatistic> statisticsFourMonths = List.of(currentWasteStatistic, WASTE_STATISTIC_LAST_MONTH,
                WASTE_STATISTIC_TWO_MONTHS_AGO, WASTE_STATISTIC_THREE_MONTHS_AGO);
        WasteStatistic ws4months = WasteStatistic.predictUsingFourMonths(statisticsFourMonths);
        assertEquals(ws4months, WasteStatistic.getWeightedStatistics(WASTE_LIST_CURRENT_MONTH,
                List.of(WASTE_LIST_LAST_MONTH, WASTE_LIST_TWO_MONTHS_AGO, WASTE_LIST_THREE_MONTHS_AGO)));
    }

    @Test
    void testEquals() {
        WasteStatistic wasteStatistic = new WasteStatistic(1.0f, 2.0f, 3.0f);
        WasteStatistic sameWasteStatistic = new WasteStatistic(1.00f, 2.00f, 3.00f);
        WasteStatistic diffWasteStatistic = new WasteStatistic(5.0f, 4.0f, 3.0f);
        assertTrue(wasteStatistic.equals(sameWasteStatistic));
        assertFalse(wasteStatistic.equals(diffWasteStatistic));

        // Different object type
        assertFalse(wasteStatistic.equals("not a valid waste statistic"));
    }

    @Test
    void testToString() {
        WasteStatistic wasteStatistic = new WasteStatistic(1.5f, 3.2f, 1.12345f);
        String displayed = "Weight = 1.500 kg, Volume = 3.200 litres, Quantity = 1.123 unit(s)";
        assertEquals(displayed, wasteStatistic.toString());
    }
}
