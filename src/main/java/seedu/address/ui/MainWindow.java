package seedu.address.ui;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.visual.DisplayFormat;
import seedu.address.model.visual.DisplayIndicator;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String LOGO_URL = "/images/InsurelyticsLogo.png";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private PolicyListPanel policyListPanel;
    private BinItemListPanel binItemListPanel;
    private HistoryListPanel historyListPanel;
    private DisplayPanel displayPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    private String rightPanelCommandText;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane listPanelPlaceholder;

    @FXML
    private StackPane displayPlaceHolder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private GridPane resultHolder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setLogo() {
        Image image = new Image(LOGO_URL);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        displayPlaceHolder.getChildren().add(imageView);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        listPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        setLogo();

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        resultHolder.prefHeightProperty().bind(((VBox) resultHolder.getParent()).heightProperty());
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
        logic.setUserSettings();
        helpWindow.hide();
        primaryStage.hide();
    }

    private void showListPolicy() {
        policyListPanel = new PolicyListPanel(logic.getFilteredPolicyList());
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(policyListPanel.getRoot());
    }

    private void showListPeople() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /** Show command history in right panel */
    private void showListHistory() {
        logger.info("Displaying command history...");
        historyListPanel = new HistoryListPanel(logic.getHistoryList());
        displayPlaceHolder.getChildren().removeAll();
        displayPlaceHolder.getChildren().add(historyListPanel.getRoot());
    }

    private void showListBin() {
        binItemListPanel = new BinItemListPanel(logic.getFilteredBinItemList());
        listPanelPlaceholder.getChildren().clear();
        listPanelPlaceholder.getChildren().add(binItemListPanel.getRoot());
    }

    /**
     * Shows display on right panel.
     */
    private void showListDisplay(CommandResult commandResult) throws ParseException {
        DisplayIndicator displayIndicator = commandResult.getDisplayIndicator();
        DisplayFormat displayFormat = commandResult.getDisplayFormat();
        DisplayController displayController = null;

        displayPlaceHolder.getChildren().clear();
        assert displayPlaceHolder.getChildren().isEmpty();
        switch (displayFormat.value) {
        case DisplayFormat.PIECHART:
            logger.info("Displaying piechart...");
            displayController = new PieChartController(logic, displayIndicator);
            break;
        case DisplayFormat.BARCHART:
            logger.info("Displaying barchart...");
            displayController = new BarChartController(logic, displayIndicator);
            break;
        case DisplayFormat.LINECHART:
            logger.info("Displaying linechart...");
            displayController = new LineChartController(logic, displayIndicator);
            break;
        default:
            throw new ParseException(DisplayFormat.getMessageConstraints());
        }

        requireNonNull(displayController);
        displayPlaceHolder.getChildren().add(displayController.getRoot());
    }

    /**
     * Expands person in right panel
     *
     * @param commandResult
     */
    private void showExpandPerson(CommandResult commandResult) {
        displayPanel = new DisplayPanel(commandResult.getPersonToExpand());
        displayPlaceHolder.getChildren().clear();
        assert displayPlaceHolder.getChildren().isEmpty();
        displayPlaceHolder.getChildren().add(displayPanel.getRoot());
    }

    /**
     * Expands policy in right panel
     *
     * @param commandResult
     */
    private void showExpandPolicy(CommandResult commandResult) {
        displayPanel = new DisplayPanel(commandResult.getPolicyToExpand());
        displayPlaceHolder.getChildren().clear();
        assert displayPlaceHolder.getChildren().isEmpty();
        displayPlaceHolder.getChildren().add(displayPanel.getRoot());
    }

    /**
     * Returns true if command result uses display place holder.
     *
     * @return boolean
     */
    private boolean usesRightPane(CommandResult commandResult) {
        return commandResult.isDisplay()
            || commandResult.isExpandPerson()
            || commandResult.isExpandPolicy()
            || commandResult.isListHistory();
    }

    /**
     * Renders right panel. Any logic execution here is always a system input.
     */
    private CommandResult renderRightPanel(String commandText) throws
        CommandException, ParseException {
        try {
            boolean isSystemInput = true;
            CommandResult commandResult = logic.execute(commandText, isSystemInput);
            logger.info("Result from render right panel: " + commandResult.getFeedbackToUser());

            if (commandResult.isListHistory()) {
                showListHistory();
            } else if (commandResult.isDisplay()) {
                showListDisplay(commandResult);
            } else if (commandResult.isExpandPerson()) {
                showExpandPerson(commandResult);
            } else if (commandResult.isExpandPolicy()) {
                showExpandPolicy(commandResult);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String, boolean)
     */
    private CommandResult executeCommand(String commandText, boolean isSystemInput) throws
        CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText, isSystemInput);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            } else if (commandResult.isExit()) {
                handleExit();
            } else if (commandResult.isListPolicy()) {
                showListPolicy();
            } else if (commandResult.isListPeople()) {
                showListPeople();
            } else if (commandResult.isListHistory()) {
                showListHistory();
            } else if (commandResult.isListBin()) {
                showListBin();
            } else if (commandResult.isDisplay()) {
                showListDisplay(commandResult);
            } else if (commandResult.isExpandPerson()) {
                showExpandPerson(commandResult);
            } else if (commandResult.isExpandPolicy()) {
                showExpandPolicy(commandResult);
            }

            if (usesRightPane(commandResult)) {
                rightPanelCommandText = commandText;
            } else if (!isNull(rightPanelCommandText)) {
                renderRightPanel(rightPanelCommandText);
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
