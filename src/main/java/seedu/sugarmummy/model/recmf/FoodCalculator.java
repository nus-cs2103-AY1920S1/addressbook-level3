package seedu.sugarmummy.model.recmf;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Handles the arithmetic of food nutrition values.
 */
public class FoodCalculator {

    private static final NumberFormat resultFormatter = new DecimalFormat("0");

    private final List<Food> foodItems;

    public FoodCalculator(List<Food> foodItems) {
        this.foodItems = foodItems;
    }

    public Calorie getCalorieSum() {
        double sum = foodItems.stream().mapToDouble(food -> food.getCalorie().getNumericalValue()).sum();
        return new Calorie(resultFormatter.format(sum));
    }

    public Gi getGiAverage() {
        double average = foodItems.stream().mapToDouble(f -> f.getGi().getNumericalValue()).average().getAsDouble();
        return new Gi(resultFormatter.format(average));
    }

    public Sugar getSugarSum() {
        double sum = foodItems.stream().mapToDouble(food -> food.getSugar().getNumericalValue()).sum();
        return new Sugar(resultFormatter.format(sum));
    }

    public Fat getFatSum() {
        double sum = foodItems.stream().mapToDouble(food -> food.getFat().getNumericalValue()).sum();
        return new Fat(resultFormatter.format(sum));
    }
}
