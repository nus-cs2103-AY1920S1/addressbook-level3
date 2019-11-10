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
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<User> PREDICATE_SHOW_ALL_USERS = unused -> true;
    //TODO: check what this means
    Predicate<Record> PREDICATE_SHOW_ALL_RECORDS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);


    //==================Food List====================

    boolean hasFood(Food food);

    /**
     * Adds the given food. {@code food} must not already exist in the recommendations.
     */
    void addFood(Food food);

    /**
     * Deletes the food that matches the give food name. {@code foodName} must exist in the recommendations.
     */
    void deleteFood(FoodName foodName);

    /**
     * Returns the {@code UniqueFoodList} object.
     */
    UniqueFoodList getUniqueFoodListObject();

    /**
     * Returns the a list of foods.
     */
    ObservableList<Food> getFoodList();

    /**
     * Replaces food list data with the data in {@code newFoodList}.
     */
    void setFoodList(UniqueFoodList newFoodList);

    /**
     * Returns an unmodifiable view of the filtered food list
     */
    ObservableList<Food> getFilterFoodList();

    /**
     * Updates the filter of the filtered food list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFoodList(Predicate<Food> predicate);

    /**
     * Sorts the food list based on the given {@code FoodComparator}.
     */
    void sortFoodList(FoodComparator foodComparator);

    /**
     * Returns an unmodifiable view of the mix of foods from each food type.
     */
    ObservableList<Food> getMixedFoodList();


    //==================RECORD====================

    boolean hasRecord(Record record);

    /**
     * Deletes the given record. The record must exist in the recommendations.
     */
    void deleteRecord(Record record);

    /**
     * Adds the given record. {@code record} must not already exist in the record list.
     */
    void addRecord(Record record);

    /**
     * Returns the {@code UniqueRecordList} object.
     */
    UniqueRecordList getUniqueRecordListObject();

    /**
     * Returns the a list of records.
     */
    ObservableList<Record> getRecordList();

    /**
     * Replaces record list data with the data in {@code newRecordList}.
     */
    void setRecordList(UniqueRecordList newRecordList);

    /**
     * Returns an unmodifiable view of the filtered record list
     */
    ObservableList<Record> getFilterRecordList();

    /**
     * Updates the filter of the filtered record list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecordList(Predicate<Record> predicate);

    //=========== Statistics List =============================================================

    /**
     * Returns the last average type calculated.
     */
    SimpleStringProperty getAverageType();

    /**
     * Returns the last record type whose average is calculated.
     */
    SimpleStringProperty getRecordType();

    /**
     * Calculates average values of a record type.
     *
     * @param averageType the average type to calculate.
     * @param recordType  the record type to calculate.
     * @param count       the number of most recent averages to calculate.
     */
    void calculateAverageMap(AverageType averageType, RecordType recordType, int count);

    /**
     * Returns an unmodifiable view of the map of average values with key being the time period and key being the
     * average value.
     */
    ObservableMap<LocalDate, Double> getAverageMap();

    //=========== User List =============================================================

    /**
     * Returns whether or not a user biography already exists.
     *
     * @return
     */
    boolean bioExists();

    /**
     * Returns the UserList
     */
    ReadOnlyUserList getUserList();

    /**
     * Replaces user list data with the data in {@code userList}.
     */
    void setUserList(ReadOnlyUserList userList);

    /**
     * Returns the user prefs' user list file path.
     */
    Path getUserListFilePath();

    /**
     * Sets the user prefs' user list file path.
     */
    void setUserListFilePath(Path userListFilePath);

    /**
     * Returns true if a user with the same identity as {@code user} exists in the user list.
     */
    boolean hasUser(User user);

    /**
     * Adds the given user. {@code user} must not already exist in the user list.
     */
    void addUser(User user);

    /**
     * Replaces the given user {@code target} with {@code editedUser}. {@code target} must exist in the user list. The
     * user identity of {@code editedUser} must not be the same as another existing user in the user list.
     */
    void setUser(User target, User editedUser);

    /**
     * Returns an unmodifiable view of the filtered user list
     */
    ObservableList<User> getFilteredUserList();

    /**
     * Updates the filter of the filtered user list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredUserList(Predicate<User> predicate);

    //========Calendar==================================
    /**
     * Returns the Calendar.
     */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns true if a calendar entry with the same identity as {@code calendarEntry} exists in the calendar.
     */
    boolean hasCalendarEntry(CalendarEntry calendarEntry);

    boolean coversCalendarEntry(CalendarEntry calendarEntry);

    CalendarEntry getCalendarEntryCovers(CalendarEntry calendarEntry);

    boolean isAnyCoveredByCalendarEntry(CalendarEntry calendarEntry);

    ObservableList<CalendarEntry> getCalendarEntriesCoveredBy(CalendarEntry calendarEntry);

    boolean overlapsCalendarEntry(CalendarEntry calendarEntry);

    ObservableList<CalendarEntry> getCalendarEntryOverlaps(CalendarEntry calendarEntry);

    boolean conflictsCalendarEntry(CalendarEntry calendarEntry);

    ObservableList<CalendarEntry> getCalendarEntryConflicts(CalendarEntry calendarEntry);

    /**
     * Deletes the given calendarEntry. The calendarEntry must exist in the calendar.
     */
    void deleteCalendarEntry(CalendarEntry target);

    /**
     * Deletes the given calendarEntries. The calendarEntries must exist in the calendar.
     */
    void deleteAllCalendarEntries(List<CalendarEntry> calendarEntries);

    /**
     * Adds the given calendar entry. {@code calendarEntry} must not already exist in the calendar.
     */
    void addCalendarEntry(CalendarEntry calendarEntry);

    /**
     * Adds a list reminders to the calendar. Only {@code Reminders} that don't already exist in the list will be
     * added.
     */
    void addPastReminders(List<Reminder> reminders);

    /**
     * Replaces the given calendarEntry {@code target} with {@code editedCalendarEntry}. {@code target} must exist in
     * the calendar. The calendarEntry identity of {@code editedCalendarEntry} must not be the same as another existing
     * calendar entry in the calendar.
     */
    void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry);

    /**
     * Returns an unmodifiable view of the filtered calendar entry list
     */
    ObservableList<CalendarEntry> getFilteredCalendarEntryList();

    /**
     * Returns an unmodifiable view of the past reminder list
     */
    ObservableList<CalendarEntry> getPastReminderList();

    /**
     * Reschedule upcoming reminders.
     */
    void schedule();

    /**
     * Stop all upcoming reminders.
     */
    void stopAllReminders();

    /**
     * Returns a Today object represents the date of today.
     */
    Today getToday();

    /**
     * Returns the date time when the app starts.
     */
    LocalDateTime getAppStartingDateTime();

    //=========== Aesthetics =============================================================

    /**
     * Returns the font colour to be set for this app.
     */
    Colour getFontColour();

    /**
     * Sets the font colour of this application and saves it to the user preferences file.
     */
    void setFontColour(Colour fontColour);

    /**
     * Returns the background to be set for this app.
     */
    Background getBackground();

    /**
     * Sets the background of this application and saves it to the user preferences file.
     */
    void setBackground(Background background);

    //=========== Motivational Quotes =============================================================

    /**
     * Returns an unmodifiable list of motivational quotes stored in this program.
     */
    List<String> getMotivationalQuotesList();

    //=========== Achievements =============================================================

    /**
     * Returns an unmodifiable map of achievements stored in this program.
     */
    Map<RecordType, List<Achievement>> getAchievementsMap();

    /**
     * Returns whether or not new achievements have been attained after a modification to the user's list of records.
     */
    boolean newAchievementsHaveBeenAttained();

    /**
     * Returns whether or not existing achievements have been lost after a modification to the user's list of records.
     */
    boolean existingAchievementsHaveBeenLost();

    /**
     * Resets whether there are modification to the user's list of records to false.
     */
    void resetNewAchievementsState();

    /**
     * Returns whether or not a given achievements map is the same as the current achievements map.
     */
    boolean currAchievementsMapIsSameAs(Map<RecordType, List<Achievement>> prevAchievementMap);


}
