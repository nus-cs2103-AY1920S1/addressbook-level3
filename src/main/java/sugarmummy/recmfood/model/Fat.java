package sugarmummy.recmfood.model;

/**
 * Specifies the nutrition value of Fat.
 */
public class Fat extends NutritionValue {

    private static final double HIGH_FAT = 35;

    /**
     * Constructs a {@code Fat}.
     *
     * @param fatValue a valid fat value
     */
    public Fat(String fatValue) {
        super(fatValue);
    }

    @Override
    public boolean isInDangerousRange() {
        return getNumericalValue() >= HIGH_FAT;
    }

    @Override
    public String getWarningMessage() {
        return super.getWarningMessage("Fat", HIGH_FAT);
    }
}

