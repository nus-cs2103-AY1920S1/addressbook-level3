package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.AverageType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.DateTime;
import seedu.address.model.record.RecordType;

/**
 * Shows daily/weekly/monthly average of different record types.
 */
public class AverageCommand extends Command {

    public static final String COMMAND_WORD = "average";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows daily/weekly/monthly average of different "
            + "record types.\n"
            + "Parameters: a/AVERAGE_TYPE rt/RECORD_TYPE [n/COUNT]\n"
            + "Example: " + COMMAND_WORD + " a/daily r/bloodsugar n/5";

    public static final String MESSAGE_INVALID_COUNT = "n/COUNT";

    public static final String MESSAGE_INVALID_AVGTYPE = "a/AVERAGE_TYPE";

    public static final String MESSAGE_INVALID_RECORDTYPE = "r/RECORD_TYPE";

    public static final String MESSAGE_NO_RECORD = "Sorry! You do not have any %1$s record.";

    private static final Map<AverageType, TemporalAdjuster> TIMEADJUSTERS = Map.of(
            AverageType.DAILY, TemporalAdjusters.ofDateAdjuster(date -> date),
            AverageType.WEEKLY, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)),
            AverageType.MONTHLY, TemporalAdjusters.firstDayOfMonth()
    );

    private final AverageType averageType;

    private final RecordType recordType;

    private final int count;

    private final Collection<DummyRecord> recordBook;

    public AverageCommand(AverageType averageType, RecordType recordType, int count) {
        requireNonNull(averageType);
        requireNonNull(recordType);
        this.averageType = averageType;
        this.recordType = recordType;
        this.count = count;
        this.recordBook = new ArrayList<>();

        // sample data
        // TODO: Remove this after integration
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 10, 1), LocalTime.of(12, 24)), 1));
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 10, 2), LocalTime.of(12, 24)), 2));
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 10, 8), LocalTime.of(12, 24)), 3));
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 10, 9), LocalTime.of(12, 24)), 4));
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 9, 15), LocalTime.of(12, 24)), 5));
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 9, 14), LocalTime.of(12, 24)), 6));
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 7, 13), LocalTime.of(12, 24)), 7));
        recordBook.add(new DummyRecord(RecordType.BLOODSUGAR,
                new DateTime(LocalDate.of(2019, 6, 12), LocalTime.of(12, 24)), 8));
        recordBook.add(new DummyRecord(RecordType.MEDICALEXPENSES,
                new DateTime(LocalDate.of(2019, 10, 11), LocalTime.of(12, 24)), 9));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        // Remove irrelevant record types
        Collection<DummyRecord> filteredRecords = recordBook
                .stream().filter(ele -> this.recordType.equals(ele.getRecordType()))
                .collect(Collectors.toList());

        // Group records according to average type
        Map<LocalDate, List<DummyRecord>> groupByTimeRecords = filteredRecords.stream()
                .collect(Collectors.groupingBy(record -> record.getDateTime()
                .getDate().with(TIMEADJUSTERS.get(this.averageType))));

        // Calculate averages for each grouping in groupByTimeRecords
        Map<LocalDate, Double> averages = groupByTimeRecords.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, ele -> ele.getValue()
                .stream().map(record -> record.getValue()).mapToDouble(Double::doubleValue).average().getAsDouble()));

        // Sort by descending date
        Map<LocalDate, Double> averagesTreeMap = new TreeMap<>(Collections.reverseOrder());
        averagesTreeMap.putAll(averages);

        String result = "";

        averagesTreeMap.entrySet().stream()
                .limit(count)
                .forEach(ele -> result.concat("average for " + this.recordType + " "
                + ele.getKey() + " is " + ele.getValue() + "\n"));

        if (result.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_RECORD, this.recordType));
        }

        return new CommandResult(String.format(result));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AverageCommand // instanceof handles nulls
                && averageType.equals(((AverageCommand) other).averageType) // state check
                && recordType.equals(((AverageCommand) other).recordType)
                && count == ((AverageCommand) other).count);
    }

    /**
     * A dummy class to replace actual implementation of Record class
     */
    public static class DummyRecord {
        private final RecordType recordType;

        private final DateTime dateTime;

        private final double value;

        public DummyRecord(RecordType recordType, DateTime dateTime, double value) {
            this.recordType = recordType;
            this.dateTime = dateTime;
            this.value = value;
        }

        public RecordType getRecordType() {
            return recordType;
        }

        public DateTime getDateTime() {
            return dateTime;
        }

        public double getValue() {
            return value;
        }
    }
}
