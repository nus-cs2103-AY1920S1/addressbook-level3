package seedu.address.model.statistics;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;

/**
 * A map of average values with key as {@code LocalDate} and value as {@code Double}. Keys represent the time period of
 * the average values. Values represent the average from values within time period of keys.
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
     *
     * @param recordList  list of records.
     * @param averageType the average type.
     * @param recordType  the record type.
     * @param count
     * @return
     */
    public void calculateAverage(ObservableList<Record> recordList,
                                 AverageType averageType, RecordType recordType, int count) {
        // Remove irrelevant record types
        ObservableList<Record> filteredRecords = filterRecord(recordList, recordType);

        // Group records according to average type
        Map<LocalDate, List<Record>> groupByTimeRecords = groupByAverageType(averageType, filteredRecords);

        // Calculate averages for each grouping in groupByTimeRecords
        Map<LocalDate, Double> averages = getAverage(recordType, groupByTimeRecords);

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

    //TODO: abstract this by using ModelManager#updateFilteredRecordList.

    /**
     * Filters list of records by the given record type.
     *
     * @param recordList list of records.
     * @param recordType record type to be kept.
     * @return returns a list containing only records of specified record type.
     */
    private ObservableList<Record> filterRecord(ObservableList<Record> recordList, RecordType recordType) {
        FilteredList<Record> filteredRecords = new FilteredList<>(recordList);
        Predicate<Record> containsRecordType;
        switch (recordType) {
        case BLOODSUGAR:
            containsRecordType = ele -> ele instanceof BloodSugar;
            break;
        case BMI:
            containsRecordType = ele -> ele instanceof Bmi;
            break;
        default:
            // will not happen
            assert false : "Record type is not supported.";
            containsRecordType = null;
        }
        filteredRecords.setPredicate(containsRecordType);
        return filteredRecords;
    }

    /**
     * Groups records by average type. For example, if average type is weekly, records from the same week are grouped
     * together.
     *
     * @param averageType the average type.
     * @param recordList  list of records.
     * @return returns a {@code Map} object that maps a time period to the respective records found in that time
     *         period.
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
     *                   period.
     * @return returns a {@code Map} object that maps a time period to the respective average values of records found in
     *         that time period.
     */
    private Map<LocalDate, Double> getAverage(RecordType recordType, Map<LocalDate, List<Record>> recordMap) {
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
            // will not happen
            assert false : "Record type is not supported";
            return null;
        }
    }

    /**
     * Returns the backing map as an unmodifiable {@code ObservableMap}
     *
     * @return
     */
    public ObservableMap<LocalDate, Double> asUnmodifiableObservableMap() {
        return internalUnmodifiableMap;
    }
}
