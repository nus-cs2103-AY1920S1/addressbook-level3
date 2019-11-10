package seedu.sugarmummy.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.sugarmummy.commons.core.GuiSettings;
import seedu.sugarmummy.commons.core.LogsCenter;
import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.logic.parser.SugarMummyParser;
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
import seedu.sugarmummy.storage.Storage;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SugarMummyParser sugarMummyParser;
    private DisplayPaneType displayPaneType;
    private boolean newPaneIsToBeCreated;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        sugarMummyParser = new SugarMummyParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = sugarMummyParser.parseCommand(commandText);
        displayPaneType = command.getDisplayPaneType();
        newPaneIsToBeCreated = command.isToCreateNewPane();
        commandResult = command.execute(model);

        try {
            storage.saveUserList(model.getUserList());
            storage.saveFoodList(model.getUniqueFoodListObject());
            storage.saveRecordList(model.getUniqueRecordListObject());
            storage.saveCalendar(model.getCalendar());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    //=========== General =============================================================

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return displayPaneType;
    }

    @Override
    public boolean getNewPaneIsToBeCreated() {
        return newPaneIsToBeCreated;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    //=========== food List =============================================================

    @Override
    public ObservableList<Food> getFoodList() {
        return model.getFoodList();
    }

    @Override
    public ObservableList<Food> getFilterFoodList() {
        return model.getFilterFoodList();
    }

    @Override
    public ObservableList<Food> getMixedFoodList() {
        return model.getMixedFoodList();
    }

    //=========== Record List =============================================================

    @Override
    public ObservableList<Record> getRecordList() {
        return model.getRecordList();
    }

    @Override
    public ObservableList<Record> getFilterRecordList() {
        return model.getFilterRecordList();
    }

    //=========== User List =============================================================

    @Override
    public Path getUserListFilePath() {
        return model.getUserListFilePath();
    }

    @Override
    public ReadOnlyUserList getUserList() {
        return model.getUserList();
    }

    @Override
    public ObservableList<User> getFilteredUserList() {
        return model.getFilteredUserList();
    }

    @Override
    public List<Map<String, String>> getListOfFieldsContainingInvalidReferences() {
        return storage.getListOfFieldsContainingInvalidReferences();
    }

    public ReadOnlyCalendar getCalendar() {
        return model.getCalendar();
    }

    @Override
    public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
        return model.getFilteredCalendarEntryList();
    }

    @Override
    public ObservableList<CalendarEntry> getPastReminderList() {
        return model.getPastReminderList();
    }

    @Override
    public void schedule() {
        model.schedule();
    }

    @Override
    public void stopAllReminders() {
        model.stopAllReminders();
    }

    @Override
    public Today getToday() {
        return model.getToday();
    }

    @Override
    public LocalDateTime getAppStartingDateTime() {
        return model.getAppStartingDateTime();
    }

    //=========== Statistics List =============================================================

    @Override
    public SimpleStringProperty getAverageType() {
        return model.getAverageType();
    }

    @Override
    public SimpleStringProperty getRecordType() {
        return model.getRecordType();
    }

    @Override
    public ObservableMap<LocalDate, Double> getAverageMap() {
        return model.getAverageMap();
    }

    //=========== Aesthetics =============================================================

    @Override
    public Colour getFontColour() {
        return model.getFontColour();
    }

    @Override
    public Background getBackground() {
        return model.getBackground();
    }

    //=========== Motivational Quotes =============================================================

    @Override
    public List<String> getMotivationalQuotesList() {
        return model.getMotivationalQuotesList();
    }

    //=========== Achievements =============================================================

    @Override
    public Map<RecordType, List<Achievement>> getAchievementsMap() {
        return model.getAchievementsMap();
    }

    @Override
    public boolean newAchievementsHaveBeenAttained() {
        return model.newAchievementsHaveBeenAttained();
    }

    @Override
    public boolean existingAchievementsHaveBeenLost() {
        return model.existingAchievementsHaveBeenLost();
    }

    @Override
    public void resetNewAchievementsState() {
        model.resetNewAchievementsState();
    }

    @Override
    public boolean currAchievementsMapIsSameAs(Map<RecordType, List<Achievement>> prevAchievementMap) {
        return model.currAchievementsMapIsSameAs(prevAchievementMap);
    }


}
