package seedu.savenus.model.purchase;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import seedu.savenus.model.food.Food;
import seedu.savenus.model.util.TimeFormatter;

/**
 * Represents a Purchase in the menu.
 * Guarantees: immutable.
 */
public class Purchase {
    // Identity fields
    private final Food foodPurchased;
    private final LocalDateTime timeOfPurchase;

    public Purchase(Food foodPurchased) {
        this.foodPurchased = foodPurchased;
        timeOfPurchase = LocalDateTime.now();
    }

    public Food getPurchasedFood() {
        return foodPurchased;
    }

    public LocalDateTime getTimeOfPurchase() {
        return timeOfPurchase;
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
        return otherPurchase.getPurchasedFood().equals(getPurchasedFood())
                && otherPurchase.getTimeOfPurchase().isEqual(getTimeOfPurchase());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(foodPurchased, timeOfPurchase);
    }

    @Override
    public String toString() {
        return "Bought " + getPurchasedFood().getName() + " at "
                + TimeFormatter.format(timeOfPurchase.until(LocalDateTime.now(), ChronoUnit.MILLIS));
    }
}
