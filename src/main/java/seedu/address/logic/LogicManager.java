package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SugarMummyParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.aesthetics.Background;
import seedu.address.model.aesthetics.Colour;
import seedu.address.model.bio.User;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.storage.Storage;
import seedu.address.ui.DisplayPaneType;
import sugarmummy.recmfood.model.Food;

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
        newPaneIsToBeCreated = command.getNewPaneIsToBeCreated();
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveUserList(model.getUserList());
            storage.saveFoodList(model.getUniqueFoodListObject());
            storage.saveRecordList(model.getUniqueRecordListObject());
            storage.saveCalendar(model.getCalendar());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return displayPaneType;
    }

    @Override
    public boolean getNewPaneIsToBeCreated() {
        return newPaneIsToBeCreated;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Food> getFoodList() {
        return model.getFoodList();
    }

    @Override
    public ObservableList<Food> getFilterFoodList() {
        return model.getFilterFoodList();
    }

    @Override
    public ObservableList<Record> getRecordList() {
        return model.getRecordList();
    }

    @Override
    public ObservableList<Record> getFilterRecordList() {
        return model.getFilterRecordList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
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


}
