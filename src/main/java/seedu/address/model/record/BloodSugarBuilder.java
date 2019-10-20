package seedu.address.model.record;

import seedu.address.model.DateTime;

/**
 * A utility class to help with building Food objects.
 */
public class BloodSugarBuilder {

    public static final String DEFAULT_DATETIME = "2019-01-04 09:09";
    public static final String DEFAULT_CONCENTRATION = "1.23";

    private DateTime dateTime;
    private Concentration concentration;

    /**
     * Initializes the FoodBuilder with the default data.
     */
    public BloodSugarBuilder() {
        this.dateTime = new DateTime(DEFAULT_DATETIME);
        this.concentration = new Concentration(DEFAULT_CONCENTRATION);
    }

    /**
     * Initializes the FoodBuilder with the data of {@code foodToCopy}.
     */
    public BloodSugarBuilder(BloodSugar bloodSugarToCopy) {
        this.dateTime = bloodSugarToCopy.getDateTime();
        this.concentration = bloodSugarToCopy.getConcentration();
    }

    /**
     * Sets the {@code FoodName} of the {@code Food} that we are building.
     */
    public BloodSugarBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Calorie} of the {@code Food} that we are building.
     */
    public BloodSugarBuilder withConcentration(String concentration) {
        this.concentration = new Concentration(concentration);
        return this;
    }

    public BloodSugar build() {
        return new BloodSugar(concentration, dateTime);
    }

}
