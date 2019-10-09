package calofit.model.meal;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MealLog {
    private List<Meal> mealLog = new ArrayList<>();
    private ObservableList<Meal> observableMeals = FXCollections.observableList(mealLog);
    private ObservableList<Meal> readOnlyMeals = FXCollections.unmodifiableObservableList(observableMeals);

    public ObservableList<Meal> getMeals() {
        return readOnlyMeals;
    }

    public boolean addMeal(Meal meal) {
        return observableMeals.add(meal);
    }

    public boolean removeMeal(Meal meal) {
        return observableMeals.remove(meal);
    }
}
