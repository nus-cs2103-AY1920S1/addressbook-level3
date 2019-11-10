package seedu.address.ui;

import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.CommandType;
import seedu.address.ui.listpanel.EntityListPanel;
import seedu.address.ui.listpanel.StatisticsListPanel;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StatisticsListPanel statisticListPanel;
    private EntityListPanel entityListPanel;
    private CommandListPanel commandListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private JFXButton lastFired;
    private AutoCompleteCommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private JFXButton participantsButton;

    @FXML
    private JFXButton leaderboardButton;

    @FXML
    private JFXButton teamsButton;

    @FXML
    private JFXButton mentorsButton;

    @FXML
    private JFXButton historyButton;

    @FXML
    private JFXButton homeButton;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Set minimum size of window
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(800);

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        lastFired = homeButton;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666 is fixed in later version of
         * SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will not
         * work when the focus is in them because the key event is consumed by the
         * TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is in
         * CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        // Displays the list of teams during application start up
        statisticListPanel = new StatisticsListPanel(logic.getStatistics());
        listPanelPlaceholder.getChildren().add(statisticListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        // Displays the file path for team list during start up for application
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getTeamListFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new AutoCompleteCommandBox(this::executeCommand);

        commandBox.requestFocus();
        commandBoxPlaceholder.getChildren().add(commandBox);
        setHandler();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                commandBox.requestFocus();
            }
        });
    }

    /**
     * Sets the handlers for the events generated whenever the alt modifier key, as
     * well as the up/down arrow keys are pressed.
     */
    private void setHandler() {
        final KeyCombination upCombo = new KeyCodeCombination(KeyCode.UP, KeyCombination.ALT_DOWN);
        final KeyCombination downCombo = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.ALT_DOWN);

        this.commandBoxPlaceholder.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (upCombo.match(ke)) {
                    commandBox.setTextField(logic.getPrevCommandString());
                }

                if (downCombo.match(ke)) {
                    commandBox.setTextField(logic.getNextCommandString());
                }

                if (KeyCode.ENTER == ke.getCode()) {
                    commandBox.handleCommandEntered();
                }
            }
        });
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

    /**
     * Closes the help window.
     */
    @FXML
    public void closeHelp() {
        helpWindow.hide();
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

    /**
     * Displays the statistics of total number or entities and its distribution.
     */
    @FXML
    private void displayStatistics() {
        statisticListPanel = new StatisticsListPanel(logic.getStatistics());
        listPanelPlaceholder.getChildren().set(0, statisticListPanel.getRoot());
        lastFired = homeButton;
    }

    /**
     * Displays the list of Participants in Model and Storage on Graphical User
     * Interface.
     */
    @FXML
    private void displayParticipantList() {
        entityListPanel = new EntityListPanel(logic.getFilteredParticipantList());
        listPanelPlaceholder.getChildren().set(0, entityListPanel.getRoot());
        lastFired = participantsButton;
    }

    /**
     * Displays the leaderboard on the Graphical User Interface.
     */
    @FXML
    private void displayLeaderboard() {
        entityListPanel = new EntityListPanel(logic.getSortedTeamList());
        listPanelPlaceholder.getChildren().set(0, entityListPanel.getRoot());
        lastFired = leaderboardButton;
    }

    /**
     * Displays the list of Teams in Model and Storage on Graphical User Interface.
     */
    @FXML
    private void displayTeamList() {
        entityListPanel = new EntityListPanel(logic.getFilteredTeamList());
        listPanelPlaceholder.getChildren().set(0, entityListPanel.getRoot());
        lastFired = teamsButton;
    }

    /**
     * Displays the list of Mentors in Model and Storage on Graphical User
     * Interface.
     */
    @FXML
    private void displayMentorList() {
        entityListPanel = new EntityListPanel(logic.getFilteredMentorList());
        listPanelPlaceholder.getChildren().set(0, entityListPanel.getRoot());
        lastFired = mentorsButton;
    }

    /**
     * Displays the Command History on Graphical User Interface.
     */
    @FXML
    private void displayHistory() {
        commandListPanel = new CommandListPanel(logic.getCommandHistory());
        listPanelPlaceholder.getChildren().set(0, commandListPanel.getRoot());
        lastFired = historyButton;
    }

    public EntityListPanel getEntityListPanel() {
        return entityListPanel;
    }

    /**
     * Disarms or resets all buttons so that a new command can be carried out. The
     * new command will arm a new button.
     */
    private void disarmAllButton() {
        // TODO: shorten this
        // Any ideas on how to shorten this method?
        if (homeButton.isArmed()) {
            homeButton.disarm();
        }
        if (participantsButton.isArmed()) {
            participantsButton.disarm();
        }
        if (teamsButton.isArmed()) {
            teamsButton.disarm();
        }
        if (mentorsButton.isArmed()) {
            mentorsButton.disarm();
        }
        if (historyButton.isArmed()) {
            historyButton.disarm();
        }

    }

    private void fireButton(Button button) throws AlfredModelHistoryException {
        disarmAllButton();
        button.arm();
        button.fire();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    @SuppressWarnings("checkstyle:Regexp")
    private CommandResult executeCommand(String commandText)
            throws CommandException, ParseException, AlfredModelHistoryException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            } else {
                closeHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            CommandType commandType = commandResult.getCommandType();
            if (commandType == null) {
                this.fireButton(lastFired);
                return commandResult;
            }

            // TODO: if the current panel is the one being changed, do not change the
            // entityListPlaceholder
            switch (commandType) {
            case M:
                this.fireButton(mentorsButton);
                break;
            case T:
                this.fireButton(teamsButton);
                break;
            case P:
                this.fireButton(participantsButton);
                break;
            case H:
                this.fireButton(historyButton);
                break;
            case L:
                this.fireButton(leaderboardButton);
                break;
            case HM:
                this.fireButton(homeButton);
                lastFired = homeButton;
                break;
            default:
                break;
            }
            return commandResult;
        } catch (CommandException | ParseException | AlfredModelHistoryException e) {
            if (lastFired != null) {
                this.fireButton(lastFired);
            }

            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
