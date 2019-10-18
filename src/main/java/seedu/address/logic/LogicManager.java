package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.Storage;


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

        // todo need to save wordbankstatistics after deletion.
        // todo possible solution -> just save on every command like how the word bank is saved.
        // todo currently, on deletion, the statistics is deleted on the model, and will be saved only if
        // todo a game is played with that word bank. If no game is played, and the app is closed, the statistics
        // todo will stay there forever...
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
            requireNonNull(model.getWordBankStatistics());
            WordBankStatistics currWbStats = model.getWordBankStatistics();
            currWbStats.update(gameStatistics);
            storage.saveWordBankStatistics(currWbStats,
                    Path.of("data/wbstats/" + currWbStats.getWordBankName() + ".json"));
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public WordBankStatistics getActiveWordBankStatistics() {
        return model.getWordBankStatistics();
    }

    @Override
    public ReadOnlyWordBank getActiveWordBank() {
        return model.getWordBank();
    }

    @Override
    public long getTimeAllowedPerQuestion() {
        return this.model.getDifficulty().getTimeAllowedPerQuestion();
    }
}
