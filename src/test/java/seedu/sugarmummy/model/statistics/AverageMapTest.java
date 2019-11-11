package seedu.sugarmummy.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Bmi;
import seedu.sugarmummy.model.records.Concentration;
import seedu.sugarmummy.model.records.Height;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.records.Weight;
import seedu.sugarmummy.model.time.DateTime;

//@@author chen-xi-cx

public class AverageMapTest {
    private final ObservableList<Record> bloodSugarRecordList = FXCollections.observableArrayList(Arrays.asList(
            new BloodSugar(new Concentration("4.0"), new DateTime("2019-01-01 00:00")),
            new BloodSugar(new Concentration("5.0"), new DateTime("2019-01-08 00:00"))
    ));

    private final ObservableList<Record> bmiRecordList = FXCollections.observableArrayList(Arrays.asList(
            new Bmi(new Height("2.0"), new Weight("80.0"), new DateTime("2019-01-01 00:00")),
            new Bmi(new Height("2.0"), new Weight("64.0"), new DateTime("2019-01-08 00:00"))
    ));

    private AverageMap averageMap = new AverageMap();

    @Test
    public void calculateAverage_noRecords_returnsEmptyMap() {
        ObservableList<Record> emptyRecordList = FXCollections.observableArrayList();
        averageMap.calculateAverage(emptyRecordList, AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        assertTrue(averageMap.asUnmodifiableObservableMap().isEmpty());
    }

    @Test
    public void calculateAverage_dailyAverageBloodSugar_success() {
        averageMap.calculateAverage(bloodSugarRecordList, AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2019, 1, 1), 4.0,
                LocalDate.of(2019, 1, 8), 5.0
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, averageMap.asUnmodifiableObservableMap());
    }

    @Test
    public void calculateAverage_weeklyAverageBmi_success() {
        averageMap.calculateAverage(bmiRecordList, AverageType.WEEKLY, RecordType.BMI, 5);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2018, 12, 31), 20.0,
                LocalDate.of(2019, 1, 7), 16.0
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, averageMap.asUnmodifiableObservableMap());
    }

    @Test
    public void calculateAverage_monthlyAverageBloodSugar_success() {
        averageMap.calculateAverage(bloodSugarRecordList, AverageType.MONTHLY, RecordType.BLOODSUGAR, 5);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2019, 1, 1), 4.5
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, averageMap.asUnmodifiableObservableMap());
    }

    @Test
    public void equals() {
        AverageMap mapOne = new AverageMap();
        AverageMap mapTwo = new AverageMap();
        assertEquals(mapOne, mapTwo);
    }
}
