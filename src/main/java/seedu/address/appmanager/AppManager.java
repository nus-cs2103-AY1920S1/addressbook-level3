package seedu.address.appmanager;

import java.nio.file.Path;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import seedu.address.appmanager.timer.GameTimer;
import seedu.address.commons.core.GuiSettings;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.gamecommands.GameCommandResult;
import seedu.address.logic.commands.switches.StartCommandResult;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.card.Card;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.GameStatisticsBuilder;
import seedu.address.statistics.WordBankStatistics;

/**
 * Class that wraps around the entire apps logic and the GameTimer. This is done to separate all logic
 * of the game away from the GameTimer entirely, and to separate all GameTimer from the UI itself.
 */
public class AppManager {

    private Logic logic;
    private GameTimer gameTimer = null;
    private TimerDisplayCallBack timerDisplayCallBack = null;
    // Call-back method to update ResultDisplay in MainWindow
    private HintDisplayCallBack hintDisplayCallBack = null;
    private MainWindowExecuteCallBack mainWindowExecuteCallBack = null;
    private GameStatisticsBuilder gameStatisticsBuilder = null;
    private QuestionDisplayCallBack questionDisplayCallBack = null;

    public AppManager(Logic logic) {
        this.logic = logic;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        logic.setGuiSettings(guiSettings);
    }

    private void setGameTimer(long timeAllowedPerQuestion, int hintFormatSize) {
        gameTimer = new GameTimer("Time Left", timeAllowedPerQuestion,
                this.mainWindowExecuteCallBack,
                this.timerDisplayCallBack,
                this::requestHintAndCallBack);
        if (logic.hintsAreEnabled()) {
            gameTimer.setHintTimingQueue(hintFormatSize, timeAllowedPerQuestion);
        }

    }

    /**
     * Checks if a GameTimer currently exists and terminates it if present.
     */
    private void abortAnyExistingGameTimer() {
        if (this.gameTimer != null) {
            this.gameTimer.abortTimer();
            this.gameTimer = null;
        }
    }

    /**
     * Processes the input command commandText by passing it through the GameManager's main logic.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    public CommandResult execute(String commandText) throws ParseException, CommandException {
        CommandResult commandResult = logic.execute(commandText);

        if (commandResult instanceof StartCommandResult) {
            StartCommandResult startCommandResult = (StartCommandResult) commandResult;
            initGameStatistics(startCommandResult.getTitle());
        }

        // handles game related actions
        if (commandResult instanceof GameCommandResult) {
            GameCommandResult gameCommandResult = (GameCommandResult) commandResult;

            // update statistics upon receiving a GameCommandResult with a Card
            if (gameCommandResult.getCard().isPresent()) {
                gameStatisticsBuilder.addDataPoint(gameCommandResult.getGameDataPoint(gameTimer.getElapsedMillis()),
                        gameCommandResult.getCard().get());
            }
            // should make logic save the updated game statistics
            if (gameCommandResult.isFinishedGame()) {
                abortAnyExistingGameTimer();
                logic.saveUpdatedWbStatistics(gameStatisticsBuilder.build());
                logic.incrementPlay();
            }
        }

        // GameTimer is always abort when a new command is entered while Game is running.
        abortAnyExistingGameTimer();

        if (commandResult.isPromptingGuess()) {
            setGameTimer(logic.getTimeAllowedPerQuestion(),
                    logic.getHintFormatSizeFromCurrentGame());
            Platform.runLater(() -> {
                this.questionDisplayCallBack.updateQuestionDisplay(logic.getCurrentQuestion());
                gameTimer.run();
            });
        }

        return commandResult;
    }

    public String getSelectedWbName() {
        return logic.getActiveWordBankStatistics().getWordBankName();
    }

    private void requestHintAndCallBack() {
        this.hintDisplayCallBack.updateHintDisplay(this.logic.getHintFormatFromCurrentGame().toString());
    }

    public Logic getLogic() {
        return logic;
    }

    public ReadOnlyWordBank getActiveWordBank() {
        return logic.getActiveWordBank();
    }

    public AppSettings getAppSettings() {
        return logic.getAppSettings();
    }

    public GameStatistics getGameStatistics() {
        return gameStatisticsBuilder.build();
    }

    public void initGameStatistics(String title) {
        gameStatisticsBuilder = new GameStatisticsBuilder(title);
    }

    public WordBankStatistics getActiveWordBankStatistics() {
        return logic.getActiveWordBankStatistics();
    }

    public WordBankStatisticsList getActiveWordBankStatisticsList() {
        return logic.getWordBankStatisticsList();
    }

    public GlobalStatistics getGlobalStatistics() {
        return logic.getGlobalStatistics();
    }

    public ObservableList<Card> getFilteredPersonList() {
        return logic.getFilteredPersonList();
    }

    public ObservableList<WordBank> getFilteredWordBankList() {
        return logic.getFilteredWordBankList();
    }

    public GuiSettings getGuiSettings() {
        return logic.getGuiSettings();
    }

    public Path getAddressBookFilePath() {
        return logic.getAddressBookFilePath();
    }

    public void setTimerDisplayCallBack(TimerDisplayCallBack updateTimerDisplay) {
        this.timerDisplayCallBack = updateTimerDisplay;
    }

    public void setHintDisplayCallBack(HintDisplayCallBack updateHintDisplay) {
        this.hintDisplayCallBack = updateHintDisplay;
    }

    public void setMainWindowExecuteCallBack(MainWindowExecuteCallBack mainWindowExecuteCallBack) {
        this.mainWindowExecuteCallBack = mainWindowExecuteCallBack;
    }

    public void setQuestionDisplayCallBack(QuestionDisplayCallBack questionDisplayCallBack) {
        this.questionDisplayCallBack = questionDisplayCallBack;
    }

    /**
     * Call-back functional interface for the GameManager to periodically update the TimerDisplay
     * component of the UI.
     */
    @FunctionalInterface
    public interface TimerDisplayCallBack {
        void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven);
    }

    /**
     * Call-back functional interface from GameManager to MainWindow to update the HintDisplay
     * component of the UI.
     */
    @FunctionalInterface
    public interface HintDisplayCallBack {
        void updateHintDisplay(String message);
    }

    /**
     * Call-back functional interface from GameManager to MainWindow, represents the GameManager sending
     * a command to the app as though it were another user.
     */
    @FunctionalInterface
    public interface MainWindowExecuteCallBack {
        CommandResult execute(String commandText) throws ParseException, CommandException;
    }

    /**
     * Call-back functional interface from GameManager to MainWindow to update the QuestionDisplay component
     * of the UI.
     */
    public interface QuestionDisplayCallBack {
        void updateQuestionDisplay(String message);
    }

}
