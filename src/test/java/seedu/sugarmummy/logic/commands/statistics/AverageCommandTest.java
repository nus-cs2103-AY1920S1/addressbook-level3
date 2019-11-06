package seedu.sugarmummy.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.sugarmummy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_NO_RECORD;
import static seedu.sugarmummy.logic.commands.statistics.AverageCommand.MESSAGE_SUCCESS;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.ModelStub;
import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Bmi;
import seedu.sugarmummy.model.records.Concentration;
import seedu.sugarmummy.model.records.Height;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.records.Weight;
import seedu.sugarmummy.model.statistics.AverageMap;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.statistics.predicates.RecordContainsRecordTypePredicate;
import seedu.sugarmummy.model.time.DateTime;

//@@author chen-xi-cx
public class AverageCommandTest {
    private final RecordContainsRecordTypePredicate bloodSugarPredicate =
            new RecordContainsRecordTypePredicate(RecordType.BLOODSUGAR);

    private final RecordContainsRecordTypePredicate bmiPredicate =
            new RecordContainsRecordTypePredicate(RecordType.BMI);

    @Test
    public void execute_zeroRecordType_throwsCommandException() {
        ModelStubWithNoRecords modelStubWithNoRecords = new ModelStubWithNoRecords();
        AverageCommand command = new AverageCommand(bloodSugarPredicate,
                AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        assertThrows(CommandException.class, String.format(MESSAGE_NO_RECORD,
                RecordType.BLOODSUGAR), () -> command.execute(modelStubWithNoRecords));
    }

    @Test
    public void execute_dailyAverageBloodSugar_success() {
        ModelStubWithRecords modelStubWithRecords = new ModelStubWithRecords();
        ModelStubWithRecords expectedModel = new ModelStubWithRecords();
        expectedModel.updateFilteredRecordList(bloodSugarPredicate);
        expectedModel.calculateAverageMap(AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        AverageCommand command = new AverageCommand(bloodSugarPredicate,
                AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        String expectedMessage = String.format(MESSAGE_SUCCESS, AverageType.DAILY, RecordType.BLOODSUGAR);
        assertCommandSuccess(command, modelStubWithRecords, expectedMessage, expectedModel);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2019, 1, 1), 4.0,
                LocalDate.of(2019, 1, 8), 5.0
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, modelStubWithRecords.getAverageMap());
    }

    @Test
    public void execute_weeklyAverageBmi_success() {
        ModelStubWithRecords modelStubWithRecords = new ModelStubWithRecords();
        ModelStubWithRecords expectedModel = new ModelStubWithRecords();
        expectedModel.updateFilteredRecordList(bmiPredicate);
        expectedModel.calculateAverageMap(AverageType.WEEKLY, RecordType.BMI, 5);
        AverageCommand command = new AverageCommand(bmiPredicate, AverageType.WEEKLY, RecordType.BMI, 5);
        String expectedMessage = String.format(MESSAGE_SUCCESS, AverageType.WEEKLY, RecordType.BMI);
        assertCommandSuccess(command, modelStubWithRecords, expectedMessage, expectedModel);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2018, 12, 31), 20.0,
                LocalDate.of(2019, 1, 7), 16.0
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, modelStubWithRecords.getAverageMap());
    }

    @Test
    public void execute_monthlyAverageBloodSugar_success() {
        ModelStubWithRecords modelStubWithRecords = new ModelStubWithRecords();
        ModelStubWithRecords expectedModel = new ModelStubWithRecords();
        expectedModel.updateFilteredRecordList(bloodSugarPredicate);
        expectedModel.calculateAverageMap(AverageType.MONTHLY, RecordType.BLOODSUGAR, 5);
        AverageCommand command = new AverageCommand(bloodSugarPredicate,
                AverageType.MONTHLY, RecordType.BLOODSUGAR, 5);
        String expectedMessage = String.format(MESSAGE_SUCCESS, AverageType.MONTHLY, RecordType.BLOODSUGAR);
        assertCommandSuccess(command, modelStubWithRecords, expectedMessage, expectedModel);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2019, 1, 1), 4.5
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, modelStubWithRecords.getAverageMap());
    }

    /**
     * A Model stub that contains no records.
     */
    private class ModelStubWithNoRecords extends ModelStub {
        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            return;
        }

        @Override
        public void calculateAverageMap(AverageType averageType, RecordType recordType, int count) {
            return;
        }

        @Override
        public ObservableMap<LocalDate, Double> getAverageMap() {
            return FXCollections.observableHashMap();
        }

    }

    /**
     * A Model stub that contains two BLOODSUGAR and two BMI records.
     */
    private class ModelStubWithRecords extends ModelStub {
        private final ObservableList<Record> recordList = FXCollections.observableArrayList(Arrays.asList(
                new BloodSugar(new Concentration("4.0"), new DateTime("2019-01-01 00:00")),
                new BloodSugar(new Concentration("5.0"), new DateTime("2019-01-08 00:00")),
                new Bmi(new Height("2.0"), new Weight("80.0"), new DateTime("2019-01-01 00:00")),
                new Bmi(new Height("2.0"), new Weight("64.0"), new DateTime("2019-01-08 00:00"))
        ));

        private final FilteredList<Record> filteredRecordList = new FilteredList<>(recordList);

        private final AverageMap averageMap;

        public ModelStubWithRecords() {
            this.averageMap = new AverageMap();
        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            filteredRecordList.setPredicate(predicate);
        }

        @Override
        public void calculateAverageMap(AverageType averageType, RecordType recordType, int count) {
            averageMap.calculateAverage(filteredRecordList, averageType, recordType, count);
        }

        @Override
        public ObservableMap<LocalDate, Double> getAverageMap() {
            return averageMap.asUnmodifiableObservableMap();
        }

        @Override
        public boolean equals(
                Object obj) {
            // short circuit if same object
            if (obj == this) {
                return false;
            }

            // instanceof handles nulls
            if (!(obj instanceof ModelStubWithRecords)) {
                return false;
            }

            // state check
            ModelStubWithRecords other = (ModelStubWithRecords) obj;
            return averageMap.equals(other.averageMap) && filteredRecordList.equals(other.filteredRecordList);
        }
    }

}
