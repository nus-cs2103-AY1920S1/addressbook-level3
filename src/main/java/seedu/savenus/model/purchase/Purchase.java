package seedu.savenus.model.purchase;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.util.TimeFormatter;

/**
 * Represents a Purchase in the menu.
 * Guarantees: immutable.
 */
public class Purchase {
    // Identity fields
    private final Name foodPurchasedName;
    private final Price foodPurchasedPrice;
    private final TimeOfPurchase timeOfPurchase;

    public Purchase(Name foodPurchasedName, Price foodPurchasedPrice) {
        this.foodPurchasedName = foodPurchasedName;
        this.foodPurchasedPrice = foodPurchasedPrice;
        timeOfPurchase = TimeOfPurchase.generate();
    }

    public Purchase(Name foodPurchasedName, Price foodPurchasedPrice, TimeOfPurchase timeOfPurchase) {
        this.foodPurchasedName = foodPurchasedName;
        this.foodPurchasedPrice = foodPurchasedPrice;
        this.timeOfPurchase = timeOfPurchase;
    }

    public Name getPurchasedFoodName() {
        return foodPurchasedName;
    }

    public Price getPurchasedFoodPrice() {
        return foodPurchasedPrice;
    }

    public long getTimeOfPurchaseInMillisSinceEpoch() {
        return timeOfPurchase.getTimeOfPurchaseInMillisSinceEpoch();
    }

    public String getTimeAgoString() {
        return TimeFormatter.format((
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        - getTimeOfPurchaseInMillisSinceEpoch()));
    }
    /**
     * Returns true if both purchase have the same foodPurchased and timeOfPurchase fields.
     * This defines a stronger notion of equality between two purchases.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Purchase)) {
            return false;
        }

        Purchase otherPurchase = (Purchase) other;
        return otherPurchase.getPurchasedFoodName().equals(getPurchasedFoodName())
                && otherPurchase.getPurchasedFoodPrice().equals(getPurchasedFoodPrice())
                && otherPurchase.getTimeOfPurchaseInMillisSinceEpoch() == getTimeOfPurchaseInMillisSinceEpoch();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(foodPurchasedName, foodPurchasedPrice, timeOfPurchase);
    }

    @Override
    public String toString() {
        return getPurchasedFoodName() + " for $" + getPurchasedFoodPrice();
    }
}
