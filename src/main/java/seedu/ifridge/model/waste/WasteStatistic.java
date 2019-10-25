package seedu.ifridge.model.waste;

import java.time.LocalDate;

import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.UniqueWasteList;

/**
 * The WasteStatistic for given waste list.
 */
public class WasteStatistic {

    public static final float WEIGHTS_CURRENT_MONTH = 0.4f;
    public static final float WEIGHTS_PREVIOUS_ONE_MONTH = 0.3f;
    public static final float WEIGHTS_PREVIOUS_TWO_MONTH = 0.2f;
    public static final float WEIGHTS_PREVIOUS_THREE_MONTH = 0.1f;

    private float totalWeight;
    private float totalVolume;
    private float totalQuantity;

    public WasteStatistic(float totalWeight, float totalVolume, float totalQuantity) {
        this.totalWeight = totalWeight;
        this.totalVolume = totalVolume;
        this.totalQuantity = totalQuantity;
    }

    public static WasteStatistic getWasteStatistic(UniqueWasteList wasteList) {
        float weight = 0;
        float volume = 0;
        float quantity = 0;

        for (GroceryItem wasteItem : wasteList) {
            Amount amount = wasteItem.getAmount();
            weight += Amount.getAmountInKg(amount);
            volume += Amount.getAmountInLitre(amount);
            quantity += Amount.getAmountInUnit(amount);
        }

        return new WasteStatistic(weight, volume, quantity);
    }

    /**
     * Predicts this month's current wastage statistic and returns a WasteStatistic object
     *
     * @param thisMonth WasteStatistic for the current month
     * @param previousOneMonth WasteStatistic for the previous month
     * @param previousTwoMonth WasteStatistic for the month which was two months before now
     * @param previousThreeMonth WasteStatistic for the month which was three months before now
     * @return The weighted waste statistics
     */
    public static WasteStatistic getPredictedWasteStatistic(WasteStatistic thisMonth,
                                                            WasteStatistic previousOneMonth,
                                                            WasteStatistic previousTwoMonth,
                                                            WasteStatistic previousThreeMonth) {
        float weight = thisMonth.getTotalWeight();
        float volume = thisMonth.getTotalVolume();
        float quantity = thisMonth.getTotalQuantity();

        LocalDate today = LocalDate.now();
        int daysPassed = today.getDayOfMonth();
        int daysInMonth = today.lengthOfMonth();
        float scalingFactor = daysInMonth / daysPassed;

        WasteStatistic currentMonth = new WasteStatistic(weight * scalingFactor,
                volume * scalingFactor, quantity * scalingFactor);
        return getWeightedStatistics(currentMonth, previousOneMonth,
                previousTwoMonth, previousThreeMonth);
    }

    public static WasteStatistic getWeightedStatistics(WasteStatistic currentMonth, WasteStatistic previousOneMonth,
                                                 WasteStatistic previousTwoMonth, WasteStatistic previousThreeMonth) {
        float weightedWeight = WEIGHTS_CURRENT_MONTH * currentMonth.getTotalWeight()
                + WEIGHTS_PREVIOUS_ONE_MONTH * previousOneMonth.getTotalWeight()
                + WEIGHTS_PREVIOUS_TWO_MONTH * previousTwoMonth.getTotalWeight()
                + WEIGHTS_PREVIOUS_THREE_MONTH * previousThreeMonth.getTotalWeight();

        float weightedVolume = WEIGHTS_CURRENT_MONTH * currentMonth.getTotalVolume()
                + WEIGHTS_PREVIOUS_ONE_MONTH * previousOneMonth.getTotalVolume()
                + WEIGHTS_PREVIOUS_TWO_MONTH * previousTwoMonth.getTotalVolume()
                + WEIGHTS_PREVIOUS_THREE_MONTH * previousThreeMonth.getTotalVolume();

        float weightedUnit = WEIGHTS_CURRENT_MONTH * currentMonth.getTotalQuantity()
                + WEIGHTS_PREVIOUS_ONE_MONTH * previousOneMonth.getTotalQuantity()
                + WEIGHTS_PREVIOUS_TWO_MONTH * previousTwoMonth.getTotalQuantity()
                + WEIGHTS_PREVIOUS_THREE_MONTH * previousThreeMonth.getTotalQuantity();

        return new WasteStatistic(weightedWeight, weightedVolume, weightedUnit);
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public float getTotalVolume() {
        return totalVolume;
    }

    public float getTotalQuantity() {
        return totalQuantity;
    }

    @Override
    public String toString() {
        return "Weight = " + getTotalWeight()
                + ", Volume = " + getTotalVolume()
                + ", Quantity = " + getTotalQuantity();
    }
}
