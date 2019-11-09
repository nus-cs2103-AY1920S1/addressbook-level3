package seedu.address.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.address.logic.parser.AddressBookParser;
import seedu.address.address.model.AddressBookModel;
import seedu.address.address.model.ReadOnlyAddressBook;
import seedu.address.address.model.person.Person;
import seedu.address.address.model.util.AddressBookStatistics;
import seedu.address.address.storage.AddressBookStorage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The main AddressBookLogicManager of the app.
 */
public class AddressBookLogicManager implements AddressBookLogic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(AddressBookLogicManager.class);

    private final AddressBookModel addressBookModel;
    private final AddressBookStorage addressBookStorage;
    private final AddressBookParser addressBookParser;

    public AddressBookLogicManager(AddressBookModel addressBookModel, AddressBookStorage addressBookStorage) {
        this.addressBookModel = addressBookModel;
        this.addressBookStorage = addressBookStorage;
        this.addressBookParser = new AddressBookParser();
    }


    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(addressBookModel);

        try {
            addressBookStorage.saveAddressBook(addressBookModel.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBookModel.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return addressBookModel.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return addressBookModel.getAddressBookFilePath();
    }

    @Override
    public AddressBookStatistics getStatistics() {
        return new AddressBookStatisticsManager();
    }

    /**
     * Local class for {@link AddressBookStatistics}
     */
    private class AddressBookStatisticsManager implements AddressBookStatistics {
        @Override
        public int getTotalPersons() {
            return addressBookModel.getTotalPersons();
        }

        @Override
        public XYChart.Series<Number, String> getAddressChartData() {
            return addressBookModel.getAddressChartData();
        }
    }
}
