package seedu.address.logic.calendar;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.calendar.commands.Command;
import seedu.address.logic.calendar.commands.CommandResult;
import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.logic.calendar.parser.AddressBookParser;
import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.ReadOnlyCalendarAddressBook;
import seedu.address.model.calendar.person.Task;
import seedu.address.storage.calendar.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final CalendarModel calendarModel;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    public LogicManager(CalendarModel calendarModel, Storage storage) {
        this.calendarModel = calendarModel;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(calendarModel);

        try {
            //We can deduce that the previous line of code modifies calendarModel in some way
            // since it's being stored here.
            storage.saveAddressBook(calendarModel.getCalendarAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCalendarAddressBook getAddressBook() {
        return calendarModel.getCalendarAddressBook();
    }

    @Override
    public ObservableList<Task> getFilteredPersonList() {
        return calendarModel.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return calendarModel.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return calendarModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        calendarModel.setGuiSettings(guiSettings);
    }
}
