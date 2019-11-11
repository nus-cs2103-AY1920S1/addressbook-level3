package seedu.sugarmummy.model.statistics;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_RECORD_TYPE;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Bmi;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.model.records.RecordType;

//@@author chen-xi-cx

/**
 * Calculates the average values required by {@code AverageCommand}. The averages are stored as a map
 * with key as {@code LocalDate} and value as {@code Double}. Key represents the time period.
 * Value represents the average values.
 */
public class AverageMap {

    private static final Map<AverageType, TemporalAdjuster> TIMEADJUSTERS = Map.of(
            AverageType.DAILY, TemporalAdjusters.ofDateAdjuster(date -> date),
            AverageType.WEEKLY, TemporalAdjusters.previousOrSame(DayOfWeek.of(1)),
            AverageType.MONTHLY, TemporalAdjusters.firstDayOfMonth()
    );

    private final ObservableMap<LocalDate, Double> internalMap = FXCollections.observableHashMap();
    private final ObservableMap<LocalDate, Double> internalUnmodifiableMap =
            FXCollections.unmodifiableObservableMap(internalMap);
    private final SimpleStringProperty internalAverageType = new SimpleStringProperty();
    private final SimpleStringProperty internalRecordType = new SimpleStringProperty();

    public SimpleStringProperty getInternalAverageType() {
        return internalAverageType;
    }

    public SimpleStringProperty getInternalRecordType() {
        return internalRecordType;
    }

    /**
     * Calculates average values of a given record type based on the average type given.
     * Pre-requisite: filteredRecordList must contain only the record type specified by {@code AverageCommand}.
     *
     * @param filteredRecordList list of records containing only the record type specified by @param recordType.
     * @param averageType        the average type.
     * @param recordType         the record type.
     * @param count              the number of most recent averages to calculate.
     */
    public void calculateAverage(ObservableList<Record> filteredRecordList,
            AverageType averageType, RecordType recordType, int count) {
        // Group records according to average type
        Map<LocalDate, List<Record>> groupByTimeRecords = groupByAverageType(averageType, filteredRecordList);

        // Calculate averages for each grouping in groupByTimeRecords
        Map<LocalDate, Double> averages = calculateAverageMap(recordType, groupByTimeRecords);

        // Sort by descending date
        Map<LocalDate, Double> averageMap = new TreeMap<>(Collections.reverseOrder());
        averageMap.putAll(averages);

        // get latest "count" records
        Map<LocalDate, Double> countAverageMap = averageMap.entrySet().stream().limit(count)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

        internalMap.clear();
        internalMap.putAll(countAverageMap);

        internalAverageType.setValue(averageType.toString());
        internalRecordType.setValue(recordType.toString());
    }

    /**
     * Groups records by average type. For example, if average type is weekly, records from the same week are grouped
     * together.
     *
     * @param averageType the average type.
     * @param recordList  list of records containing only one type of record.
     * @return returns a {@code Map} object that maps a time period to the respective records found in that time
     * period.
     */
    private Map<LocalDate, List<Record>> groupByAverageType(AverageType averageType,
            ObservableList<Record> recordList) {
        return recordList.stream()
                .collect(Collectors.groupingBy(record -> record.getDateTime()
                .getDate().with(TIMEADJUSTERS.get(averageType))));
    }

    /**
     * Calculates average for each group of records.
     *
     * @param recordType the record type.
     * @param recordMap  a {@code Map} object that maps a time period to the respective records found in that time
     * period.
     * @return returns a {@code Map} object that maps a time period to the respective average values of records
     * found in that time period.
     */
    private Map<LocalDate, Double> calculateAverageMap(RecordType recordType, Map<LocalDate, List<Record>> recordMap) {
        switch (recordType) {
        case BLOODSUGAR:
            return recordMap.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, ele -> ele.getValue()
                    .stream().map(record -> (BloodSugar) record)
                    .map(record -> record.getConcentration().getConcentration())
                    .mapToDouble(Double::doubleValue).average().getAsDouble()));
        case BMI:
            return recordMap.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, ele -> ele.getValue()
                    .stream().map(record -> (Bmi) record)
                    .map(record -> record.getBmi())
                    .mapToDouble(Double::doubleValue).average().getAsDouble()));
        default:
            assert false : "Record type is not found and should not happen.";
            throw new IllegalArgumentException(MESSAGE_INVALID_RECORD_TYPE);
        }
    }

    /**
     * Returns the backing map as an unmodifiable {@code ObservableMap}
     */
    public ObservableMap<LocalDate, Double> asUnmodifiableObservableMap() {
        return internalUnmodifiableMap;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AverageMap // instanceof handles nulls
                && internalMap.equals(((AverageMap) other).internalMap) // state check
                && internalUnmodifiableMap.equals(((AverageMap) other).internalUnmodifiableMap)
                // Objects.equals checks for null String before calling .equals()
                && Objects.equals(internalAverageType.get(), ((AverageMap) other).internalAverageType.get())
                && Objects.equals(internalRecordType.get(), ((AverageMap) other).internalRecordType.get()));
    }
}
