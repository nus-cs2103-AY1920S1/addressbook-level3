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
import seedu.address.logic.commands.switches.StartCommandResult;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.appsettings.AppSettings;
import seedu.address.model.card.Card;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.wordbank.ReadOnlyWordBank;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.GameStatistics;
import seedu.address.statistics.GameStatisticsBuilder;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.storage.Storage;

/**
 * Class that serves as a hub for communication between the GUI and The internal components.
 * This is done to separate all logic of the game away from the GameTimer entirely.
 */
public class AppManager {

    private Logic logic;
    private GameTimer gameTimer;
    private TimerDisplayCallBack timerDisplayCallBack;
    // Call-back method to update ResultDisplay in MainWindow
    private HintDisplayCallBack hintDisplayCallBack;
    private MainWindowExecuteCallBack mainWindowExecuteCallBack;
    private GameStatisticsBuilder gameStatisticsBuilder;
    private QuestionDisplayCallBack questionDisplayCallBack;

    public AppManager(Logic logic) {
        requireAllNonNull(logic);
        this.logic = logic;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        logic.setGuiSettings(guiSettings);
    }

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
            if (gameCommandResult.getCard().isPresent()) {
                gameStatisticsBuilder.addDataPoint(
                        gameCommandResult.getGameDataPoint(gameTimer.getElapsedMillis()),
                        gameCommandResult.getCard().get());
            }
            // should make logic save the updated game statistics
            if (gameCommandResult.isFinishedGame()) {
                abortAnyExistingGameTimer();
                logic.saveUpdatedWbStatistics(gameStatisticsBuilder.build());
                logic.incrementPlay();
            }
        }

        // AppManager will always abort Timer when a new valid command is entered while Game is running.
        abortAnyExistingGameTimer();

        if (commandResult.isPromptingGuess()) {
            setGameTimer(logic.getTimeAllowedPerQuestion(),
                    logic.getHintFormatSizeFromCurrentGame());
            Platform.runLater(() -> {

                /** Call-back to UI to update QuestionDisplay with current Question. */
                this.questionDisplayCallBack.updateQuestionDisplay(logic.getCurrentQuestion());
                gameTimer.run();
            });

        }

        return commandResult;
    }

    public String getSelectedWbName() {
        return logic.getActiveWordBankStatistics().getWordBankName();
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

    public ObservableList<Card> getFilteredPersonList() {
        return logic.getFilteredCardList();
    }

    public GuiSettings getGuiSettings() {
        return logic.getGuiSettings();
    }

    public Path getAddressBookFilePath() {
        return logic.getWordBanksFilePath();
    }

    // <---------------------------------------- CallBacks to Pass Into Timer------------------------------------>

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
            this.mainWindowExecuteCallBack.execute("skip");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    // <---------------------------Methods to register UI components to be called back by AppManager------------->

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
