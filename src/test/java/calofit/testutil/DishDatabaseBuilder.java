package calofit.testutil;

import calofit.model.dish.DishDatabase;
import calofit.model.dish.Dish;

/**
 * A utility class to help with building {@link DishDatabase} objects.
 * Example usage: <br>
 *     {@code DishDatabase ab = new DishDatabaseBuilder().withPerson("John", "Doe").build();}
 */
public class DishDatabaseBuilder {

    private DishDatabase dishDatabase;

    public DishDatabaseBuilder() {
        dishDatabase = new DishDatabase();
    }

    public DishDatabaseBuilder(DishDatabase dishDatabase) {
        this.dishDatabase = dishDatabase;
    }

    /**
     * Adds a new {@code Dish} to the {@code DishDatabase} that we are building.
     */
    public DishDatabaseBuilder withDish(Dish dish) {
        dishDatabase.addDish(dish);
        return this;
    }

    public DishDatabase build() {
        return dishDatabase;
    }
}
