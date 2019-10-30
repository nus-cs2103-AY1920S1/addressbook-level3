package seedu.jarvis.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.jarvis.commons.core.GuiSettings;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.ui.address.PersonListView;
import seedu.jarvis.ui.cca.CcaListView;
import seedu.jarvis.ui.course.CoursePlannerWindow;
import seedu.jarvis.ui.finance.FinanceListView;
import seedu.jarvis.ui.template.View;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final int PLANNER_INDEX = 0;
    private static final int MODULES_INDEX = 1;
    private static final int CCAS_INDEX = 2;
    private static final int FINANCES_INDEX = 3;
    private static final String MESSAGE_VIEW_NOT_IMPLEMENTED = "View not yet implemented.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private Model model;
    private CommandUpdater commandUpdater;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane tabPanePlaceHolder;

    @FXML
    private StackPane plannerContentPlaceholder;

    @FXML
    private StackPane moduleContentPlaceholder;

    @FXML
    private StackPane ccaContentPlaceholder;

    @FXML
    private StackPane financeContentPlaceholder;

    @FXML
    private StackPane historyDisplayPlaceHolder;

    public MainWindow(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.model = model;

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
            if (event.getCode() == KeyCode.TAB) {
                event.consume();
                model.setViewStatus(ViewType.getNextViewType(model.getViewStatus().getViewType()));
                handleSwitch();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.jarvis.logic.Logic#execute(String)
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

            if (commandResult.doSwitchPage()) {
                handleSwitch();
                commandUpdater.executeUpdateCallback();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Gets the model's current {@code PageStatus} and switches the content in the selected placeholder.
     */
    private void handleSwitch() {
        ViewType currentViewType = model.getViewStatus().getViewType();
        View<? extends Node> newView;
        StackPane toUpdatePlaceHolder;

        switch (currentViewType) {

        case LIST_ADDRESS:
            newView = new PersonListView(this, logic, model);
            toUpdatePlaceHolder = ccaContentPlaceholder;
            break;

        case LIST_CCA:
            newView = new CcaListView(this, logic, model);
            toUpdatePlaceHolder = ccaContentPlaceholder;
            break;

        case LIST_FINANCE:
            newView = new FinanceListView(this, logic, model);
            toUpdatePlaceHolder = financeContentPlaceholder;
            break;

        case LIST_COURSE:
            newView = new CoursePlannerWindow(this, logic, model);
            toUpdatePlaceHolder = moduleContentPlaceholder;
            break;

        default:
            resultDisplay.setFeedbackToUser(
                String.format(MESSAGE_VIEW_NOT_IMPLEMENTED, currentViewType.toString()));
            return;
        }

        switchContent(newView, toUpdatePlaceHolder);
        this.commandUpdater = newView::fillPage;
        switchPage(toUpdatePlaceHolder);
    }

    /**
     * Allows callback to be executed.
     */
    @FunctionalInterface
    public interface CommandUpdater {
        void executeUpdateCallback();
    }

    /**
     * Switches the content in the {@code contentPlaceholder} {@code StackPane}.
     *
     * @param view The {@code Page} to switch to.
     */
    private void switchContent(View<? extends Node> view, StackPane toUpdatePlaceHolder) {
        setWindowDefaultSize(model.getGuiSettings());
        Node pageNode = view.getRoot();

        List<Node> currentChildren = toUpdatePlaceHolder.getChildren();
        int numChildren = currentChildren.size();
        assert numChildren == 0 || numChildren == 1;

        toUpdatePlaceHolder.getChildren().add(pageNode);

        if (numChildren == 1) {
            Node previousPage = currentChildren.get(0);
            currentChildren.remove(previousPage);
        }
    }

    /**
     * Switches the tabPane to the correct tab. Each {@targetStackPane} is associated with a tab.
     */
    private void switchPage(StackPane targetStackPane) {

        String stackPaneId = targetStackPane.getId();

        switch (stackPaneId) {

        case "plannerContentPlaceholder":
            tabPanePlaceHolder.getSelectionModel().select(PLANNER_INDEX);
            break;

        case "financeContentPlaceholder" :
            tabPanePlaceHolder.getSelectionModel().select(FINANCES_INDEX);
            break;

        case "moduleContentPlaceholder":
            tabPanePlaceHolder.getSelectionModel().select(MODULES_INDEX);
            break;

        case "ccaContentPlaceholder" :
            tabPanePlaceHolder.getSelectionModel().select(CCAS_INDEX);
            break;

        default:
            return;

        }
    }
}
