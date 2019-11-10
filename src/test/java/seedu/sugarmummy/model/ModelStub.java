package seedu.sugarmummy.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.biography.ReadOnlyUserList;
import seedu.sugarmummy.model.biography.User;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.ReadOnlyCalendar;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.recmf.FoodComparator;
import seedu.sugarmummy.model.recmf.FoodName;
import seedu.sugarmummy.model.recmf.UniqueFoodList;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.records.UniqueRecordList;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.time.Today;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    //=========== UserPrefs ==================================================================================

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

    //=========== User List =============================================================

    @Override
    public boolean bioExists() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserList getUserList() {
        throw new AssertionError("This method should not be called.");
    }

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

    //Calendar
    @Override
    public ReadOnlyCalendar getCalendar() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean coversCalendarEntry(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public CalendarEntry getCalendarEntryCovers(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isAnyCoveredByCalendarEntry(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntriesCoveredBy(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean overlapsCalendarEntry(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntryOverlaps(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean conflictsCalendarEntry(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntryConflicts(CalendarEntry calendarEntry) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteCalendarEntry(CalendarEntry target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteAllCalendarEntries(List<CalendarEntry> calendarEntries) {
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
    public Today getToday() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public LocalDateTime getAppStartingDateTime() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUser(User target, User editedUser) {
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

    //=========== Food List =============================================================

    @Override
    public boolean hasFood(Food food) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addFood(Food food) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteFood(FoodName foodName) {
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
    public void setFoodList(UniqueFoodList uniqueFoodLists) {
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
    public void sortFoodList(FoodComparator foodComparator) {
    }

    @Override
    public ObservableList<Food> getMixedFoodList() {
        throw new AssertionError("This method should not be called.");
    }

    //=========== Records =============================================================
    @Override
    public boolean hasRecord(Record record) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteRecord(Record record) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addRecord(Record record) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public UniqueRecordList getUniqueRecordListObject() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Record> getRecordList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRecordList(UniqueRecordList uniqueRecordLists) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Record> getFilterRecordList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
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
    public boolean currAchievementsMapIsSameAs(Map<RecordType, List<Achievement>> prevAchievemenstMap) {
        throw new AssertionError("This method should not be called.");
    }
}
