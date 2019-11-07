package seedu.address.appmanager;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import seedu.address.appmanager.timer.GameTimer;
import seedu.address.commons.core.GuiSettings;

import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.gamecommands.GameCommandResult;
import seedu.address.logic.commands.gamecommands.SkipCommand;
import seedu.address.logic.commands.switches.StartCommandResult;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.card.Card;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbank.WordBank;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.GameStatisticsBuilder;
import seedu.address.storage.Storage;

/**
 * Class that serves as a hub for communication between the GUI and the internal components.
 * This is done to separate all logic of the game away from the GameTimer entirely.
 */
public class AppManager {

    private Logic logic;

    /** GameTimer object to keep track of time elapsed during Game mode. */
    private GameTimer gameTimer;

    /** Call-back methods that AppManager is dependent on. */
    private TimerDisplayCallBack timerDisplayCallBack;
    private HintDisplayCallBack hintDisplayCallBack;
    private MainWindowExecuteCallBack mainWindowExecuteCallBack;
    private QuestionDisplayCallBack questionDisplayCallBack;

    private GameStatisticsBuilder gameStatisticsBuilder;


    public AppManager(Logic logic) {
        requireAllNonNull(logic);
        this.logic = logic;
    }

    /**
     * Sets the gameTimer instance to be a new GameTimer object with the appropriate {@code timeAllowedPerQuestion}
     * based on the requested Difficulty setting.
     *
     * If hints are enabled, the {@code hintFormatSize} of the current
     * question will be used to initialize the HintTimingQueue in the GameTimer.
     */
    private void setGameTimer(long timeAllowedPerQuestion, int hintFormatSize) {
        gameTimer = GameTimer.getInstance("Time Left", timeAllowedPerQuestion,
                this::skipOverToNextQuestion,
                this::updateTimerDisplay,
                this::updateHints);

        if (logic.hintsAreEnabled()) {
            gameTimer.initHintTimingQueue(hintFormatSize, timeAllowedPerQuestion);
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
     * Processes the input command commandText by passing it through the AppManager's main logic.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    public CommandResult execute(String commandText) throws ParseException, CommandException {
        CommandResult commandResult = logic.execute(commandText);

        if (commandResult instanceof StartCommandResult) {
            StartCommandResult startCommandResult = (StartCommandResult) commandResult;
            initGameStatistics(startCommandResult.getTitle()); // initialize game statistics building
        }

        // handles game related actions
        if (commandResult instanceof GameCommandResult) {
            GameCommandResult gameCommandResult = (GameCommandResult) commandResult;

            // update statistics upon receiving a GameCommandResult with a Card
            updateGameStatistics(gameCommandResult);
            // should make logic save the updated game statistics
            if (gameCommandResult.isFinishedGame()) {
                abortAnyExistingGameTimer();
                GameStatistics gameStatistics = gameStatisticsBuilder.build();
                logic.updateStatistics(gameStatisticsBuilder.build());
                logic.updateRevisionBank(gameStatisticsBuilder.build());
            }
        }

        /* AppManager will always abort Timer when a new valid command is entered while Game is running. */
        abortAnyExistingGameTimer();

        if (commandResult.isPromptingGuess()) {
            setGameTimer(logic.getTimeAllowedPerQuestion(), logic.getHintFormatSizeFromCurrentGame());

            Platform.runLater(() -> {

                /* Call-back to UI to update QuestionDisplay with current Question. */
                this.questionDisplayCallBack.updateQuestionDisplay(logic.getCurrentQuestion());

                /* Starts the initialized GameTimer for this current Card. */
                gameTimer.run();
            });
        }

        return commandResult;
    }

    /**
     * Update the {@code gameStatisticsBuilder} with the {@code gameCommandResult}.
     */
    private void updateGameStatistics(GameCommandResult gameCommandResult) {
        if (gameCommandResult.getCard().isPresent()) {
            gameStatisticsBuilder.addDataPoint(
                    gameCommandResult.getGameDataPoint(gameTimer.getElapsedMillis()),
                    gameCommandResult.getCard().get());
        }
    }
    public String getSelectedWbName() {
        return logic.getActiveWordBankStatistics().getWordBankName();
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        logic.setGuiSettings(guiSettings);
    }

    /**
     * Gets the logic object from itself.
     * @return Logic instance
     */
    public Logic getLogic() {
        return logic;
    }

    public Storage getStorage() {
        return logic.getStorage();
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

    public ObservableList<Card> getFilteredCardList() {
        return logic.getFilteredCardList();
    }

    public ObservableList<WordBank> getFilteredWordBankList() {
        return logic.getFilteredWordBankList();
    }

    public GuiSettings getGuiSettings() {
        return logic.getGuiSettings();
    }

    public Path getWordBanksFilePath() {
        return logic.getWordBanksFilePath();
    }

    // <---------------------------------------- CallBacks to Pass Into GameTimer-------------------------------->

    private void updateHints() {
        this.hintDisplayCallBack.updateHintDisplay(this.logic.getHintFormatFromCurrentGame().toString());
    }

    private void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven) {
        this.timerDisplayCallBack.updateTimerDisplay(timerMessage, timeLeft, totalTimeGiven);
    }

    /**
     * Calls-back to the UI to simulate a `skip` command being passed into the program.
     */
    private void skipOverToNextQuestion() {
        try {
            this.mainWindowExecuteCallBack.execute(SkipCommand.COMMAND_WORD);
        } catch (ParseException | CommandException e) {
            e.printStackTrace();
        }
    }

    // <-----------------------Methods to register UI components to be called back by AppManager----------------->

    /**
     * Registers a method that will be called by the AppManager to update the UI's TimerDisplay
     * @param updateTimerDisplay Method to register.
     */
    public void registerTimerDisplayCallBack(TimerDisplayCallBack updateTimerDisplay) {
        requireAllNonNull(updateTimerDisplay);
        this.timerDisplayCallBack = updateTimerDisplay;
    }

    /**
     * Registers a method that will be called by the AppManager to update the UI's HintDisplay
     * @param updateHintDisplay Method to register.
     */
    public void registerHintDisplayCallBack(HintDisplayCallBack updateHintDisplay) {
        requireAllNonNull(updateHintDisplay);
        this.hintDisplayCallBack = updateHintDisplay;
    }

    /**
     * Registers a method that will be called by the AppManager to simulate a 'skip' command as though
     * it were a user.
     * @param mainWindowExecuteCallBack Method to register.
     */
    public void registerMainWindowExecuteCallBack(MainWindowExecuteCallBack mainWindowExecuteCallBack) {
        requireAllNonNull(mainWindowExecuteCallBack);
        this.mainWindowExecuteCallBack = mainWindowExecuteCallBack;
    }

    /**
     * Registers a method that will be called by the AppManager to up the UI's QuestionDisplay.
     * @param questionDisplayCallBack Method to register.
     */
    public void registerQuestionDisplayCallBack(QuestionDisplayCallBack questionDisplayCallBack) {
        requireAllNonNull(questionDisplayCallBack);
        this.questionDisplayCallBack = questionDisplayCallBack;
    }

    /**
     * Call-back functional interface for the AppManager to periodically update the TimerDisplay
     * component of the UI.
     */
    @FunctionalInterface
    public interface TimerDisplayCallBack {
        void updateTimerDisplay(String timerMessage, long timeLeft, long totalTimeGiven);
    }

    /**
     * Call-back functional interface from AppManager to MainWindow to update the HintDisplay
     * component of the UI.
     */
    @FunctionalInterface
    public interface HintDisplayCallBack {
        void updateHintDisplay(String message);
    }

    /**
     * Call-back functional interface from AppManager to MainWindow, represents the AppManager sending
     * a command to the app as though it were another user.
     */
    @FunctionalInterface
    public interface MainWindowExecuteCallBack {
        CommandResult execute(String commandText) throws ParseException, CommandException;
    }

    /**
     * Call-back functional interface from AppManager to MainWindow to update the QuestionDisplay component
     * of the UI.
     */
    @FunctionalInterface
    public interface QuestionDisplayCallBack {
        void updateQuestionDisplay(String message);
    }

}
