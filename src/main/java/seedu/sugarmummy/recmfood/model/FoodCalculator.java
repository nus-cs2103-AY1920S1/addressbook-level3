package seedu.sugarmummy.recmfood.model;

import java.util.List;

/**
 * Handles the arithmetic of food nutrition values.
 */
public class FoodCalculator {

    public static Calorie getCalorieSum(List<Food> foodItems) {
        double sum = foodItems.stream().mapToDouble(food -> food.getCalorie().getNumericalValue()).sum();
        return new Calorie(Double.toString(sum));
    }

    public static Gi getGiSum(List<Food> foodItems) {
        double sum = foodItems.stream().mapToDouble(food -> food.getGi().getNumericalValue()).sum();
        return new Gi(Double.toString(sum));
    }

    public static Sugar getSugarSum(List<Food> foodItems) {
        double sum = foodItems.stream().mapToDouble(food -> food.getSugar().getNumericalValue()).sum();
        return new Sugar(Double.toString(sum));
    }

    public static Fat getFatSum(List<Food> foodItems) {
        double sum = foodItems.stream().mapToDouble(food -> food.getFat().getNumericalValue()).sum();
        return new Fat(Double.toString(sum));
    }
}
