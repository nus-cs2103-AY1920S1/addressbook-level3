package seedu.algobase.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.Logic;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ModelType;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionResult;
import seedu.algobase.ui.action.UiLogic;
import seedu.algobase.ui.details.DetailsTabPane;
import seedu.algobase.ui.display.DisplayTab;
import seedu.algobase.ui.display.DisplayTabPane;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private UiLogic uiLogic;

    private ProblemListPanel problemListPanel;
    private TagListPanel tagListPanel;
    private PlanListPanel planListPanel;
    private FindRuleListPanel findRuleListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private SplitPane mainDisplayPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private SplitPane layoutPanePlaceholder;

    public MainWindow(Stage primaryStage, Logic logic, UiLogic uiLogic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.uiLogic = uiLogic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
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
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        DisplayTabPane displayTabPane = getDisplayTabPane();
        DetailsTabPane detailsTabPane = new DetailsTabPane(logic, this::executeUiAction);
        TaskManagementPane taskManagementPane = new TaskManagementPane(
            logic.getProcessedTaskList(),
            logic.getCurrentPlan(),
            logic.getCurrentDoneCount(),
            logic.getCurrentUndoneCount(),
            logic.getCurrentTaskCount()
        );

        layoutPanePlaceholder.getItems().add(displayTabPane.getRoot());
        layoutPanePlaceholder.getItems().add(detailsTabPane.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAlgoBaseFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, logic.getHistory());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        mainDisplayPlaceholder.getItems().add(taskManagementPane.getRoot());
        mainDisplayPlaceholder.setDividerPositions(0.66);
    }

    private DisplayTabPane getDisplayTabPane() {
        problemListPanel = new ProblemListPanel(
            logic.getProcessedProblemList(),
            this::executeUiAction
        );
        planListPanel = new PlanListPanel(
            logic.getProcessedPlanList(),
            this::executeUiAction
        );
        tagListPanel = new TagListPanel(logic.getProcessedTagList());
        findRuleListPanel = new FindRuleListPanel(logic.getProcessedFindRuleList());
        DisplayTab problemListPanelTab = new DisplayTab(ModelType.PROBLEM.getTabName(), problemListPanel);
        DisplayTab tagListPanelTab = new DisplayTab(ModelType.TAG.getTabName(), tagListPanel);
        DisplayTab planListPanelTab = new DisplayTab(ModelType.PLAN.getTabName(), planListPanel);
        DisplayTab findRuleListPaneTab = new DisplayTab(ModelType.FINDRULE.getTabName(), findRuleListPanel);
        return new DisplayTabPane(
            logic.getGuiState().getReadOnlyTabManager(),
            this::executeUiAction,
            problemListPanelTab,
            tagListPanelTab,
            planListPanelTab,
            findRuleListPaneTab
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
    private void handleHelp() {
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

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.algobase.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Executes a UI action returns the result.
     *
     * @see seedu.algobase.ui.action.UiLogic#execute(UiActionDetails)
     */
    private UiActionResult executeUiAction(UiActionDetails uiActionDetails) {
        try {
            UiActionResult uiActionResult = uiLogic.execute(uiActionDetails);
            uiActionResult.getFeedbackToUser().ifPresent((feedback) -> {
                logger.info("Result: " + feedback);
                resultDisplay.setFeedbackToUser(feedback);
            });

            if (uiActionResult.isShowHelp()) {
                handleHelp();
            }

            if (uiActionResult.isExit()) {
                handleExit();
            }

            return uiActionResult;
        } catch (UiActionException | ParseException e) {
            resultDisplay.setFeedbackToUser(e.getMessage());
            return new UiActionResult(false, Optional.empty());
        }
    }
}
