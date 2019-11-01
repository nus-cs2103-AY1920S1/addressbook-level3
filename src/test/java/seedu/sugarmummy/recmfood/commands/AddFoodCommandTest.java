package seedu.sugarmummy.recmfood.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.ReadOnlyCalendar;
import seedu.sugarmummy.model.ReadOnlyUserList;
import seedu.sugarmummy.model.ReadOnlyUserPrefs;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.bio.User;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.record.Record;
import seedu.sugarmummy.model.record.RecordType;
import seedu.sugarmummy.model.record.UniqueRecordList;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.recmfood.model.Food;
import seedu.sugarmummy.recmfood.model.UniqueFoodList;
import seedu.sugarmummy.recmfood.testutil.FoodBuilder;

class AddFoodCommandTest {

    private Food food;

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddFoodCommand(null));
    }

    @Test
    public void execute_foodAcceptedByModel_addSuccessful() throws Exception {
        AddFoodCommandTest.ModelStubAcceptingFoodAdded modelStub = new AddFoodCommandTest.ModelStubAcceptingFoodAdded();
        Food food = new FoodBuilder().build();

        CommandResult commandResult = new AddFoodCommand(food).execute(modelStub);

        assertEquals(String.format(AddFoodCommand.MESSAGE_SUCCESS, food), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(food), modelStub.foodsAdded);
    }

    @Test
    public void execute_duplicateFood_throwsCommandException() {
        Food validFood = new FoodBuilder().build();
        AddFoodCommand addFoodCommand = new AddFoodCommand(validFood);
        AddFoodCommandTest.ModelStub modelStub = new AddFoodCommandTest.ModelStubWithFood(validFood);

        assertThrows(CommandException.class, AddFoodCommand.MESSAGE_DUPLICATE_FOOD, () ->
                addFoodCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Food yogurt = new FoodBuilder().withFoodName("Yogurt").build();
        Food water = new FoodBuilder().withFoodName("water").build();
        AddFoodCommand addYogurtCommand = new AddFoodCommand(yogurt);
        AddFoodCommand addWaterCommand = new AddFoodCommand(water);

        // same object -> returns true
        assertTrue(addYogurtCommand.equals(addYogurtCommand));

        // same values -> returns true
        AddFoodCommand addFoodCommandCopy = new AddFoodCommand(yogurt);
        assertTrue(addYogurtCommand.equals(addFoodCommandCopy));

        // different types -> returns false
        assertFalse(addYogurtCommand.equals(1));

        // null -> returns false
        assertFalse(addYogurtCommand.equals(null));

        // different food -> returns false
        assertFalse(addYogurtCommand.equals(addWaterCommand));
    }

    /**
     * A default sugarmummy.recmfood.model stub that have all of the methods failing.
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
        public boolean hasFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addFood(Food food) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UniqueFoodList getUniqueFoodListObject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFoodList(UniqueFoodList newFoodList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Food> getFilterFoodList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredFoodList(Predicate<Food> predicate) {
            throw new AssertionError("This method should not be called.");
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


        //=========== Records =============================================================

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

        @Override
        public boolean currAchievementsMapIsSameAs(Map<RecordType, List<Achievement>> prevAchievementMap) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single food.
     */
    private class ModelStubWithFood extends AddFoodCommandTest.ModelStub {
        private final Food food;

        ModelStubWithFood(Food food) {
            requireNonNull(food);
            this.food = food;
        }

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return this.food.isSameFood(food);
        }
    }

    /**
     * A Model stub that always accept the food being added.
     */
    private class ModelStubAcceptingFoodAdded extends AddFoodCommandTest.ModelStub {
        final ArrayList<Food> foodsAdded = new ArrayList<>();

        @Override
        public boolean hasFood(Food food) {
            requireNonNull(food);
            return foodsAdded.stream().anyMatch(food::isSameFood);
        }

        @Override
        public void addFood(Food food) {
            requireNonNull(food);
            foodsAdded.add(food);
        }

    }
}
