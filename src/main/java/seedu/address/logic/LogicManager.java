package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.load.BankCommandResult;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

import static java.util.Objects.requireNonNull;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "File operation failed";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    private boolean gameStarted;
    private ModeEnum mode;

    /** temporary, will change when there is a better way to implement changing wordbanks **/
    private WordBankStatistics currWbStats;
    private Path currWbStatsPath;


    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        this.gameStarted = false;
        this.mode = ModeEnum.LOAD;
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

        // load the bank statistics on bank command
        // todo very messy... similar to the link below. Change when there is a better way to load wordbanks.
        // todo preferably, use the given storage class, since it is only used to save and not read currently.
        /** {@link seedu.address.logic.commands.switches.StartCommand#execute(Model)} **/

        if (commandResult instanceof BankCommandResult) {
            BankCommandResult bankCommandResult = (BankCommandResult) commandResult;
            String wbName = bankCommandResult.getSelectedWordBankName();
            String pathString = "data/" + wbName + ".json";
            Path filePath = Paths.get(pathString);
            currWbStatsPath = StorageManager.getWbStatsStoragePath(filePath);
            try {
                Optional<WordBankStatistics> optionalWbStats =  storage.readWordBankStatistics(currWbStatsPath);
                if (optionalWbStats.isPresent()) {
                    currWbStats = optionalWbStats.get();
                } else {
                    logger.fine("Cannot find wordbank statistics for [" + wbName + "]\n"
                        + "Proceeding with a blank statistics");
                    currWbStats = WordBankStatistics.getEmpty(wbName);
                }
            } catch (IOException | DataConversionException e) {
                throw new CommandException(FILE_OPS_ERROR_MESSAGE + e, e);
            }
        }

        /*
        Step 12.
        We save game here too.
        Similar methods to saveAddressBook();
         */
        try {
            ReadOnlyWordBank wb = model.getWordBank();
            Path filePath = Paths.get("data/" + wb.getName() + ".json");
            storage.saveAddressBook(model.getWordBank(), filePath);
            System.out.println("_____bank" + model.getWordBank().getName());
            for (Card c : wb.getCardList()) {
                System.out.println(c);
            }
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
    public ObservableList<WordBank> getFilteredWordBankList() {
        return model.getFilteredWordBankList();
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

    @Override
    public void saveUpdatedWbStatistics(GameStatistics gameStatistics) throws CommandException {
        try {
            requireNonNull(currWbStatsPath);
            currWbStats.update(gameStatistics);
            storage.saveWordBankStatistics(currWbStats, currWbStatsPath);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public WordBankStatistics getWordBankStatistics() {
        return currWbStats;
    }
}
