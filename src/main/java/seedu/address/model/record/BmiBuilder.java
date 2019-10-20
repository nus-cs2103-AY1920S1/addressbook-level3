package seedu.address.model.record;

import seedu.address.model.DateTime;

/**
 * A utility class to help with building Food objects.
 */
public class BmiBuilder {

    public static final String DEFAULT_DATETIME = "2019-01-01 09:09";
    public static final String DEFAULT_HEIGHT = "34.0";
    public static final String DEFAULT_WEIGHT = "10.0";

    private DateTime dateTime;
    private Height height;
    private Weight weight;

    /**
     * Initializes the FoodBuilder with the default data.
     */
    public BmiBuilder() {
        this.dateTime = new DateTime(DEFAULT_DATETIME);
        this.height = new Height(DEFAULT_HEIGHT);
        this.weight = new Weight(DEFAULT_WEIGHT);
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public BmiBuilder(Bmi bmiToCopy) {
        this.dateTime = bmiToCopy.getDateTime();
        this.height = bmiToCopy.getHeight();
        this.weight = bmiToCopy.getWeight();
    }

    /**
     * Sets the {@code FoodName} of the {@code Food} that we are building.
     */
    public BmiBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code Food} that we are building.
     */
    public BmiBuilder withHeight(String height) {
        this.height = new Height(height);
        return this;
    }

    /**
     * Sets the {@code Gi} of the {@code Food} that we are building.
     */
    public BmiBuilder withWeight(String weight) {
        this.weight = new Weight(weight);
        return this;
    }

    public Bmi build() {
        return new Bmi(height, weight, dateTime);
    }

}
