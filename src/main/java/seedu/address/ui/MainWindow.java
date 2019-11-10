package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.ui.components.CommandBox;
import seedu.address.ui.components.ResultDisplay;
import seedu.address.ui.components.StatusBarFooter;
import seedu.address.ui.currency.CurrencyPage;
import seedu.address.ui.diary.DiaryPage;
import seedu.address.ui.expense.EditExpensePage;
import seedu.address.ui.expense.ExpensesPage;
import seedu.address.ui.inventory.InventoryPage;
import seedu.address.ui.itinerary.DaysPage;
import seedu.address.ui.itinerary.EditDayPage;
import seedu.address.ui.itinerary.EditEventPage;
import seedu.address.ui.itinerary.EventsPage;
import seedu.address.ui.itinerary.ItineraryPage;
import seedu.address.ui.template.Page;
import seedu.address.ui.template.UiChangeConsumer;
import seedu.address.ui.trips.EditTripPage;
import seedu.address.ui.trips.TripsPage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private static final double MIN_WINDOW_WIDTH = 800;
    private static final double MIN_WINDOW_HEIGHT = 600;

    private static final int PAGE_TRANSITION_DURATION_MILLIS = 500;
    private static final double PAGE_TRANSITION_INITIAL_OPACITY = 0.2;
    private static final String MESSAGE_PAGE_NOT_IMPLEMENTED = "Sorry! We haven't implemented the %1$s page!";

    protected final Logger logger = LogsCenter.getLogger(getClass());
    protected Stage primaryStage;
    protected Logic logic;
    protected Model model;
    protected Page<? extends Node> currentPage;

    private CommandUpdater commandUpdater;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane contentPlaceholder;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    public MainWindow(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.logic = logic;
        this.model = model;

        setMinWindowSizeListeners();
        fillInnerParts();
        helpWindow = new HelpWindow();
    }

    private void setMinWindowSizeListeners() {
        primaryStage.setMinWidth(MIN_WINDOW_WIDTH);
        primaryStage.setMinHeight(MIN_WINDOW_HEIGHT);

        primaryStage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < MIN_WINDOW_WIDTH) {
                primaryStage.setWidth(MIN_WINDOW_WIDTH);
            }
        }));

        primaryStage.heightProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < MIN_WINDOW_HEIGHT) {
                primaryStage.setHeight(MIN_WINDOW_HEIGHT);
            }
        }));
    }

    /**
     * Fills the place holders of the MainWindow.
     */
    private void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(model.getTravelPalFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        //Set 'F1' accelerator to move keyboard focus back to cli input
        setAccelerator(KeyCombination.keyCombination("F1"), commandBox::requestFocus);
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    protected void show() {
        setWindowDefaultSize(model.getGuiSettings());
        handleSwitch();
        primaryStage.show();
    }

    /**
     * Executes the command and returns the {@code CommandResult}.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    public CommandResult executeCommand(String commandText)
            throws CommandException, ParseException, IllegalArgumentException {
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
            }

            if (commandResult.doChangeUi()) {
                handleChange(commandResult.getCommandWord());
            }

            commandUpdater.executeUpdateCallback();

            return commandResult;
        } catch (CommandException | ParseException | IllegalArgumentException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Executes the command and returns the {@code CommandResult}.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    public CommandResult executeGuiCommand(String commandText) {
        try {
            return executeCommand(commandText);
        } catch (CommandException | ParseException e) {
            return null;
        }
    }

    /**
     * Opens the help window.
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
     * Closes the application, and saves the current window position and dimensions.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        model.setGuiSettings(guiSettings);

        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Retrieves the {@code Page} type and attempts to switch the content in
     * {@code contentPlaceholder} with it.
     * TODO
     */
    private void handleSwitch() {
        PageType currentPageType = model.getPageStatus().getPageType();
        Page<? extends Node> newPage;

        switch (currentPageType) {
        case TRIP_MANAGER:
            newPage = new TripsPage(this, logic, model);
            break;
        case ADD_TRIP:
            newPage = new EditTripPage(this, logic, model);
            break;
        case ADD_EVENT:
            newPage = new EditEventPage(this, logic, model);
            break;
        case OVERALL_VIEW:
            newPage = new DaysPage(this, logic, model);
            break;
        case ADD_DAY:
            newPage = new EditDayPage(this, logic, model);
            break;
        case EVENT_PAGE:
            newPage = new EventsPage(this, logic, model);
            break;
        case ITINERARY:
            newPage = new ItineraryPage(this, logic, model);
            break;
        case EXPENSE_MANAGER:
            newPage = new ExpensesPage(this, logic, model);
            break;
        case ADD_EXPENSE:
            newPage = new EditExpensePage(this, logic, model);
            break;
        case ADD_CURRENCY:
            newPage = new CurrencyPage(this, logic, model);
            break;
        case PRETRIP_INVENTORY:
            newPage = new InventoryPage(this, logic, model);
            break;
        case DIARY:
            newPage = new DiaryPage(this, logic, model);
            break;
        default:
            resultDisplay.setFeedbackToUser(
                    String.format(MESSAGE_PAGE_NOT_IMPLEMENTED, currentPageType.toString()));
            return;
        }

        currentPage = newPage;
        switchContent(newPage);
        this.commandUpdater = newPage::fillPage;
    }

    /**
     * Switches the content in the {@code contentPlaceholder} {@code StackPane}.
     *
     * @param page The {@code Page} to switch to.
     */
    private void switchContent(Page<? extends Node> page) {
        Node pageNode = page.getRoot();

        //transition
        List<Node> currentChildren = contentPlaceholder.getChildren();
        int numChildren = currentChildren.size();
        assert numChildren == 0 || numChildren == 1;

        pageNode.translateXProperty().set(contentPlaceholder.getWidth());
        pageNode.opacityProperty().set(PAGE_TRANSITION_INITIAL_OPACITY);
        contentPlaceholder.getChildren().add(pageNode);

        Timeline timeline = new Timeline();
        KeyValue yTranslateKv =
                new KeyValue(pageNode.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyValue opacityKv =
                new KeyValue(pageNode.opacityProperty(), 1.0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(
                new Duration(PAGE_TRANSITION_DURATION_MILLIS), yTranslateKv, opacityKv);
        timeline.getKeyFrames().add(keyFrame);

        if (numChildren == 1) {
            Node previousPage = currentChildren.get(0);
            timeline.setOnFinished(event -> currentChildren.remove(previousPage));
        }

        timeline.play();
    }

    /**
     * Executes the change in the UI within the same page.
     * @param commandWord The command word used to execute this change.
     */
    private void handleChange(String commandWord) throws CommandException {
        if (currentPage instanceof UiChangeConsumer) {
            UiChangeConsumer consumer = (UiChangeConsumer) currentPage;
            consumer.changeUi(commandWord.toUpperCase());
        } else {
            throw new CommandException("Page does not support this command");
        }
    }

    /**
     * Functional interface for allowing custom operations to occur after a command is executed.
     */
    @FunctionalInterface
    public interface CommandUpdater {
        void executeUpdateCallback();
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
     * Sets an accelerator the application.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(KeyCombination keyCombination, Runnable keyCombinationExecutor) {
        getRoot().getScene().getAccelerators().put(keyCombination, keyCombinationExecutor);

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
         *
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                keyCombinationExecutor.run();
                event.consume();
            }
        });
    }
}
