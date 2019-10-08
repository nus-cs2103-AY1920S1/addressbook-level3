package calofit.model.meal;

import calofit.model.dish.Dish;
import calofit.model.util.Timestamp;

import java.util.Objects;

public class Meal implements Comparable<Meal> {
    private Dish dish;
    private Timestamp timestamp;

    public Meal(Dish dish, Timestamp timestamp) {
        this.dish = dish;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(dish, meal.dish) &&
                Objects.equals(timestamp, meal.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dish, timestamp);
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Meal meal) {
        return timestamp.compareTo(meal.timestamp);
    }
}
