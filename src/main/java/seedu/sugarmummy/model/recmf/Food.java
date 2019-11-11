package seedu.sugarmummy.model.recmf;

import static seedu.sugarmummy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Food in the food recommendation list. Guarantees: details are present and not null, field values are
 * validated, immutable.
 */
public class Food {

    private final FoodName foodName;
    private final Calorie calorie;
    private final Gi gi;
    private final Sugar sugar;
    private final Fat fat;
    private final FoodType foodType;

    public Food(FoodName foodName, Calorie calorie, Gi gi, Sugar sugar, Fat fat, FoodType foodType) {
        requireAllNonNull(foodName, calorie, gi, sugar, fat);
        this.foodName = foodName;
        this.calorie = calorie;
        this.gi = gi;
        this.sugar = sugar;
        this.fat = fat;
        this.foodType = foodType;
    }

    public FoodName getFoodName() {
        return foodName;
    }

    public Calorie getCalorie() {
        return calorie;
    }

    public Gi getGi() {
        return gi;
    }

    public Sugar getSugar() {
        return sugar;
    }

    public Fat getFat() {
        return fat;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    /**
     * Returns true if both foods have the same food names.
     */
    public boolean isSameFood(Food other) {
        if (this == other) {
            return true;
        }

        return this.foodName.equals(other.foodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodName, calorie, gi, sugar, fat, foodType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Food)) {
            return false;
        }
        Food other = (Food) obj;
        return foodName.equals(other.foodName)
                && foodType.equals(other.foodType)
                && calorie.equals(other.calorie)
                && gi.equals(other.gi)
                && sugar.equals(other.sugar)
                && fat.equals(other.fat);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getFoodName())
                .append(" Type: ")
                .append(getFoodType().getTypeName())
                .append(" Calorie: ")
                .append(getCalorie())
                .append(" GI: ")
                .append(getGi())
                .append(" Sugar: ")
                .append(getSugar())
                .append(" Fat: ")
                .append(getFat());
        return builder.toString();
    }
}
