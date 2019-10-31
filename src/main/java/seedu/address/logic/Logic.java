package seedu.address.logic;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.achievements.Achievement;
import seedu.address.model.aesthetics.Background;
import seedu.address.model.aesthetics.Colour;
import seedu.address.model.bio.User;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;
import seedu.address.ui.DisplayPaneType;
import sugarmummy.recmfood.model.Food;

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
     * @see seedu.address.model.Model#getFoodList()
     */
    ObservableList<Food> getFoodList();

    /**
     * Returns an unmodifiable view of the filtered list of foods
     */
    ObservableList<Food> getFilterFoodList();

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
     * @see seedu.address.model.Model#getUserList()
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
     * Returns a {@code Map} object that maps time period to the respective average values.
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
    public Map<RecordType, List<Achievement>> getAchievementsMap();

    /**
     * Returns whether or not new achievements have been attained after a modification to the user's list of records.
     */
    public boolean newAchievementsHaveBeenAttained();

    /**
     * Returns whether or not existing achievements have been lost after a modification to the user's list of records.
     */
    public boolean existingAchievementsHaveBeenLost();

    /**
     * Resets whether there are modification to the user's list of records to false.
     */
    public void resetNewAchievementsState();

    /**
     * Returns whether or not a given achievements map is the same as the current achievements map.
     */
    public boolean currAchievementsMapIsSameAs(Map<RecordType, List<Achievement>> prevAchievementMap);


}
