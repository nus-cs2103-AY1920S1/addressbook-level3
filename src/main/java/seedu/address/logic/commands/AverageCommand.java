package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.AverageType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AverageCommandParser.RecordType;
import seedu.address.model.Model;
import seedu.address.model.calendar.DateTime;

/**
 * Shows daily/weekly/monthly average of different record types.
 */
public class AverageCommand extends Command {

    public static final String COMMAND_WORD = "average";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows daily/weekly/monthly average of different "
            + "record types.\n"
            + "Parameters: a/AVERAGE_TYPE r/RECORD_TYPE [n/COUNT]\n"
            + "Example: " + COMMAND_WORD + " a/daily r/bloodsugar n/5";

    public static final String MESSAGE_INVALID_COUNT = "n/COUNT";

    public static final String MESSAGE_INVALID_AVGTYPE = "a/AVERAGE_TYPE";

    public static final String MESSAGE_INVALID_RECORDTYPE = "r/RECORD_TYPE";

    private static final Map<AverageType, TemporalAdjuster> TIMEADJUSTERS = Map.of(
            AverageType.DAILY, TemporalAdjusters.ofDateAdjuster(date -> date),
            AverageType.WEEKLY, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)),
            AverageType.MONTHLY, TemporalAdjusters.firstDayOfMonth()
    );

    private final AverageType averageType;

    private final RecordType recordType;

    private final int count;

    public AverageCommand(AverageType averageType, RecordType recordType, int count) {
        requireNonNull(averageType);
        requireNonNull(recordType);
        this.averageType = averageType;
        this.recordType = recordType;
        this.count = count;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //TODO: Change this to use record book
        Collection<DummyRecord> recordBooks = new ArrayList<DummyRecord>();
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 10, 15), LocalTime.of(12,24)),1));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 9, 15), LocalTime.of(12,24)), 2));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 8, 15), LocalTime.of(12,24)), 3));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 7, 15), LocalTime.of(12,24)), 4));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 6, 15), LocalTime.of(12,24)), 5));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 10, 14), LocalTime.of(12,24)),6));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 10, 13), LocalTime.of(12,24)),7));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 10, 12), LocalTime.of(12,24)),8));
        recordBooks.add(new DummyRecord(RecordType.BLOODSUGAR, new DateTime(LocalDate.of(2019, 10, 11), LocalTime.of(12,24)),9));


        Collection<DummyRecord> filteredRecords = recordBooks
                .stream().filter(ele -> this.recordType.equals(ele.getRecordType()))
                .collect(Collectors.toList());

        Map<LocalDate, List<DummyRecord>> groupByTimeRecords = filteredRecords.stream()
                .collect(Collectors.groupingBy(record -> record.getDateTime()
                .getDate().with(TIMEADJUSTERS.get(this.averageType))));

        Map<LocalDate, Double> averages = groupByTimeRecords.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, record -> record.getValue().stream().mapToDouble(Double::doubleValue).average().getAsDouble()));

        String result = "";
        for (Map.Entry<LocalDate, List<DummyRecord>> entry : groupByTimeRecords.entrySet()) {
            result = result + entry.getKey() + ", " + entry.getValue().toString() + "\n";
        }

        return new CommandResult(result);
//        return new CommandResult(String.format(String.valueOf(groupByTimeRecords.size())));
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
    private class DummyRecord {
        private final RecordType recordType;

        private final DateTime dateTime;

        private final double value;

        private DummyRecord(RecordType recordType, DateTime dateTime, double value) {
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
