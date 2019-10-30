package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.AverageCommand.MESSAGE_NO_RECORD;
import static seedu.address.logic.commands.AverageCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.achievements.Achievement;
import seedu.address.model.aesthetics.Background;
import seedu.address.model.aesthetics.Colour;
import seedu.address.model.bio.User;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.person.Person;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.UniqueRecordList;
import seedu.address.model.record.Weight;
import seedu.address.model.statistics.AverageMap;
import seedu.address.model.statistics.AverageType;
import seedu.address.model.time.DateTime;
import sugarmummy.recmfood.model.Food;
import sugarmummy.recmfood.model.UniqueFoodList;

public class AverageCommandTest {

    @Test
    public void execute_zeroRecordType_throwsCommandException() {
        ModelStubWithNoRecords modelStubWithNoRecords = new ModelStubWithNoRecords();
        AverageCommand command = new AverageCommand(AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        assertThrows(CommandException.class, String.format(MESSAGE_NO_RECORD,
                RecordType.BLOODSUGAR), () -> command.execute(modelStubWithNoRecords));
    }

    @Test
    public void execute_dailyAverageBloodSugar_success() {
        ModelStubWithRecords modelStubWithRecords = new ModelStubWithRecords();
        ModelStubWithRecords expectedModel = new ModelStubWithRecords();
        expectedModel.calculateAverageMap(AverageType.DAILY, RecordType.BLOODSUGAR, 5);
        AverageCommand command = new AverageCommand(AverageType.DAILY, RecordType.BLOODSUGAR, 5);
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
        expectedModel.calculateAverageMap(AverageType.WEEKLY, RecordType.BMI, 5);
        AverageCommand command = new AverageCommand(AverageType.WEEKLY, RecordType.BMI, 5);
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
        expectedModel.calculateAverageMap(AverageType.MONTHLY, RecordType.BLOODSUGAR, 5);
        AverageCommand command = new AverageCommand(AverageType.MONTHLY, RecordType.BLOODSUGAR, 5);
        String expectedMessage = String.format(MESSAGE_SUCCESS, AverageType.MONTHLY, RecordType.BLOODSUGAR);
        assertCommandSuccess(command, modelStubWithRecords, expectedMessage, expectedModel);
        ObservableMap<LocalDate, Double> calculationMap = FXCollections.observableMap(Map.of(
                LocalDate.of(2019, 1, 1), 4.5
        ));
        ObservableMap<LocalDate, Double> expectedResult = FXCollections.unmodifiableObservableMap(calculationMap);
        assertEquals(expectedResult, modelStubWithRecords.getAverageMap());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasFood(Food food) {
            return false;
        }

        @Override
        public void addFood(Food food) {
        }

        @Override
        public UniqueFoodList getUniqueFoodListObject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFoodList() {
            return null;
        }

        @Override
        public void setFoodList(UniqueFoodList newFoodList) {
        }

        @Override
        public void addRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueRecordList getUniqueRecordListObject() {
            return null;
        }

        @Override
        public ObservableList<Record> getRecordList() {
            return null;
        }

        @Override
        public void setRecordList(UniqueRecordList newRecordList) {

        }

        @Override
        public ObservableList<Record> getFilterRecordList() {
            return null;
        }

        @Override
        public boolean hasRecord(Record toAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRecord(Record record) {

        }

        @Override
        public void updateFilteredRecordList(Predicate<Record> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFilterFoodList() {
            return null;
        }

        @Override
        public void updateFilteredFoodList(Predicate<Food> predicate) {

        }

        @Override
        public boolean bioExists() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserList getUserList() {
            throw new AssertionError("This method should not be called.");
        }

        //=========== User List =============================================================
        @Override
        public void setUserList(ReadOnlyUserList userList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getUserListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserListFilePath(Path userListFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasUser(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addUser(User user) {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public ObservableList<User> getFilteredUserList() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void updateFilteredUserList(Predicate<User> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCalendar getCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCalendarEntry(CalendarEntry target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCalendarEntry(CalendarEntry calendarEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPastReminders(List<Reminder> reminders) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CalendarEntry> getPastReminderList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void schedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void stopAllReminders() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUser(User target, User editedUser) {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Statistics List =============================================================

        @Override
        public SimpleStringProperty getAverageType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SimpleStringProperty getRecordType() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void calculateAverageMap(AverageType averageType, RecordType recordType, int count) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableMap<LocalDate, Double> getAverageMap() {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Aesthetics =============================================================

        @Override
        public Colour getFontColour() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFontColour(Colour fontColour) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Background getBackground() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBackground(Background background) {
            throw new AssertionError("This method should not be called.");
        }


        //=========== Motivational Quotes =============================================================

        @Override
        public List<String> getMotivationalQuotesList() {
            throw new AssertionError("This method should not be called.");
        }

        //=========== Achievements =============================================================

        @Override
        public Map<RecordType, List<Achievement>> getAchievementsMap() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean newAchievementsHaveBeenAttained() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean existingAchievementsHaveBeenLost() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetNewAchievementsState() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains no records.
     */
    private class ModelStubWithNoRecords extends ModelStub {

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
                new Bmi(new Height("200.0"), new Weight("80.0"), new DateTime("2019-01-01 00:00")),
                new Bmi(new Height("200.0"), new Weight("64.0"), new DateTime("2019-01-08 00:00"))
        ));

        private final AverageMap averageMap;

        public ModelStubWithRecords() {
            this.averageMap = new AverageMap();
        }

        @Override
        public void calculateAverageMap(AverageType averageType, RecordType recordType, int count) {
            averageMap.calculateAverage(recordList, averageType, recordType, count);
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
            return averageMap.equals(other.averageMap);
        }
    }

}
