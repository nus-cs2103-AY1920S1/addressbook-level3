package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.aesthetics.Colour;
import seedu.address.model.bio.User;
import seedu.address.model.calendar.CalendarEntry;
import seedu.address.model.person.Person;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordType;
import seedu.address.model.statistics.AverageType;
import seedu.address.storage.Storage;
import seedu.address.ui.DisplayPaneType;
import seedu.sgm.model.food.Food;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private DisplayPaneType displayPaneType;
    private boolean newPaneIsToBeCreated;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        displayPaneType = command.getDisplayPaneType();
        newPaneIsToBeCreated = command.getnewPaneIsToBeCreated();
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
    public boolean getnewPaneIsToBeCreated() {
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
    public ReadOnlyCalendar getCalendar() {
        return model.getCalendar();
    }

    @Override
    public ObservableList<CalendarEntry> getFilteredCalendarEntryList() {
        return model.getFilteredCalendarEntryList();
    }
    //=========== Statistics List =============================================================

    @Override
    public AverageType getAverageType() {
        return model.getAverageType();
    }

    @Override
    public RecordType getRecordType() {
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

}
