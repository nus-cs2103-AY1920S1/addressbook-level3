package seedu.sugarmummy.recmfood.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles the arithmetic of food nutrition values.
 */
public class FoodCalculator {

    private final List<Food> foodItems;

    public FoodCalculator(List<Food> foodItems) {
        this.foodItems = foodItems;
    }

    public Calorie getCalorieSum() {
        double sum = foodItems.stream().mapToDouble(food -> food.getCalorie().getNumericalValue()).sum();
        return new Calorie(Double.toString(sum));
    }

    public Gi getGiSum() {
        double sum = foodItems.stream().mapToDouble(food -> food.getGi().getNumericalValue()).sum();
        return new Gi(Double.toString(sum));
    }

    public Sugar getSugarSum() {
        double sum = foodItems.stream().mapToDouble(food -> food.getSugar().getNumericalValue()).sum();
        return new Sugar(Double.toString(sum));
    }

    public Fat getFatSum() {
        double sum = foodItems.stream().mapToDouble(food -> food.getFat().getNumericalValue()).sum();
        return new Fat(Double.toString(sum));
    }

}
