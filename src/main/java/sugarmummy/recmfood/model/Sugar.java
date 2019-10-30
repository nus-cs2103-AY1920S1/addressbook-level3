package sugarmummy.recmfood.model;

/**
 * Specifies the nutrition value of Sugar.
 */
public class Sugar extends NutritionValue {

    private static final double HIGH_SUGAR = 25;

    /**
     * Constructs a {@code Sugar}.
     *
     * @param sugarValue a valid sugar value
     */
    public Sugar(String sugarValue) {
        super(sugarValue);
    }

    @Override
    public boolean isInDangerousRange() {
        return getNumericalValue() >= HIGH_SUGAR;
    }

    @Override
    public String getWarningMessage() {
        return super.getWarningMessage("Sugar", HIGH_SUGAR);
    }
}

