package seedu.address.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.Weight;
import seedu.address.model.time.DateTime;

public class AverageMapTest {
    private static final ObservableList<Record> recordList = FXCollections.observableArrayList(Arrays.asList(
            new BloodSugar(new Concentration("4.0"), new DateTime("2019-01-01 00:00")),
            new BloodSugar(new Concentration("5.0"), new DateTime("2019-01-08 00:00")),
            new Bmi(new Height("200.0"), new Weight("80.0"), new DateTime("2019-01-01 00:00")),
            new Bmi(new Height("200.0"), new Weight("64.0"), new DateTime("2019-01-08 00:00"))
    ));

    private AverageMap averageMap = new AverageMap();

    @Test
    public void calculate_noRecords_returnsEmptyMap() {
        ObservableList<Record> emptyRecordList = FXCollections.observableArrayList();
        averageMap.calculateAverage(emptyRecordList, AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        assertTrue(averageMap.asUnmodifiableObservableMap().isEmpty());
    }

    @Test
    public void calculate_dailyAverageBloodSugar_success() {
        averageMap.calculateAverage(recordList, AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2019, 1, 1), 4.0,
                LocalDate.of(2019, 1, 8), 5.0
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, averageMap.asUnmodifiableObservableMap());
    }

    @Test
    public void calculate_weeklyAverageBmi_success() {
        averageMap.calculateAverage(recordList, AverageType.WEEKLY, RecordType.BMI, 5);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2018, 12, 31), 20.0,
                LocalDate.of(2019, 1, 7), 16.0
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, averageMap.asUnmodifiableObservableMap());
    }

    @Test
    public void calculate_monthlyAverageBloodSugar_success() {
        averageMap.calculateAverage(recordList, AverageType.MONTHLY, RecordType.BLOODSUGAR, 5);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2019, 1, 1), 4.5
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, averageMap.asUnmodifiableObservableMap());
    }

    @Test
    public void equals() {
        AverageMap one = new AverageMap();
        AverageMap two = new AverageMap();
        assertEquals(one, two);
    }
}
