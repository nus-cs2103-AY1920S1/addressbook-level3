package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.cardcommands.CardCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.wordbankcommands.WordBankCommandResult;
import seedu.address.logic.parser.ParserManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.Model;
import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.card.Card;
import seedu.address.model.card.FormattedHint;
import seedu.address.model.card.Meaning;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbank.exceptions.DuplicateWordBankException;
import seedu.address.model.wordbank.exceptions.WordBankNotFoundException;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic, UiLogicHelper {
    private static final String FILE_OPS_ERROR_MESSAGE = "File operation failed";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;

    private final Storage storage;

    private final ParserManager parserManager;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        parserManager = new ParserManager();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        // @@author sreesubbash
        CommandResult commandResult;
        parserManager.updateState(model.getHasBank(), model.gameIsOver());
        Command command = parserManager.parseCommand(commandText);
        commandResult = command.execute(model);
        parserManager.updateState(model.getHasBank(), model.gameIsOver());
        // @@author

        try {
            if (getMode().equals(ModeEnum.SETTINGS)) {
                storage.saveAppSettings(model.getAppSettings(), model.getAppSettingsFilePath());
            }

            if (commandResult instanceof CardCommandResult) {
                CardCommandResult cardCommandResult = (CardCommandResult) commandResult;
                cardCommandResult.updateStorage(storage, (WordBank) model.getCurrentWordBank());
            }

            if (commandResult instanceof WordBankCommandResult) {
                WordBankCommandResult wordBankCommandResult = (WordBankCommandResult) commandResult;
                wordBankCommandResult.updateStorage(storage);
            }

        } catch (DataConversionException e) {
            logger.info("Corrupted word bank file found.");
            commandResult = new CommandResult("Word bank file is corrupted.");
        } catch (WordBankNotFoundException e) {
            logger.info("Word bank file is not found.");
            commandResult = new CommandResult(e.getMessage());
        } catch (IllegalValueException | DuplicateWordBankException e) {
            logger.info("Word bank file has invalid values.");
            commandResult = new CommandResult(e.getMessage());

        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public Model getModel() {
        return model;
    }

    // <------------------------------ WordBank ------------------------------------------------------------>

    @Override
    public ReadOnlyWordBank getCurrentWordBank() {
        return model.getCurrentWordBank();
    }

    @Override
    public ObservableList<Card> getFilteredCardList() {
        return model.getFilteredCardList();
    }

    @Override
    public ObservableList<WordBank> getFilteredWordBankList() {
        return storage.getFilteredWordBankList();
    }

    @Override
    public Path getWordBanksFilePath() {
        return model.getWordBankFilePath();
    }

    // <------------------------------ Revision bank ---------------------------------------------------------->

    /**
     * Updates the revision bank, to add all wrong cards,
     * and remove all correct cards in any game session when it ends.
     *
     * @param gameStatistics provides the wrong and correct cards needed.
     */
    @Override
    public void updateRevisionBank(GameStatistics gameStatistics) {
        try {
            storage.createWordBank("revision");
        } catch (DuplicateWordBankException e) {
            logger.info("Revision bank already exist");
        }

        List<Card> wrongCards = gameStatistics.getWrongCards();
        List<Card> correctCards = gameStatistics.getCorrectCards();
        WordBank revisionBank = storage.getWordBankFromName("revision");

        if (gameStatistics.getTitle().equals("revision")) {
            for (Card c : correctCards) {
                if (revisionBank.hasCard(c)) {
                    revisionBank.removeCard(c);
                }
            }
            storage.updateWordBank(revisionBank);
        } else {
            for (Card c : wrongCards) {
                Meaning newMeaning = new Meaning(c.getMeaning().getValue()
                        + " [Word Bank: " + gameStatistics.getTitle() + "]", true);
                Card toAdd = Card.createNewCard(c.getWord(), newMeaning, c.getTags());
                if (!revisionBank.hasCard(toAdd)) {
                    revisionBank.addCard(toAdd);
                }
            }

            for (Card c : correctCards) {
                Meaning newMeaning = new Meaning(c.getMeaning().getValue()
                        + " [Word Bank: " + gameStatistics.getTitle() + "]", true);
                Card toRemove = Card.createNewCard(c.getWord(), newMeaning, c.getTags());
                if (revisionBank.hasCard(toRemove)) {
                    Card cardInRevisionBank = revisionBank.getCard(toRemove.getMeaning());
                    revisionBank.removeCard(cardInRevisionBank);
                }
            }
            storage.updateWordBank(revisionBank);
        }
    }

    // <------------------------------ Statistics ---------------------------------------------------------->

    /**
     * Increments the number of play in global statistics.
     */
    private void incrementPlay() throws CommandException {
        try {
            requireNonNull(model.getGlobalStatistics());
            GlobalStatistics globalStats = model.getGlobalStatistics();
            globalStats.addPlay();
            storage.saveGlobalStatistics(globalStats);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public void updateStatistics(GameStatistics gameStatistics) throws CommandException {
        try {
            WordBankStatistics currWbStats;
            if (model.getWordBankStatistics() == null) {
                currWbStats = WordBankStatistics.getEmpty("sample");
            } else {
                currWbStats = model.getWordBankStatistics();
            }
            currWbStats.update(gameStatistics, model.getCurrentGameDifficulty());

            Path targetPath = Path.of(model.getUserPrefs().getDataFilePath().toString(), "wbstats",
                    currWbStats.getWordBankName() + ".json");

            storage.saveWordBankStatistics(currWbStats, targetPath);
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
        incrementPlay();
    }

    @Override
    public WordBankStatistics getActiveWordBankStatistics() {
        return model.getWordBankStatistics();
    }

    @Override
    public WordBankStatisticsList getWordBankStatisticsList() {
        return model.getWordBankStatisticsList();
    }

    @Override
    public GlobalStatistics getGlobalStatistics() {
        return model.getGlobalStatistics();
    }

    // <------------------------------ Game Related -------------------------------------------------------->

    @Override
    public ReadOnlyWordBank getActiveWordBank() {
        return model.getCurrentWordBank();
    }

    @Override
    public String getCurrentQuestion() {
        return model.getGame().getCurrQuestion();
    }

    @Override
    public long getTimeAllowedPerQuestion() {
        return this.model.getTimeAllowedPerQuestion();
    }

    @Override
    public FormattedHint getHintFormatFromCurrentGame() {
        return this.model.getHintFormatFromCurrentGame();
    }

    @Override
    public int getHintFormatSizeFromCurrentGame() {
        return this.model.getHintFormatSizeFromCurrentGame();
    }

    // <------------------------------ Parser related ------------------------------------------------------>

    @Override
    public List<AutoFillAction> getMenuItems(String text) {
        return parserManager.getAutoFill(text);
    }

    @Override
    public ModeEnum getMode() {
        return parserManager.getMode();
    }

    @Override
    public List<ModeEnum> getModes() {
        return parserManager.getModes();
    }

    // <------------------------------ AppSettings and UserPrefs ------------------------------------------->

    @Override
    public AppSettings getAppSettings() {
        return this.model.getAppSettings();
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
    public boolean hintsAreEnabled() {
        return model.getHintsEnabled();
    }

}
