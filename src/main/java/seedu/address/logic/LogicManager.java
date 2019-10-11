package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.GameCommand;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.switchmode.HomeCommand;
import seedu.address.logic.commands.switchmode.StartCommand;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.ReadOnlyWordBank;
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
    private GameLogic gameLogic;

    private boolean gameStarted;
    private ModeEnum mode;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.gameStarted = false;
        this.mode = ModeEnum.LOAD;
        this.gameLogic = new GameLogic(model);
        /*
        Step 9.
        this.game = game //get from constructor
         */
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        /*
        Step 10.
        Modify parseCommand()
        2 user modes: Game mode and Normal mode
        */
        Command command = addressBookParser.parseCommand(commandText);

        /*
        Step 11.
        Extends to Step 13 in Command.java

        commandResult = command.execute(model, game);
         */
        //commandResult = command.execute(model);

        /* Checks if command entered in wrong mode */
        this.mode = command.check(model, mode);
        commandResult = command.execute(model);

        /*
        Step 12.
        We save game here too.
        Similar methods to saveAddressBook();
         */
        try {
            storage.saveAddressBook(model.getWordBank());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyWordBank getAddressBook() {
        return model.getWordBank();
    }

    @Override
    public ObservableList<Card> getFilteredPersonList() {
        return model.getFilteredCardList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getWordBankFilePath();
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
