package seedu.address.model.waste;

import java.time.LocalDate;
import java.time.LocalDateTime;

import seedu.address.model.food.Amount;
import seedu.address.model.food.GroceryItem;
import seedu.address.model.food.UniqueFoodList;

public class WasteStatistic {

    private float totalWeight;
    private float totalVolume;
    private float totalQuantity;

    public WasteStatistic(float totalWeight, float totalVolume, float totalQuantity){
        this.totalWeight = totalWeight;
        this.totalVolume = totalVolume;
        this.totalQuantity = totalQuantity;
    }

    public static WasteStatistic getWasteStatistic(UniqueFoodList wasteList) {
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

    public WasteStatistic getPredictedWasteStatistic(UniqueFoodList wasteList) {
        WasteStatistic currentWasteStatistic = getWasteStatistic(wasteList);
        float weight = currentWasteStatistic.getTotalWeight();
        float volume = currentWasteStatistic.getTotalVolume();
        float quantity = currentWasteStatistic.getTotalQuantity();

        LocalDate today = LocalDate.now();
        int daysPassed = today.getDayOfMonth();
        int daysInMonth = today.lengthOfMonth();
        float scalingFactor = daysInMonth/daysPassed;

        return new WasteStatistic(weight * scalingFactor, volume * scalingFactor, quantity * scalingFactor);
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

}
