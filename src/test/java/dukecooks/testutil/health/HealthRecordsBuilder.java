package dukecooks.testutil.health;

import dukecooks.model.health.HealthRecords;
import dukecooks.model.health.components.Record;

/**
 * A utility class to help with building HealthRecords objects.
 * Example usage: <br>
 *     {@code HealthRecords dc = new HealthRecordsBuilder().withRecord("Fried", "Chicken").build();}
 */
public class HealthRecordsBuilder {

    private HealthRecords healthRecords;

    public HealthRecordsBuilder() {
        healthRecords = new HealthRecords();
    }

    public HealthRecordsBuilder(HealthRecords healthRecords) {
        this.healthRecords = healthRecords;
    }

    /**
     * Adds a new {@code Record} to the {@code HealthRecords} that we are building.
     */
    public HealthRecordsBuilder withRecord(Record record) {
        healthRecords.addRecord(record);
        return this;
    }

    public HealthRecords build() {
        return healthRecords;
    }
}
