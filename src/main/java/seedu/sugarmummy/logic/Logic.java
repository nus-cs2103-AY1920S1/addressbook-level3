package seedu.sugarmummy.logic;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.achievements.Achievement;
import seedu.sugarmummy.model.aesthetics.Background;
import seedu.sugarmummy.model.aesthetics.Colour;
import seedu.sugarmummy.model.biography.ReadOnlyUserList;
import seedu.sugarmummy.model.biography.User;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.ReadOnlyCalendar;
import seedu.sugarmummy.model.recmf.Food;
import seedu.sugarmummy.model.records.Record;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.time.Today;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the {@code DisplayPaneType} for updating the main pane based on different commands.
     */
    DisplayPaneType getDisplayPaneType();

    /**
     * Returns a boolean indicating whether a new pane is to be created, regardless of whether an existing one already
     * exists.
     */
    boolean getNewPaneIsToBeCreated();

    /**
     * Returns the a list of foods.
     *
     * @see seedu.sugarmummy.model.Model#getFoodList()
     */
    ObservableList<Food> getFoodList();

    /**
     * Returns an unmodifiable view of the filtered list of foods
     */
    ObservableList<Food> getFilterFoodList();

    /**
     * Returns an unmodifiable view of the mix of foods from each food type.
     */
    ObservableList<Food> getMixedFoodList();

    ObservableList<Record> getRecordList();

    ObservableList<Record> getFilterRecordList();


    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    //=========== User List =============================================================

    /**
     * Returns the user prefs' address book file path.
     */
    Path getUserListFilePath();

    /**
     * Returns the UserList.
     *
     * @see seedu.sugarmummy.model.Model#getUserList()
     */
    ReadOnlyUserList getUserList();

    /**
     * Returns an unmodifiable view of the filtered list of users
     */
    ObservableList<User> getFilteredUserList();

    /**
     * Return a list of maps of fields in the json file that contain invalid references.
     *
     * @return List of maps of fields in the json file containing invalid references.
     */
    List<Map<String, String>> getListOfFieldsContainingInvalidReferences();

    //=========================Calendar==============================

    /**
     * Returns the Calendar.
     *
     * @see Model#getCalendar()
     */
    ReadOnlyCalendar getCalendar();

    /**
     * Returns an unmodifiable view of the filtered list of calendar entries
     */
    ObservableList<CalendarEntry> getFilteredCalendarEntryList();

    /**
     * Returns an unmodifiable view of past reminders.
     */
    ObservableList<CalendarEntry> getPastReminderList();

    /**
     * Reschedule upcoming reminders.
     */
    void schedule();

    /**
     * Stop all upcoming reminders;
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
     * Returns an unmodifiable view of the map of average values with key being the time period and
     * key being the average value.
     */
    ObservableMap<LocalDate, Double> getAverageMap();

    //=========== Aesthetics =============================================================

    /**
     * Returns the font colour to be set for this app.
     */
    Colour getFontColour();

    /**
     * Returns the background to be set for this app.
     */
    Background getBackground();

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
