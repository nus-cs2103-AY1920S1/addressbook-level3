package seedu.sugarmummy.model.records;

import static java.util.Objects.requireNonNull;

import seedu.sugarmummy.model.time.DateTime;

/**
 * Represents a Bmi record in the record book. Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Bmi extends Record {
    public static final String MESSAGE_CONSTRAINTS = "Bmi can take any positive values,"
            + " and it should not be blank";

    private final Height height;
    private final Weight weight;
    private final Double bmi;

    public Bmi(Height height, Weight weight, DateTime dateTime) {
        super(dateTime);
        requireNonNull(height);
        requireNonNull(weight);
        this.height = height;
        this.weight = weight;
        this.bmi = weight.getWeight() / (height.getHeight() * height.getHeight());
    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    public Double getBmi() {
        return bmi;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BMI record :")
                .append(" Height: ")
                .append(height)
                .append(" Weight: ")
                .append(weight);
        return builder.toString() + " " + super.toString();
    }
}
