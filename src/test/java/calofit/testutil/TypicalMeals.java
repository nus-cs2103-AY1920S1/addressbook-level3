package calofit.testutil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import calofit.model.meal.Meal;
import calofit.model.meal.MealLog;
import calofit.model.util.Timestamp;

public class TypicalMeals {

    public static final Meal SPAGHETTI = new Meal(
            TypicalDishes.SPAGHETTI,
            new Timestamp(LocalDateTime.now()));
    public static final Meal MUSHROOM_SOUP = new Meal(
            TypicalDishes.MUSHROOM_SOUP,
            new Timestamp(LocalDateTime.now()));
    public static final Meal CHICKEN_RICE = new Meal(
            TypicalDishes.CHICKEN_RICE,
            new Timestamp(LocalDateTime.now()));
    public static final Meal NASI_LEMAK = new Meal(
            TypicalDishes.NASI_LEMAK,
            new Timestamp(LocalDateTime.now().minusMonths(1)));
    public static final Meal CHEESE_BAKED_RICE = new Meal(
            TypicalDishes.CHEESE_BAKED_RICE,
            new Timestamp(LocalDateTime.now().minusMonths(6)));
    public static final Meal APPLE_PIE = new Meal(
            TypicalDishes.APPLE_PIE,
            new Timestamp(LocalDateTime.now().minusYears(1)));
    public static final Meal CHENDOL = new Meal(
            TypicalDishes.CHENDOL,
            new Timestamp(LocalDateTime.now().minusYears(1)));


    public static ObservableList<Meal> getTypicalMealsObservableList() {
        return FXCollections.observableList(new ArrayList<>(Arrays.asList(
                SPAGHETTI, MUSHROOM_SOUP, CHICKEN_RICE, NASI_LEMAK, CHEESE_BAKED_RICE, APPLE_PIE, CHENDOL)));
    }

    public static ObservableList<Meal> getTypicalMealsObservableListOfSameMonth() {
        return FXCollections.observableList(new ArrayList<>(Arrays.asList(
                SPAGHETTI, MUSHROOM_SOUP, CHICKEN_RICE)));
    }

    public static MealLog getTypicalMealLog() {
        MealLog log = new MealLog();
        for (Meal meal : getTypicalMealsObservableList()) {
            log.addMeal(meal);
        }
        return log;
    }

}
