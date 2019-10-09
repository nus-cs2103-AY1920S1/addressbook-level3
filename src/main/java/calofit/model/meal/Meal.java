package calofit.model.meal;

import java.util.Objects;

import calofit.model.dish.Dish;
import calofit.model.util.Timestamp;

/**
 * Represents a meal entry in the meal log.
 */
public class Meal implements Comparable<Meal> {
    private Dish dish;
    private Timestamp timestamp;

    /**
     * Constructs a meal entry from a {@link Dish} and {@link Timestamp}.
     * @param dish Dish eaten
     * @param timestamp Time of meal
     */
    public Meal(Dish dish, Timestamp timestamp) {
        this.dish = dish;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Meal meal = (Meal) o;
        return Objects.equals(dish, meal.dish)
                && Objects.equals(timestamp, meal.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish, timestamp);
    }

    /**
     * Returns the dish eaten.
     * @return Dish eaten
     */
    public Dish getDish() {
        return dish;
    }

    /**
     * Sets the dish eaten.
     * @param dish Dish eaten
     */
    public void setDish(Dish dish) {
        this.dish = dish;
    }

    /**
     * Gets the time of meal.
     * @return Time of meal
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the time of meal.
     *
     * WARNING! Editing timestamp may cause ordering to change.
     * @param timestamp Time of meal
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Meal meal) {
        return timestamp.compareTo(meal.timestamp);
    }
}
