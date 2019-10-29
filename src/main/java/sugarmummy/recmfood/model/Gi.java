package sugarmummy.recmfood.model;

/**
 * Specifies the nutrition value of glycemic index(GI).
 */
public class Gi extends NutritionValue {

    private static final double HIGH_GI = 70;

    /**
     * Constructs a {@code GI}.
     *
     * @param giValue a valid glycemic index value
     */
    public Gi(String giValue) {
        super(giValue);
    }

    @Override
    public boolean isInDangerousRange() {
        return getNumericalValue() >= HIGH_GI;
    }

    @Override
    public String getWarningMessage() {
        return super.getWarningMessage("GI", HIGH_GI);
    }
}

