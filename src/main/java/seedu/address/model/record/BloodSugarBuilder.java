package seedu.address.model.record;

import seedu.address.model.DateTime;

/**
 * A utility class to help with building BloodSugar objects.
 */
public class BloodSugarBuilder {

    public static final String DEFAULT_DATETIME = "2019-01-04 09:09";
    public static final String DEFAULT_CONCENTRATION = "1.23";

    private DateTime dateTime;
    private Concentration concentration;

    /**
     * Initializes the BloodSugarBuilder with the default data.
     */
    public BloodSugarBuilder() {
        this.dateTime = new DateTime(DEFAULT_DATETIME);
        this.concentration = new Concentration(DEFAULT_CONCENTRATION);
    }

    /**
     * Initializes the BloodSugarBuilder with the data of {@code bloodSugarToCopy}.
     */
    public BloodSugarBuilder(BloodSugar bloodSugarToCopy) {
        this.dateTime = bloodSugarToCopy.getDateTime();
        this.concentration = bloodSugarToCopy.getConcentration();
    }

    /**
     * Sets the {@code DateTime} of the {@code BloodSugar} that we are building.
     */
    public BloodSugarBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }

    /**
     * Sets the {@code Concentration} of the {@code BloodSugar} that we are building.
     */
    public BloodSugarBuilder withConcentration(String concentration) {
        this.concentration = new Concentration(concentration);
        return this;
    }

    public BloodSugar build() {
        return new BloodSugar(concentration, dateTime);
    }

}
