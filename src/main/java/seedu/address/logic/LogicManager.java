package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.TimeBook;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplay;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

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
        commandResult = command.execute(model);

        try {
            storage.saveTimeBook(model.getTimeBook());
            logger.info("Attempting to save timebook");

        } catch (IOException ioe) {
            logger.severe("Unable to save timebook");
        }
        return commandResult;
    }

    @Override
    public TimeBook getTimeBook() {
        return model.getTimeBook();
    }

    //=========== UI Model =============================================================

    @Override
    public DetailWindowDisplay getMainWindowDisplay() {
        return model.getDetailWindowDisplay();
    }

    @Override
    public SidePanelDisplay getSidePanelDisplay() {
        return model.getSidePanelDisplay();
    }


    //=========== Suggesters =============================================================

    @Override
    public ArrayList<String> personSuggester(String prefix) {
        return model.personSuggester(prefix);
    }

    @Override
    public ArrayList<String> personSuggester(String prefix, String groupName) {
        return model.personSuggester(prefix, groupName);
    }

    @Override
    public ArrayList<String> groupSuggester(String prefix) {
        return model.groupSuggester(prefix);
    }

    //=========== Legacy =============================================================


    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Group> getGroupList() {
        //To Do.
        return model.getObservableGroupList();
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

}
