package seedu.address.model.statistics;

import java.time.LocalDate;

import seedu.address.model.calendar.DateTime;
import seedu.address.model.record.RecordType;

/**
 * Represents an average value.
 */
public class Average {
    private final AverageType averageType;
    private final RecordType recordType;

    private final DateTime dateTime;

    private final double average;

    public Average(AverageType averageType, RecordType recordType, DateTime dateTime, double average) {
        this.averageType = averageType;
        this.recordType = recordType;
        this.dateTime = dateTime;
        this.average = average;
    }

    public AverageType getAverageType() {
        return averageType;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public LocalDate getDate() {
        return dateTime.getDate();
    }

    public double getAverage() {
        return average;
    }
}
