package seedu.ifridge.model.waste;

import static seedu.ifridge.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.ifridge.model.ReadOnlyWasteList;
import seedu.ifridge.model.food.Amount;
import seedu.ifridge.model.food.GroceryItem;
import seedu.ifridge.model.food.UniqueWasteList;

/**
 * The WasteStatistic for given waste list.
 */
public class WasteStatistic {

    public static final String MESSAGE_CONSTRAINTS = "All values (weight in kg, volume in litres, quantity in units) "
            + "must be non-negative.";

    private static final float[] WEIGHTS_FOUR_MONTHS = {0.5f, 0.3f, 0.15f, 0.05f};
    private static final float[] WEIGHTS_THREE_MONTHS = {0.6f, 0.35f, 0.15f};
    private static final float[] WEIGHTS_TWO_MONTHS = {0.8f, 0.2f};
    private static final float THRESHOLD = 0.0001f;

    private float totalWeight;
    private float totalVolume;
    private float totalQuantity;

    public WasteStatistic(float totalWeight, float totalVolume, float totalQuantity) {
        checkArgument(isValidStatistic(totalWeight, totalVolume, totalQuantity), MESSAGE_CONSTRAINTS);
        this.totalWeight = totalWeight;
        this.totalVolume = totalVolume;
        this.totalQuantity = totalQuantity;
    }

    /**
     * Calculates the total amount of food wasted in a UniqueWasteList {@link UniqueWasteList}.
     *
     * @param wasteList The {@link UniqueWasteList} which will be iterated through to compute waste statistics.
     * @return A {@link WasteStatistic} object which contains details on the amount of food wastage for the waste list.
     */
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
     * Computes the weighted average of monthly food waste based on current and previous months' food wastes.
     *
     * @param currentWasteList The waste list of the current month of the format {@link ReadOnlyWasteList}.
     * @param pastWasteLists The {@link List} of previous waste lists of the format {@link ReadOnlyWasteList},
     *                       sorted in order of most recent list (excluding the current waste list) at the start.
     *                       It can be of any length, but a maximum of 3 previous months will be used to obtain the
     *                       weighted average.
     * @return The {@link WasteStatistic} of the weighted average of the previous months.
     */
    public static WasteStatistic getWeightedStatistics(ReadOnlyWasteList currentWasteList,
                                                       List<ReadOnlyWasteList> pastWasteLists) {

        List<WasteStatistic> wasteStatistics = new ArrayList<>();
        WasteStatistic currentWastage = getWasteStatistic(currentWasteList.getIterableWasteList());
        WasteStatistic interpolatedCurrentWastage = interpolateCurrentWasteStatistic(currentWastage);
        wasteStatistics.add(interpolatedCurrentWastage);
        for (ReadOnlyWasteList wasteList : pastWasteLists) {
            wasteStatistics.add(getWasteStatistic(wasteList.getIterableWasteList()));
        }

        int numMonths = pastWasteLists.size() + 1;
        switch (numMonths) {
        case 1:
            return interpolatedCurrentWastage;
        case 2:
            return predictUsingTwoMonths(wasteStatistics);
        case 3:
            return predictUsingThreeMonths(wasteStatistics);
        default:
            return predictUsingFourMonths(wasteStatistics);
        }
    }

    /**
     * Predicts the food wastage using 2 months' worth of data.
     *
     * @param wasteStatistics The list of WasteStatistics of the 2 months
     */
    public static WasteStatistic predictUsingTwoMonths(List<WasteStatistic> wasteStatistics) {
        float weightedWeight = 0.0f;
        float weightedVolume = 0.0f;
        float weightedQuantity = 0.0f;
        for (int i = 0; i < 2; i++) {
            weightedWeight += WEIGHTS_TWO_MONTHS[i] * wasteStatistics.get(i).getTotalWeight();
            weightedVolume += WEIGHTS_TWO_MONTHS[i] * wasteStatistics.get(i).getTotalVolume();
            weightedQuantity += WEIGHTS_TWO_MONTHS[i] * wasteStatistics.get(i).getTotalQuantity();
        }
        return new WasteStatistic(weightedWeight, weightedVolume, weightedQuantity);
    }

    /**
     * Predicts the food wastage using 3 months' worth of data.
     * @param wasteStatistics The list of WasteStatistics of the 3 months
     */
    public static WasteStatistic predictUsingThreeMonths(List<WasteStatistic> wasteStatistics) {
        float weightedWeight = 0.0f;
        float weightedVolume = 0.0f;
        float weightedQuantity = 0.0f;
        for (int i = 0; i < 3; i++) {
            weightedWeight += WEIGHTS_THREE_MONTHS[i] * wasteStatistics.get(i).getTotalWeight();
            weightedVolume += WEIGHTS_THREE_MONTHS[i] * wasteStatistics.get(i).getTotalVolume();
            weightedQuantity += WEIGHTS_THREE_MONTHS[i] * wasteStatistics.get(i).getTotalQuantity();
        }
        return new WasteStatistic(weightedWeight, weightedVolume, weightedQuantity);
    }

    /**
     * Predicts the food wastage using 4 months' worth of data.
     *
     * @param wasteStatistics The list of WasteStatistics of the 4 months
     */
    public static WasteStatistic predictUsingFourMonths(List<WasteStatistic> wasteStatistics) {
        float weightedWeight = 0.0f;
        float weightedVolume = 0.0f;
        float weightedQuantity = 0.0f;
        for (int i = 0; i < 4; i++) {
            weightedWeight += WEIGHTS_FOUR_MONTHS[i] * wasteStatistics.get(i).getTotalWeight();
            weightedVolume += WEIGHTS_FOUR_MONTHS[i] * wasteStatistics.get(i).getTotalVolume();
            weightedQuantity += WEIGHTS_FOUR_MONTHS[i] * wasteStatistics.get(i).getTotalQuantity();
        }
        return new WasteStatistic(weightedWeight, weightedVolume, weightedQuantity);
    }

    /**
     * Interpolates the current month's waste statistic by scaling.
     */
    public static WasteStatistic interpolateCurrentWasteStatistic(WasteStatistic thisMonthStatistics) {
        float weight = thisMonthStatistics.getTotalWeight();
        float volume = thisMonthStatistics.getTotalVolume();
        float quantity = thisMonthStatistics.getTotalQuantity();

        LocalDate today = LocalDate.now();
        float daysPassed = (float) today.getDayOfMonth();
        float daysInMonth = (float) today.lengthOfMonth();
        float scalingFactor = daysInMonth / daysPassed;

        return new WasteStatistic(weight * scalingFactor,
                volume * scalingFactor, quantity * scalingFactor);
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

    /**
     * Checks for the validity of a {@link WasteStatistic} object by verifying that none of the values are negative.
     */
    public static boolean isValidStatistic(float totalWeight, float totalVolume, float totalQuantity) {
        return totalWeight >= 0 && totalVolume >= 0 && totalQuantity >= 0;
    }

    /**
     * Compares equality of two {@link WasteStatistic} objects. A threshold is used due to the potential rounding
     * errors arising from floating point computations.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WasteStatistic)) {
            return false;
        }
        WasteStatistic otherWasteStatistic = (WasteStatistic) other;
        return (otherWasteStatistic.getTotalWeight() - this.getTotalWeight() < THRESHOLD)
                && (otherWasteStatistic.getTotalVolume() - this.getTotalVolume() < THRESHOLD)
                && (otherWasteStatistic.getTotalQuantity() - this.getTotalQuantity() < THRESHOLD);
    }

    @Override
    public String toString() {
        String displayFormat = "Weight = %.3f kg, Volume = %.3f litres, Quantity = %.3f unit(s)";
        return String.format(displayFormat, getTotalWeight(), getTotalVolume(), getTotalQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalWeight, totalVolume, totalQuantity);
    }
}
