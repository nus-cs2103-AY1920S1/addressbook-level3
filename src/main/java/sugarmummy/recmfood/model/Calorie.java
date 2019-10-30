package sugarmummy.recmfood.model;

/**
 * Specifies the nutrition value of Calorie.
 */
public class Calorie extends NutritionValue {

    private static final double HIGH_CALORIE = 700;

    /**
     * Constructs a {@code Calorie}.
     *
     * @param calorieValue a valid calorie value
     */
    public Calorie(String calorieValue) {
        super(calorieValue);
    }

    @Override
    public boolean isInDangerousRange() {
        return getNumericalValue() >= HIGH_CALORIE;
    }

    @Override
    public String getWarningMessage() {
        return super.getWarningMessage("Calorie", HIGH_CALORIE);
    }
}

