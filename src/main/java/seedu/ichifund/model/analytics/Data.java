package seedu.ichifund.model.analytics;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.analytics.exceptions.FieldNotFoundException;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.transaction.Category;

/**
 * Represents a data field in an analytics report.
 * Guarantees: immutable
 */
public class Data implements Comparable<Data> {

    private final String description;
    private final Amount amount;
    private final Optional<Year> year;
    private final Optional<Month> month;
    private final Optional<Day> day;
    private final Optional<Category> category;

    /**
     * Constructs a {@code Data}.
     */
    public Data(String description, Amount amount, Optional<Year> year, Optional<Month> month, Optional<Day> day,
                Optional<Category> category) {
        requireNonNull(amount);
        this.description = description;
        this.amount = amount;
        this.year = year;
        this.month = month;
        this.day = day;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    public Year getYear() {
        return year.orElseThrow(FieldNotFoundException::new);
    }

    public Month getMonth() {
        return month.orElseThrow(FieldNotFoundException::new);
    }

    public Day getDay() {
        return day.orElseThrow(FieldNotFoundException::new);
    }

    public Category getCategory() {
        return category.orElseThrow(FieldNotFoundException::new);
    }

    /**
     * Returns true if both data objects contain the same data fields.
     * This defines a stronger notion of equality between two data objects.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Data)) {
            return false;
        }

        Data otherData = (Data) other;
        return otherData.getDescription().equals(getDescription())
                && otherData.getAmount().equals(getAmount())
                && otherData.getYear().equals(getYear())
                && otherData.getMonth().equals(getMonth())
                && otherData.getDay().equals(getDay())
                && otherData.getCategory().equals(getCategory());
    }

    @Override
    public int compareTo(Data other) {
        return other.getAmount().compareTo(getAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, year, month, day, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Description: ")
                .append(getDescription());

        builder.append(" Amount: ")
                .append(getAmount());

        if (year.isPresent()) {
            builder.append(" Year: ")
                    .append(getYear());
        }

        if (month.isPresent()) {
            builder.append(" Month: ")
                    .append(getMonth());
        }

        if (day.isPresent()) {
            builder.append(" Day: ")
                    .append(getDay());
        }

        if (category.isPresent()) {
            builder.append(" Category: ")
                    .append(getCategory());
        }

        return builder.toString();
    }
}
