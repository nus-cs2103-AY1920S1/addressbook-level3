package seedu.sugarmummy.model;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.sugarmummy.model.achievements.AchievementState.ACHIEVED;
import static seedu.sugarmummy.model.achievements.AchievementState.PREVIOUSLY_ACHIEVED;
import static seedu.sugarmummy.model.achievements.AchievementState.YET_TO_ACHIEVE;
import static seedu.sugarmummy.model.achievements.AchievementsMap.ACHIEVEMENTS_MAP;
import static seedu.sugarmummy.model.motivationalquotes.MotivationalQuotes.MOTIVATIONAL_QUOTES_LIST;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.achievements.AchievementState;
import seedu.sugarmummy.model.achievements.AchievementStateProcessor;
import seedu.sugarmummy.model.achievements.AchievementsMap;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.biography.ReadOnlyUserList;
import seedu.sugarmummy.model.biography.User;
import seedu.sugarmummy.model.biography.UserList;
import seedu.sugarmummy.model.calendar.Calendar;
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
import seedu.sugarmummy.model.statistics.AverageMap;
import seedu.sugarmummy.model.statistics.AverageType;
import seedu.sugarmummy.model.time.Today;

/**
 * Represents the in-memory sugarmummy.model of the SugarMummy data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final FilteredList<User> filteredUserList;
    private final UserList userList;

    private final UniqueFoodList foodList;
    private final FilteredList<Food> filteredFoodList;

    private final UniqueRecordList recordList;
    private final FilteredList<Record> filteredRecordList;

    private final Calendar calendar;
    private final FilteredList<CalendarEntry> filteredCalenderEntryList;
    private final FilteredList<CalendarEntry> pastReminderList;

    private final AverageMap averageMap;
    private final List<String> motivationalQuotesList;
    private final Map<RecordType, List<Achievement>> achievementsMap;

    private boolean achievementsHaveBeenAttained;
    private boolean achievementsHaveBeenLost;

    /**
     * Initializes a ModelManager with the given data and userPrefs.
     */
    public ModelManager(ReadOnlyUserPrefs userPrefs, ReadOnlyUserList userList,
                        UniqueFoodList foodList, UniqueRecordList recordList,
                        ReadOnlyCalendar calendar) {
        super();
        requireAllNonNull(userPrefs, foodList, userList, recordList, calendar);

        logger.fine("Initializing with and user prefs " + userPrefs
                + " and food map: " + foodList + " and record list: " + recordList + " and calendar: " + calendar);

        this.userPrefs = new UserPrefs(userPrefs);
        this.userList = new UserList(userList);
        this.filteredUserList = new FilteredList<>(this.userList.getUserList());
        this.foodList = foodList;
        this.filteredFoodList = new FilteredList<>(this.foodList.getUnmodifiableObservableList());
        this.recordList = recordList;
        this.filteredRecordList = new FilteredList<>(this.recordList.asUnmodifiableObservableList());
        this.calendar = new Calendar(calendar);
        this.filteredCalenderEntryList = new FilteredList<>(this.calendar.getCalendarEntryList());
        this.pastReminderList = new FilteredList<>(this.calendar.getPastReminderList());
        this.averageMap = new AverageMap();
        this.motivationalQuotesList = MOTIVATIONAL_QUOTES_LIST;
        this.achievementsMap = ACHIEVEMENTS_MAP;
        this.achievementsHaveBeenAttained = false;
        this.achievementsHaveBeenLost = false;
        getNewAchievementStates();
    }

    public ModelManager() {
        this(new UserPrefs(), new UserList(), new UniqueFoodList(), new UniqueRecordList(),
                new Calendar());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public boolean equals(
            Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && filteredUserList.equals(other.filteredUserList)
                && filteredFoodList.equals(other.filteredFoodList)
                && filteredRecordList.equals(other.filteredRecordList)
                && averageMap.equals(other.averageMap);
    }

    //=========== User List =============================================================

    @Override
    public boolean bioExists() {
        return !this.userList.isEmpty();
    }

    @Override
    public ReadOnlyUserList getUserList() {
        return userList;
    }

    @Override
    public void setUserList(ReadOnlyUserList userList) {
        this.userList.resetData(userList);
    }

    @Override
    public Path getUserListFilePath() {
        return userPrefs.getUserListFilePath();
    }

    @Override
    public void setUserListFilePath(Path userListFilePath) {
        requireNonNull(userListFilePath);
        userPrefs.setUserListFilePath(userListFilePath);
    }

    @Override
    public boolean hasUser(User user) {
        requireNonNull(user);
        return userList.hasUser(user);
    }

    @Override
    public void addUser(User user) {
        userList.addUser(user);
        updateFilteredUserList(PREDICATE_SHOW_ALL_USERS);
    }

    /**
     * Returns an unmodifiable view of the list of {@code User} backed by the internal list of {@code
     * versionedAddressBook}
     */
    @Override
    public ObservableList<User> getFilteredUserList() {
        return filteredUserList;
    }

    @Override
    public void updateFilteredUserList(Predicate<User> predicate) {
        requireNonNull(predicate);
        filteredUserList.setPredicate(predicate);
    }

    //Calendar
    @Override
    public ReadOnlyCalendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean hasCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendar.hasCalendarEntry(calendarEntry);
    }

    @Override
    public boolean coversCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendar.coversCalendarEntry(calendarEntry);
    }

    @Override
    public CalendarEntry getCalendarEntryCovers(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendar.getCalendarEntryCovers(calendarEntry);
    }

    @Override
    public boolean isAnyCoveredByCalendarEntry(CalendarEntry calendarEntry) {
        return calendar.isAnyCoveredByCalendarEntry(calendarEntry);
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntriesCoveredBy(CalendarEntry calendarEntry) {
        return calendar.getCalendarEntriesCoveredBy(calendarEntry);
    }

    @Override
    public boolean overlapsCalendarEntry(CalendarEntry calendarEntry) {
        requireNonNull(calendarEntry);
        return calendar.overlapsCalendarEntry(calendarEntry);
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntryOverlaps(CalendarEntry calendarEntry) {
        return calendar.getCalendarEntryOverlaps(calendarEntry);
    }

    @Override
    public boolean conflictsCalendarEntry(CalendarEntry calendarEntry) {
        return calendar.conflictsCalendarEntry(calendarEntry);
    }

    @Override
    public ObservableList<CalendarEntry> getCalendarEntryConflicts(CalendarEntry calendarEntry) {
        return calendar.getCalendarEntriesConflicts(calendarEntry);
    }

    @Override
    public void deleteCalendarEntry(CalendarEntry target) {
        calendar.removeCalendarEntry(target);
    }

    @Override
    public void deleteAllCalendarEntries(List<CalendarEntry> calendarEntries) {
        calendar.removeAllCalendarEntries(calendarEntries);
    }

    @Override
    public void addCalendarEntry(CalendarEntry calendarEntry) {
        calendar.addCalendarEntry(calendarEntry);
    }

    @Override
    public void addPastReminders(List<Reminder> reminders) {
        calendar.addPastReminders(reminders);
    }

    @Override
    public void setCalendarEntry(CalendarEntry target, CalendarEntry editedCalendarEntry) {
        requireAllNonNull(target, editedCalendarEntry);
        calendar.setCalendarEntry(target, editedCalendarEntry);
    }

    @Override
    public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
        return filteredCalenderEntryList;
    }

    @Override
    public ObservableList<CalendarEntry> getPastReminderList() {
        return pastReminderList;
    }

    @Override
    public void schedule() {
        calendar.schedule();
    }

    @Override
    public void stopAllReminders() {
        calendar.stopAllReminders();
    }

    @Override
    public Today getToday() {
        return calendar.getToday();
    }

    @Override
    public LocalDateTime getAppStartingDateTime() {
        return calendar.getAppStartingDateTime();
    }

    @Override
    public void setUser(User target, User editedUser) {
        requireAllNonNull(target, editedUser);
        userList.setUser(target, editedUser);
    }


    //=========== Aesthetics =============================================================

    @Override
    public Colour getFontColour() {
        return userPrefs.getFontColour();
    }

    @Override
    public void setFontColour(Colour fontColour) {
        userPrefs.setFontColour(fontColour);
    }

    @Override
    public Background getBackground() {
        return userPrefs.getBackground();
    }

    @Override
    public void setBackground(Background background) {
        userPrefs.setBackground(background);
    }

    //=========== Food List =============================================================

    @Override
    public boolean hasFood(Food food) {
        requireNonNull(food);
        return foodList.contains(food);
    }

    @Override
    public void addFood(Food food) {
        foodList.add(food);
    }

    @Override
    public void deleteFood(FoodName foodName) {
        foodList.delete(foodName);
    }

    @Override
    public UniqueFoodList getUniqueFoodListObject() {
        return foodList;
    }

    @Override
    public ObservableList<Food> getFoodList() {
        return foodList.getUnmodifiableObservableList();
    }

    @Override
    public void setFoodList(UniqueFoodList uniqueFoodLists) {
        requireAllNonNull(uniqueFoodLists);
        foodList.setFoods(uniqueFoodLists);
    }

    @Override
    public ObservableList<Food> getFilterFoodList() {
        return filteredFoodList;
    }

    @Override
    public void updateFilteredFoodList(Predicate<Food> predicate) {
        requireNonNull(predicate);
        filteredFoodList.setPredicate(predicate);
    }

    @Override
    public ObservableList<Food> getMixedFoodList() {
        return foodList.getMixedFoodList();
    }

    @Override
    public void sortFoodList(FoodComparator foodComparator) {
        requireNonNull(foodComparator);
        foodList.sortFoods(foodComparator);
    }

    //=========== Records =============================================================
    @Override
    public boolean hasRecord(Record record) {
        requireNonNull(record);
        return recordList.contains(record);
    }

    @Override
    public void deleteRecord(Record record) {
        recordList.remove(record);
        determineIfAchievementsHaveChanged();
    }

    @Override
    public void addRecord(Record record) {
        recordList.add(record);
        determineIfAchievementsHaveChanged();
    }

    @Override
    public UniqueRecordList getUniqueRecordListObject() {
        return recordList;
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return recordList.asUnmodifiableObservableList();
    }

    @Override
    public void setRecordList(UniqueRecordList uniqueRecordLists) {
        requireAllNonNull(uniqueRecordLists);
        recordList.setRecords(uniqueRecordLists);
    }

    @Override
    public ObservableList<Record> getFilterRecordList() {
        return filteredRecordList;
    }

    @Override
    public void updateFilteredRecordList(Predicate<Record> predicate) {
        requireNonNull(predicate);
        filteredRecordList.setPredicate(predicate);
    }

    //=========== Statistics List =============================================================

    @Override
    public SimpleStringProperty getAverageType() {
        return averageMap.getInternalAverageType();
    }

    @Override
    public SimpleStringProperty getRecordType() {
        return averageMap.getInternalRecordType();
    }

    @Override
    public void calculateAverageMap(AverageType averageType, RecordType recordType, int count) {
        averageMap.calculateAverage(getFilterRecordList(), averageType, recordType, count);
    }

    @Override
    public ObservableMap<LocalDate, Double> getAverageMap() {
        return averageMap.asUnmodifiableObservableMap();
    }

    //=========== Motivational Quotes =============================================================

    @Override
    public List<String> getMotivationalQuotesList() {
        return motivationalQuotesList;
    }

    //=========== Achievements =============================================================

    @Override
    public Map<RecordType, List<Achievement>> getAchievementsMap() {
        return achievementsMap;
    }

    @Override
    public boolean newAchievementsHaveBeenAttained() {
        return achievementsHaveBeenAttained;
    }

    @Override
    public boolean existingAchievementsHaveBeenLost() {
        return achievementsHaveBeenLost;
    }

    @Override
    public void resetNewAchievementsState() {
        achievementsHaveBeenLost = false;
        achievementsHaveBeenAttained = false;
    }

    /**
     * Sets whether there are achievements attained and / or lost after checking for changes.
     */
    private void determineIfAchievementsHaveChanged() {
        Set<AchievementState> newStates = getNewAchievementStates();
        if (!newStates.isEmpty()) {
            if (newStates.contains(ACHIEVED)) {
                achievementsHaveBeenAttained = true;
            }
            if (newStates.contains(PREVIOUSLY_ACHIEVED)
                    || newStates.contains(YET_TO_ACHIEVE)) {
                achievementsHaveBeenLost = true;
            }
        }
    }

    /**
     * Returns the set of changes made to the list of achievements stored in this program, if any.
     */
    private Set<AchievementState> getNewAchievementStates() {
        return (new AchievementStateProcessor(this)).getNewAchievementStates();
    }

    @Override
    public boolean currAchievementsMapIsSameAs(Map<RecordType, List<Achievement>> prevAchievemenstMap) {
        return AchievementsMap.currAchievementsMapIsSameAs(prevAchievemenstMap);
    }


}
