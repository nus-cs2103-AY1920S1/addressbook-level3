package seedu.ezwatchlist.ui;

import java.util.logging.Logger;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.ezwatchlist.api.exceptions.NoRecommendationsException;
import seedu.ezwatchlist.api.exceptions.OnlineConnectionException;
import seedu.ezwatchlist.commons.core.GuiSettings;
import seedu.ezwatchlist.commons.core.LogsCenter;
import seedu.ezwatchlist.logic.Logic;

import seedu.ezwatchlist.logic.commands.CommandResult;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;
import seedu.ezwatchlist.logic.parser.exceptions.ParseException;
import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.statistics.Statistics;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    public static final String MAIN_TAB = "watch-list";
    public static final String WATCHED_TAB = "watched-list";
    public static final String SEARCH_TAB = "search-list";
    public static final String STATISTICS_TAB = "statistics tab";

    private static final String ACCELERATOR_ERROR = "setAccelerator must be called when button is attached to a scene";

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private String currentTab;
    private Boolean isSearchLoading = false;
    private Boolean isChangedList = true;
    private Statistics statistics;

    // Independent Ui parts residing in this Ui container
    private ShowListPanel showListPanel;
    private WatchedPanel watchedPanel;
    private SearchPanel searchPanel;
    private StatisticsPanel statisticsPanel;
    private LoadingPanel loadingPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    @FXML
    private StackPane resultDisplayPlaceHolder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane contentPanelPlaceholder;

    @FXML
    private Button watchlistButton;

    @FXML
    private Button watchedButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button statisticsButton;

    private Button currentButton;

    public MainWindow(Stage primaryStage, Logic logic, Statistics statistics) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.primaryStage.setTitle("Ezwatchlist");
        this.currentTab = MAIN_TAB;
        this.statistics = statistics;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ShowListPanel getShowListPanel() {
        return showListPanel;
    }

    public Logic getLogic() {
        return logic;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of main window.
     */
    void fillInnerParts() {
        showListPanel = new ShowListPanel(logic.getUnWatchedList());
        showListPanel.setMainWindow(this);
        watchedPanel = new WatchedPanel(logic.getWatchedList());
        watchedPanel.setMainWindow(this);
        searchPanel = new SearchPanel(logic.getSearchResultList());
        searchPanel.setMainWindow(this);
        loadingPanel = new LoadingPanel();

        contentPanelPlaceholder.getChildren().add(showListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceHolder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        commandBox.setMainWindow(this);

        watchlistButton.getStyleClass().removeAll("button");
        watchlistButton.getStyleClass().add("button-current");

        currentButton = watchlistButton;
        setSearchAccelerator(searchButton);
        setWatchListAccelerator(watchlistButton);
        setWatchedAccelerator(watchedButton);
        setStatisticsAccelerator(statisticsButton);
    }

    private void setSearchAccelerator(final Button button) {
        Scene scene = button.getScene();
        if (scene == null) {
            throw new IllegalArgumentException(ACCELERATOR_ERROR);
        }
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.DIGIT3),
                new Runnable() {
                    @Override public void run() {
                        goToSearch();
                    }
                }
        );
    }

    private void setWatchListAccelerator(final Button button) {
        Scene scene = button.getScene();
        if (scene == null) {
            throw new IllegalArgumentException(ACCELERATOR_ERROR);
        }
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.DIGIT1),
                new Runnable() {
                    @Override public void run() {
                        goToWatchlist();
                    }
                }
        );
    }

    private void setWatchedAccelerator(final Button button) {
        Scene scene = button.getScene();
        if (scene == null) {
            throw new IllegalArgumentException(ACCELERATOR_ERROR);
        }
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.DIGIT2),
                new Runnable() {
                    @Override public void run() {
                        goToWatched();
                    }
                }
        );
    }

    private void setStatisticsAccelerator(final Button button) {
        Scene scene = button.getScene();
        if (scene == null) {
            throw new IllegalArgumentException(ACCELERATOR_ERROR);
        }
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.DIGIT4),
                new Runnable() {
                    @Override public void run() {
                        try {
                            goToStatistics();
                        } catch (NoRecommendationsException e) {
                            e.printStackTrace();
                        } catch (OnlineConnectionException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public ResultDisplay getResultDisplay() {
        return resultDisplay;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String, MainWindow, String)
     */
    public CommandResult executeCommand(String commandText)
            throws CommandException, ParseException, OnlineConnectionException, NoRecommendationsException {
        try {
            switch (currentTab) {
            case (MAIN_TAB):
                logic.updateFilteredShowList(Model.PREDICATE_UNWATCHED_SHOWS);
                break;
            case (WATCHED_TAB):
                logic.updateFilteredShowList(Model.PREDICATE_WATCHED_SHOWS);
                break;
            case (SEARCH_TAB):
                logic.updateFilteredShowList(Model.PREDICATE_NO_SHOWS);
                break;
            case (STATISTICS_TAB):
                logic.updateFilteredShowList(Model.PREDICATE_NO_SHOWS);
                break;
            default:
                break;
            }
            CommandResult commandResult = logic.execute(commandText, this, currentTab);
            if (!isSearchLoading) {
                logger.info("Result: " + commandResult.getFeedbackToUser());
                resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
                if (commandResult.isShowHelp()) {
                    handleHelp();
                }
                if (commandResult.isExit()) {
                    handleExit();
                }
                if (commandResult.isShortCutKey()) {
                    handleShortCutKey(commandResult.getFeedbackToUser());
                }
                if (commandResult.isChangedList()) {
                    isChangedList = true;
                }
                return commandResult;
            }
            return commandResult;

        //catch ParseException here to implement spellcheck
        } catch (CommandException | ParseException | OnlineConnectionException | NoRecommendationsException e) {

            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * execute short cut key on UI
     * @param feedbackToUser
     * @throws NoRecommendationsException
     * @throws OnlineConnectionException
     */
    private void handleShortCutKey(String feedbackToUser) throws NoRecommendationsException, OnlineConnectionException {
        switch (feedbackToUser) {

        case "Watchlist":
            goToWatchlist();
            return;

        case "Watchedlist":
            goToWatched();
            return;

        case "Search":
            goToSearch();
            return;

        case "Statistics":
            goToStatistics();
            return;

        default:
            return;
        }
    }

    /**
     * Populates the contentPanel with watchlist content
     */
    @FXML
    public void goToWatchlist() {
        contentPanelPlaceholder.getChildren().clear();
        contentPanelPlaceholder.getChildren().add(showListPanel.getRoot());
        logic.updateFilteredShowList(Model.PREDICATE_UNWATCHED_SHOWS);
        currentTab = MAIN_TAB;
        move(currentButton, watchlistButton);
        currentButton = watchlistButton;
    }

    /**
     * Populates the contentPanel with watched list content
     */
    @FXML
    public void goToWatched() {
        contentPanelPlaceholder.getChildren().clear();
        contentPanelPlaceholder.getChildren().add(watchedPanel.getRoot());
        logic.updateFilteredShowList(Model.PREDICATE_WATCHED_SHOWS);
        currentTab = WATCHED_TAB;
        move(currentButton, watchedButton);
        currentButton = watchedButton;
    }

    /**
     * Populates the contentPanel with search content
     */
    @FXML
    public void goToSearch() {
        if (isSearchLoading) {
            contentPanelPlaceholder.getChildren().clear();
            contentPanelPlaceholder.getChildren().add(loadingPanel.getRoot());
        } else {
            contentPanelPlaceholder.getChildren().clear();
            contentPanelPlaceholder.getChildren().add(searchPanel.getRoot());
        }
        currentTab = SEARCH_TAB;
        move(currentButton, searchButton);
        currentButton = searchButton;
    }

    /**
     * Populates the contentPanel with statistics content
     */
    @FXML
    public void goToStatistics() throws NoRecommendationsException, OnlineConnectionException {
        if (isChangedList) {
            try {
                contentPanelPlaceholder.getChildren().clear();
                contentPanelPlaceholder.getChildren().add(loadingPanel.getRoot());

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            statisticsPanel = new StatisticsPanel(statistics.getForgotten(),
                                    statistics.getFavouriteGenre(), statistics.getMovieRecommendations(),
                                    statistics.getTvShowRecommendations());
                        } catch (OnlineConnectionException e) {
                            statisticsPanel = new StatisticsPanel(statistics.getForgotten(),
                                    statistics.getFavouriteGenre(),
                                    null, null);
                            resultDisplay.setFeedbackToUser("Note: You are not connected to the internet!");
                        }
                        return null;
                    }
                };
                task.setOnSucceeded(evt -> {
                    contentPanelPlaceholder.getChildren().clear();
                    contentPanelPlaceholder.getChildren().add(statisticsPanel.getRoot());
                    isChangedList = false;
                    currentTab = STATISTICS_TAB;
                    move(currentButton, statisticsButton);
                    currentButton = statisticsButton;
                });
                new Thread(task).start();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            contentPanelPlaceholder.getChildren().clear();
            contentPanelPlaceholder.getChildren().add(statisticsPanel.getRoot());
            isChangedList = false;
            currentTab = STATISTICS_TAB;
            move(currentButton, statisticsButton);
            currentButton = statisticsButton;
        }
    }

    /**
     * Changes the style of the button when changing panels.
     * @param a the button representing the current panel
     * @param b the button representing the button clicked
     */
    public void move(Button a, Button b) {
        a.getStyleClass().removeAll("button-current");
        a.getStyleClass().add("button");
        b.getStyleClass().removeAll("button");
        b.getStyleClass().add("button-current");
    }

    public String getCurrentTab() {
        return currentTab;
    }

    /**
     * For logic manager. If command is search, searchResultLogger will be called so logic can update this UI (Main
     * Window)
     * @param commandResult
     */
    public void searchResultLogger(CommandResult commandResult) {
        logger.info("Result: " + commandResult.getFeedbackToUser());
        resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

    }

    public void setIsSearchLoading() {
        isSearchLoading = !isSearchLoading;
    }


}
